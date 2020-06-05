package ch.hes_so.master.beerfest.ui.blog

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.adapter.BreweryAdapter
import ch.hes_so.master.beerfest.utils.BaseViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.android.synthetic.main.fragment_scan.*
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class ScanFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {

    private var viewModel: ScanViewModel? = null
    private val PERMISSION_REQUEST_CODE = 200

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }
    private var breweryAdapter: BreweryAdapter? = null

    private val onBreweryClick = { id: String ->
        navigateToBrewery(id)
    }
    private var appBarIsExpanded = true

    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@ScanFragment.displayId) {
                Timber.d("Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    fun navigateToBrewery(id: String) {
        //TODO navigate to brewery
        viewModel?.requestNavigation(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breweryAdapter = BreweryAdapter(onBreweryClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_scan, container, false)

    override fun onResume() {
        super.onResume()
        headerLayout.addOnOffsetChangedListener(this)
        headerLayout.setExpanded(appBarIsExpanded)
    }

    override fun onStop() {
        super.onStop()
        headerLayout.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        appBarIsExpanded = verticalOffset == 0
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScanViewModel::class.java)
        cameraExecutor = Executors.newSingleThreadExecutor()
        // Every time the orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)

        brewery_list.layoutManager = LinearLayoutManager(context)
        brewery_list.adapter = breweryAdapter

        viewModel?.brewery?.observe(viewLifecycleOwner, Observer {
            breweryAdapter?.items = it
        })

        viewModel?.navigationCommands?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseViewModel.NavigationCommand.To -> {
                    clearCamera()
                    findNavController().navigate(it.directions)
                }
                is BaseViewModel.NavigationCommand.Back -> findNavController().navigateUp()
            }
        })

        if (checkPermission()) {
            startCamera()
        } else {
            requestPermission()
        }
        viewModel?.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Shut down our background executor
        cameraExecutor.shutdown()
        brewery_list.adapter = null

        // Unregister the broadcast receivers and listeners
        displayManager.unregisterDisplayListener(displayListener)
    }

    private fun clearCamera() {
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        preview_view.post {
            // Keep track of the display in which this view is attached
            displayId = preview_view.display.displayId
            setupCamera()
        }
    }

    private fun setupCamera() {
        // Get screen metrics used to setup camera for full screen resolution
        val metrics = DisplayMetrics().also { preview_view.display.getRealMetrics(it) }
        Timber.d("Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Timber.d("Preview aspect ratio: $screenAspectRatio")

        val rotation = preview_view.display.rotation

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {

            // CameraProvider
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                // We request aspect ratio but no resolution
                .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation
                .setTargetRotation(rotation)
                .build()

            //Image capture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits our use cases
                .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
                .build()

            // ImageAnalysis
            imageAnalyzer = ImageAnalysis.Builder()
                // We request aspect ratio but no resolution
                .setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
                .build()
                // The analyzer can then be assigned to the instance
                .also {
                    it.setAnalyzer(cameraExecutor, QrCodeAnalyzer { qrCodes ->
                        Timber.e("Showing ${qrCodes.size} codes")
                        qrCodes.forEach { code ->
                            if (code.rawValue?.startsWith("brewery") == true) {
                                code.rawValue?.split("brewery")?.last()?.let { breweryId ->
                                    navigateToBrewery(breweryId)
                                }
                            }
                        }
                    })

                }
            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()

            try {
                // A variable number of use-cases can be passed here -
                // camera provides access to CameraControl & CameraInfo

                camera = cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, preview, imageCapture, imageAnalyzer
                )
                preview?.setSurfaceProvider(preview_view.createSurfaceProvider())

            } catch (exc: Exception) {
                Timber.e("Use case binding failed")
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    private fun checkPermission(): Boolean {
        return context?.let {
            ContextCompat.checkSelfPermission(
                it,
                Manifest.permission.CAMERA
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }


    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context?.let {
                            ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA)
                        } != PackageManager.PERMISSION_GRANTED
                    ) {
                            DialogInterface.OnClickListener { _, _ ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermission()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    companion object {
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    internal class QrCodeAnalyzer(
        private val onQrCodesDetected: (qrCodes: List<FirebaseVisionBarcode>) -> Unit
    ) : ImageAnalysis.Analyzer {

        @ExperimentalStdlibApi
        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(image: ImageProxy) {

            val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_QR_CODE,
                    FirebaseVisionBarcode.FORMAT_AZTEC
                )
                .build()

            val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

            val visionImage = image.image?.let {
                FirebaseVisionImage.fromMediaImage(
                    it,
                    when (image.imageInfo.rotationDegrees) {
                        0 -> FirebaseVisionImageMetadata.ROTATION_0
                        90 -> FirebaseVisionImageMetadata.ROTATION_90
                        180 -> FirebaseVisionImageMetadata.ROTATION_180
                        270 -> FirebaseVisionImageMetadata.ROTATION_270
                        else -> throw IllegalArgumentException("Not supported")
                    }
                )
            }
            visionImage?.let {
                detector.detectInImage(it)
                    .addOnSuccessListener { barcodes ->
                        onQrCodesDetected(barcodes)
                    }
                    .addOnFailureListener { exception ->
                        Timber.e(exception)
                    }
            }
            image.close()
        }
    }

}
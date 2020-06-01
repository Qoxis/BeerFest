package ch.hes_so.master.beerfest.ui.brewery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.adapter.BeersAdapter
import ch.hes_so.master.beerfest.changeConstraints
import ch.hes_so.master.beerfest.fromDpToPx
import ch.hes_so.master.beerfest.models.ConfigModel
import ch.hes_so.master.beerfest.utils.BaseViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_brewery.*
import org.koin.android.ext.android.inject

class BreweryFragment : Fragment() {

    private var viewModel: BreweryViewModel? = null
    private var adapter: BeersAdapter? = null

    private val args by navArgs<BreweryFragmentArgs>()
    private val configModel by inject<ConfigModel>()
    private var shouldReturn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreweryViewModel::class.java)
        if(args.brewery?.id == null)
        {
            if(configModel.getLastBreweryId() != -1) {
                viewModel?.init(configModel.getLastBreweryId())
            } else {
                shouldReturn = true
            }
        } else {
            viewModel?.init(args.brewery?.id!!)
        }
        adapter = BeersAdapter {
           viewModel?.getBeer(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_brewery, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brewery_toolbar.setupWithNavController(findNavController())

        if(shouldReturn){
            AlertDialog.Builder(context)
                .setMessage(R.string.no_brewery)
                .setPositiveButton(android.R.string.ok) { it, _ ->
                    findNavController().navigate(R.id.fragment_scan)
                    it.dismiss()
                }
                .setOnDismissListener {
                    findNavController().navigate(R.id.fragment_scan)
                    it.dismiss()
                }
                .create()
                .show()
        }

        context?.let { context ->
            if (configModel.isLeftHanded()) {
                brewery_view.changeConstraints {
                    this.connect(
                        socialNetworks.id,
                        ConstraintSet.LEFT,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.LEFT,
                        24.fromDpToPx(context)
                    )
                    this.clear(
                        socialNetworks.id,
                        ConstraintSet.RIGHT
                    )
                }
            }
        }
        rv_beers.layoutManager = LinearLayoutManager(context)
        rv_beers.adapter = adapter
        adapter?.items = emptyList()

        viewModel?.brewery?.observe(viewLifecycleOwner, Observer {
            brewery_name.text = it.name
            Glide.with(this).load(it.thumbnaill).into(brewery_header_image)
        })

        viewModel?.beers?.observe(viewLifecycleOwner, Observer {
            adapter?.items = it
            noBeer.isVisible = it.isEmpty()
            rv_beers.isVisible = it.isNotEmpty()
        })

        viewModel?.navigationCommands?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseViewModel.NavigationCommand.To -> findNavController().navigate(it.directions)
                is BaseViewModel.NavigationCommand.Back -> findNavController().navigateUp()
            }
        })

    }
}

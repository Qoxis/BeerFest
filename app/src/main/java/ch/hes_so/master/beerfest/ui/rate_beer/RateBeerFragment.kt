package ch.hes_so.master.beerfest.ui.rate_beer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.data.entities.Rating
import ch.hes_so.master.beerfest.models.ConfigModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_rate_beer.*
import org.koin.android.ext.android.inject

class RateBeerFragment : Fragment() {

    private val args by navArgs<RateBeerFragmentArgs>()
    private var viewModel: RateBeerViewModel? = null
    private var localRating: Rating? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RateBeerViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_rate_beer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rate_beer_toolbar.setupWithNavController(findNavController())
        viewModel?.init(args.beer?.id)

        viewModel?.rating?.observe(viewLifecycleOwner, Observer {
            localRating = it
            ratingLove.rating = it.rateLove.toFloat()
            ratingHops.rating = it.rateBitter.toFloat()
            ratingAlcohol.rating = it.rateLight.toFloat()
        })

        args.beer?.let {
            Glide.with(this).load(it.imageUUID).into(rate_beer_header_image)
        }

        saveRating.setOnClickListener {

            if (localRating != null) {
                localRating = localRating?.copy(
                    rateLight = ratingAlcohol.rating.toInt(),
                    rateBitter = ratingHops.rating.toInt(),
                    rateLove = ratingLove.rating.toInt(),
                    rateFruitness = 0
                )
                localRating?.let { it1 -> viewModel?.updateRating(it1) }
            } else {
                localRating = args.beer?.id?.let { beerId ->
                    Rating(
                        rateLight = ratingAlcohol.rating.toInt(),
                        rateBitter = ratingHops.rating.toInt(),
                        rateLove = ratingLove.rating.toInt(),
                        beerId = beerId,
                        rateFruitness = 0
                    )
                }
                localRating?.let { it1 -> viewModel?.addRating(it1) }

            }
            Toast.makeText(context, R.string.rate_saved_successfully, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }


    }

}

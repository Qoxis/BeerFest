package ch.hes_so.master.beerfest.ui.beer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController

import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.changeConstraints
import ch.hes_so.master.beerfest.fromDpToPx
import ch.hes_so.master.beerfest.models.ConfigModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_beer.*
import org.koin.android.ext.android.inject

class BeerFragment : Fragment() {

    private val args by navArgs<BeerFragmentArgs>()
    private var viewModel: BeerViewModel? = null
    private val configModel by inject<ConfigModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeerViewModel::class.java)
        viewModel?.init(args.beer?.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beer_toolbar.setupWithNavController(findNavController())
        if(args.beer == null){
            findNavController().navigateUp()
        }

        args.beer?.let {
            beer_name.text = it.name
            beer_style.text = it.type
            beer_alcool.text = "${it.acool} %"
            beer_ibu.text = it.IBU.toString()
            beer_color.text = it.color
            beer_description.text = it.description
            Glide.with(this).load(it.imageUUID).into(beer_header_image)
        }

        context?.let { context ->
            if (configModel.isLeftHanded()) {
                beerView.changeConstraints {
                    this.connect(
                        rate_beer.id,
                        ConstraintSet.LEFT,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.LEFT,
                        24.fromDpToPx(context)
                    )
                    this.clear(
                        rate_beer.id,
                        ConstraintSet.RIGHT
                    )
                }
            }
        }

        viewModel?.flavours?.observe(viewLifecycleOwner, Observer {flavours ->
            beer_flavours.text = flavours.joinToString(", ") { it.name }
        })

        rate_beer.setOnClickListener {
            val action = BeerFragmentDirections.actionBeerFragmentToRateBeerFragment(args.beer)
            findNavController().navigate(action)
        }

    }

}

package ch.hes_so.master.beerfest.ui.brewery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.adapter.BeersAdapter
import ch.hes_so.master.beerfest.utils.BaseViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_brewery.*

class BreweryFragment : Fragment() {

    private var viewModel: BreweryViewModel? = null
    private var adapter: BeersAdapter? = null

    private val args by navArgs<BreweryFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreweryViewModel::class.java)
        viewModel?.init(args.brewery)
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

        if(args.brewery == null){
            findNavController().navigate(R.id.fragment_scan)
        }

        rv_beers.layoutManager = LinearLayoutManager(context)
        rv_beers.adapter = adapter

        args.brewery?.let {
            brewery_name.text = it.name
            Glide.with(this).load(it.thumbnaill).into(brewery_header_image)
        }

        viewModel?.beers?.observe(viewLifecycleOwner, Observer {
            adapter?.items = it
        })
        viewModel?.navigationCommands?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseViewModel.NavigationCommand.To -> findNavController().navigate(it.directions)
                is BaseViewModel.NavigationCommand.Back -> findNavController().navigateUp()
            }
        })

    }
}

package ch.hes_so.master.beerfest.ui.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.adapter.EventAdapter
import kotlinx.android.synthetic.main.fragment_blog.*
import androidx.recyclerview.widget.LinearLayoutManager
import ch.hes_so.master.beerfest.utils.BaseViewModel
import androidx.navigation.fragment.findNavController

class BlogFragment : Fragment() {

    private var viewModel: BlogViewModel? = null
    private var adapter: EventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlogViewModel::class.java)
        viewModel?.init()
        /*adapter = EventAdapter {
            viewModel?.getEvent(it)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_blog, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_events.layoutManager = LinearLayoutManager(context)
        rv_events.adapter = adapter

        viewModel?.events?.observe(viewLifecycleOwner, Observer {
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

package ch.hes_so.master.beerfest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.data.entities.Beer
import kotlinx.android.synthetic.main.item_beer.view.*

class BeersAdapter(private val onBeerClick: (Int) -> Unit) : RecyclerView.Adapter<BeersAdapter.BeerViewHolder>() {

    var items = emptyList<Beer>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class BeerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent,false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = items[position]
        holder.itemView.apply {
            beer_style.text = beer.type
            beer_name.text = beer.name
            setOnClickListener {
                onBeerClick(beer.id)
            }
        }
    }
}
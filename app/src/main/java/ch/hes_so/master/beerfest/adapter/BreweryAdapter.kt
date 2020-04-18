package ch.hes_so.master.beerfest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.data.entities.Brewery
import kotlinx.android.synthetic.main.item_brewery.view.*

class BreweryAdapter(private val onBreweryClick: (String) -> Unit) : RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder>() {

    var items = emptyList<Brewery>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class BreweryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        return BreweryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_brewery, parent,false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        holder.itemView.apply {
            brewery_name.text = items[position].name
            brewery_position.text = "${position+1}#"
            setOnClickListener {
                onBreweryClick.invoke(items[position].id.toString())
            }
        }
    }
}
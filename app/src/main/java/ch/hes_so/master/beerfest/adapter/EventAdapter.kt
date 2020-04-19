package ch.hes_so.master.beerfest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.hes_so.master.beerfest.R
import ch.hes_so.master.beerfest.data.entities.Event
import kotlinx.android.synthetic.main.item_event.view.*

class EventAdapter(private val onEventClick: (Int) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var items = emptyList<Event>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent,false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = items[position]
        holder.itemView.apply {
            event_title.text = event.name
            event_location.text = event.place
            setOnClickListener {
                onEventClick(event.id)
            }
        }
    }
}
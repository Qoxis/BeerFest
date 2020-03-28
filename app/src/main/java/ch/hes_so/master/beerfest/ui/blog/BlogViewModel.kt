package ch.hes_so.master.beerfest.ui.blog

import androidx.lifecycle.ViewModel
import ch.hes_so.master.beerfest.data.dao.EventsDao
import org.koin.core.KoinComponent
import org.koin.core.inject

class BlogViewModel : ViewModel(), KoinComponent {

    private val eventsDao by inject<EventsDao>()
}
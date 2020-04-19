package ch.hes_so.master.beerfest.ui.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.hes_so.master.beerfest.data.dao.EventDao
import ch.hes_so.master.beerfest.data.entities.Event
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BlogViewModel : BaseViewModel(), KoinComponent {

    private val eventDao by inject<EventDao>()

    val events = MutableLiveData<List<Event>>()

    val disposables = CompositeDisposable()

    fun init() {

        val disposable = eventDao.getAllEvents().subscribe {
            events.postValue(it)
        }
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getEvent(eventId: Int) {
        val disposable = eventDao.getEvent(eventId).subscribe {
            //navigate(BlogFragmentDirections.actionactionFragmentBreweryToBeerFragment(it))
        }
        disposables.add(disposable)
    }
}
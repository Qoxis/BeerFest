package ch.hes_so.master.beerfest.ui.blog

import androidx.lifecycle.MutableLiveData
import ch.hes_so.master.beerfest.data.dao.EventDao
import ch.hes_so.master.beerfest.data.entities.Event
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class BlogViewModel : BaseViewModel(), KoinComponent {

    private val eventDao by inject<EventDao>()

    val events = MutableLiveData<List<Event>>()

    private val disposables = CompositeDisposable()

    fun init() {

        val disposable = eventDao.getAllEvents()
            .onErrorReturn {
                Timber.e(it)
                emptyList<Event>()
            }
            .subscribe {
                events.postValue(it)
            }
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getEvent(eventId: Int) {
        val disposable = eventDao.getEvent(eventId)
            .onErrorReturn {
                Timber.e(it)
                null
            }.subscribe {
            //navigate(BlogFragmentDirections.actionactionFragmentBreweryToBeerFragment(it))
        }
        disposables.add(disposable)
    }
}
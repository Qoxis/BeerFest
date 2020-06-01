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
                updateEvents(it)
            }
        disposables.add(disposable)
    }

    private fun updateEvents(it: List<Event>?) {
        it?.forEach {
            when (it.id) {
                0 -> {
                    eventDao.update(
                        it.copy(imageUrl = "https://learn.kegerator.com/wp-content/uploads/2014/11/brewing_beer_ingredients.jpg")
                    )
                }
                1 -> {
                    eventDao.update(
                        it.copy(imageUrl = "https://spaceselectors.com/wp-content/uploads/2019/07/breweries-colorado-lease.jpg")
                    )
                }
                2 -> {
                    eventDao.update(
                        it.copy(imageUrl = "https://www.discovertheburgh.com/wp-content/uploads/2019/03/DSC09507-600px.jpg")
                    )
                }
                3 -> {
                    eventDao.update(
                        it.copy(imageUrl = "https://editorial.designtaxi.com/news-beer180214/1.jpg")
                    )
                }
                4 -> {
                    eventDao.update(
                        it.copy(imageUrl = "https://static.vinepair.com/wp-content/uploads/2015/08/hops-and-beer-social.jpg")
                    )
                }
                else -> {
                }
            }
        }
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
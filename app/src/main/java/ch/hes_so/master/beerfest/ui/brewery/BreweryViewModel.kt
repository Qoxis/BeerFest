package ch.hes_so.master.beerfest.ui.brewery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.hes_so.master.beerfest.data.dao.BeerDao
import ch.hes_so.master.beerfest.data.dao.BreweryDao
import ch.hes_so.master.beerfest.data.entities.Beer
import ch.hes_so.master.beerfest.data.entities.Brewery
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BreweryViewModel : BaseViewModel(), KoinComponent {

    private val breweryDao by inject<BreweryDao>()
    private val beerDao by inject<BeerDao>()

    val beers = MutableLiveData<List<Beer>>()

    private val disposables = CompositeDisposable()

    fun init(brewery: Brewery?) {
        brewery?.id?.let {
            val disposable = beerDao.getBeersOfBrewery(it).subscribe {
                beers.postValue(it)
            }
            disposables.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getBeer(beerId: Int) {
        val disposable = beerDao.getBeer(beerId).subscribe {
            navigate(BreweryFragmentDirections.actionFragmentBreweryToBeerFragment(it))
        }
        disposables.add(disposable)
    }
}
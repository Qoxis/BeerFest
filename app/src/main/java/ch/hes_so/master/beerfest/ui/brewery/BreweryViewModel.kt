package ch.hes_so.master.beerfest.ui.brewery

import androidx.lifecycle.MutableLiveData
import ch.hes_so.master.beerfest.data.dao.BeerDao
import ch.hes_so.master.beerfest.data.dao.BreweryDao
import ch.hes_so.master.beerfest.data.entities.Beer
import ch.hes_so.master.beerfest.data.entities.Brewery
import ch.hes_so.master.beerfest.models.ConfigModel
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BreweryViewModel : BaseViewModel(), KoinComponent {

    private val breweryDao by inject<BreweryDao>()
    private val beerDao by inject<BeerDao>()
    private val configModel by inject<ConfigModel>()

    val beers = MutableLiveData<List<Beer>>()
    val brewery = MutableLiveData<Brewery>()

    private val disposables = CompositeDisposable()

    fun init(breweryId: Int) {

        disposables.add(beerDao.getBeersOfBrewery(breweryId).subscribe {
            beers.postValue(it)
        })

        disposables.add(breweryDao.getBrewery(breweryId.toString()).subscribe {
            brewery.postValue(it)
        })
        configModel.setLastBreweryId(breweryId)
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
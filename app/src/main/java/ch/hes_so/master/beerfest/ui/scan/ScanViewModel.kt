package ch.hes_so.master.beerfest.ui.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import ch.hes_so.master.beerfest.data.dao.BreweryDao
import ch.hes_so.master.beerfest.data.entities.Brewery
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class ScanViewModel : BaseViewModel(), KoinComponent {

    private val breweryDao by inject<BreweryDao>()

    val brewery = MutableLiveData<List<Brewery>>()

    val disposables = CompositeDisposable()

    fun init() {

        val disposable = breweryDao.getAllBrewery().subscribe {
            brewery.postValue(it)
        }
        disposables.add(disposable)
    }

    fun requestNavigation(id: String) {
        val disposable = breweryDao.getBrewery(id).subscribe {
            navigate(ScanFragmentDirections.actionFragmentScanToFragmentBrewery(it))
        }
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
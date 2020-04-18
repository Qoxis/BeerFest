package ch.hes_so.master.beerfest.ui.beer

import androidx.lifecycle.MutableLiveData
import ch.hes_so.master.beerfest.data.dao.BeerDao
import ch.hes_so.master.beerfest.data.dao.FlavourDao
import ch.hes_so.master.beerfest.data.entities.Beer
import ch.hes_so.master.beerfest.data.entities.Flavours
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class BeerViewModel : BaseViewModel(), KoinComponent{

    private val beerDao by inject<BeerDao>()
    private val flavourDao by inject<FlavourDao>()

    val flavours = MutableLiveData<List<Flavours>>()

    private val disposables = CompositeDisposable()



    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun init(id: Int?) {
        id?.let {
            val disposable = flavourDao.getBeerFlavours(it).subscribe {
                flavours.postValue(it)
            }
            disposables.add(disposable)
        }
    }
}
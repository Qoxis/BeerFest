package ch.hes_so.master.beerfest.ui.brewery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.hes_so.master.beerfest.data.dao.BeerDao
import ch.hes_so.master.beerfest.data.dao.BreweryDao
import org.koin.core.KoinComponent
import org.koin.core.inject

class BreweryViewModel : ViewModel(), KoinComponent {

    private val breweryDao by inject<BreweryDao>()
    private val beerDao by inject<BeerDao>()

}
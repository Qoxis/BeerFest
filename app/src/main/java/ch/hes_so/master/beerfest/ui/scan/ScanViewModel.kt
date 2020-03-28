package ch.hes_so.master.beerfest.ui.blog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.hes_so.master.beerfest.data.dao.BreweryDao
import ch.hes_so.master.beerfest.data.entities.Beer
import org.koin.core.KoinComponent
import org.koin.core.inject

class ScanViewModel : ViewModel(), KoinComponent {

    private val breweryDao by inject<BreweryDao>()


}
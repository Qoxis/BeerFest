package ch.hes_so.master.beerfest.ui.rate_beer

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import ch.hes_so.master.beerfest.data.dao.RatingDao
import ch.hes_so.master.beerfest.data.entities.Rating
import ch.hes_so.master.beerfest.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class RateBeerViewModel : BaseViewModel(), KoinComponent {

    val ratingDao by inject<RatingDao>()

    val rating = MutableLiveData<Rating>()
    private val disposables = CompositeDisposable()


    fun init(id: Int?) {
        id?.let {
            val disposable = ratingDao.getBeerRating(it).subscribe {
                rating.postValue(it)
            }
            disposables.add(disposable)
        }
    }

    fun updateRating(rating: Rating) {
        Observable.fromCallable {
            Runnable {
                ratingDao.update(rating)
            }.run()
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    @SuppressLint("CheckResult")
    fun addRating(rating: Rating) {
        Observable.fromCallable {
            Runnable {
                ratingDao.insert(rating)
            }.run()
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
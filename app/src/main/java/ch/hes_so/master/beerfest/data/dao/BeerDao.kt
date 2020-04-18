package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface BeerDao{

    //Beer
    @Query("SELECT * FROM beer")
    fun getAllBeers(): Flowable<List<Beer>> //TODO use LiveData for Pagination

    @Query("SELECT * FROM beer WHERE brewery = :breweryId")
    fun getBeersOfBrewery(breweryId: Int): Flowable<List<Beer>>

    @Query("SELECT * FROM beer WHERE id = :beer_id")
    fun getBeer(beer_id: Int): Flowable<Beer>

    @Insert
    fun insert(beer: Beer)




}
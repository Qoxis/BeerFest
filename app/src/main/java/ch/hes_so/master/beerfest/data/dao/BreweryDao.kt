package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface BreweryDao{

    //Brewery
    @Query("SELECT * FROM brewery")
    fun getAllBrewery(): Flowable<List<Brewery>>

    @Query("SELECT * FROM brewery WHERE id = :breweryId")
    fun getBrewery(breweryId: String): Flowable<Brewery>

    @Insert
    fun insert(brewery: Brewery)

}
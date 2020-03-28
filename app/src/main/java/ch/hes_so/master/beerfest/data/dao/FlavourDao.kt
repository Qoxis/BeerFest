package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface FlavourDao{

    //Flavours
    @Query("SELECT * FROM flavours")
    fun getAllFlavours(): Flowable<List<Flavours>>

    @Insert
    fun insert(flavours: Flavours)

    @Insert
    fun insert(beerFlavour: BeerFlavour)

    @Query("SELECT flavourId FROM beer_flavour WHERE beerId = :beerId")
    fun getBeerFlavourId(beerId: Int): Flowable<List<Int>>


}
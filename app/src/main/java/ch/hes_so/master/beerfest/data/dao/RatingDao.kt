package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface RatingDao{
    
    //Rating
    @Query("SELECT * FROM rating WHERE beerId = :beerId")
    fun getBeerRating(beerId: Int): Flowable<List<Rating>>

    @Insert
    fun insert(rating: Rating)

}
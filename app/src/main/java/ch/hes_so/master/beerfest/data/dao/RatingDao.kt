package ch.hes_so.master.beerfest.data.dao

import androidx.room.*
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface RatingDao{
    
    //Rating
    @Query("SELECT * FROM rating WHERE beerId = :beerId")
    fun getBeerRating(beerId: Int): Flowable<Rating>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rating: Rating)

    @Update
    fun update(rating: Rating)

}
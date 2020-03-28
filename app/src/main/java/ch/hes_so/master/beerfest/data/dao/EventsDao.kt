package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface EventsDao{

    //Events
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flowable<List<Events>>

    @Insert
    fun insert(event: Events)

}
package ch.hes_so.master.beerfest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hes_so.master.beerfest.data.entities.*
import io.reactivex.Flowable

@Dao
interface EventDao{

    //Events
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flowable<List<Event>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEvent(eventId: Int): Flowable<Event>

    @Insert
    fun insert(event: Event)
}
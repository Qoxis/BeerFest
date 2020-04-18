package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "events")
data class Events(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val place: String,
    val date: DateTime
) : Serializable
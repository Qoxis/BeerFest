package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "flavours")
data class Flavours(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) : Serializable
package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "beer_flavour")
data class BeerFlavour(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val beerId: Int,
    val flavourId: Int
) : Serializable
package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "rating")
data class Rating(
    val beerId: Int,
    val rateLove: Int,
    val rateBitter: Int,
    val rateFruitness: Int,
    val rateLight: Int
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
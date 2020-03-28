package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rating")
data class Rating(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val beerId: Int,
    val rateLove: Int,
    val rateBitter: Int,
    val rateFruitness: Int,
    val rateLight: Int
)
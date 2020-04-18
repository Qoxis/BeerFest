package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "brewery")
data class Brewery(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val country: String,
    val state: String,
    val city: String,
    val thumbnaill: String
): Serializable {
    enum class COUNTRY(val country: String) {
        ENGLAND("Angleterre"), SWITZERLAND("Suisse"), FRANCE("France"), GERMANY("Allemagne"), SWEDEN(
            "Suède"
        ),
        DANEMARK("Danemark"), NETHERLANDS("Pays-bas")
    }
}
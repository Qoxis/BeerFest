package ch.hes_so.master.beerfest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.joda.time.DateTime
import java.io.Serializable

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val place: String,
    val date: DateTime,
    val imageUrl: String? //Added in version 2
) : Serializable

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE events ADD COLUMN imageUrl TEXT DEFAULT ''")
    }
}
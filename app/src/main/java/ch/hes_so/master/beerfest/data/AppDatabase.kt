package ch.hes_so.master.beerfest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.hes_so.master.beerfest.data.converters.Converters
import ch.hes_so.master.beerfest.data.dao.*
import ch.hes_so.master.beerfest.data.entities.*

@Database(entities = [Beer::class, Brewery::class, Events::class, Rating::class, Flavours::class, BeerFlavour::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun breweryDao(): BreweryDao
    abstract fun eventsDao(): EventsDao
    abstract fun flavourDao(): FlavourDao
    abstract fun ratingDao(): RatingDao
}
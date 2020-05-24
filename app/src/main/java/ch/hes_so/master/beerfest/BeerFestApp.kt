package ch.hes_so.master.beerfest

import android.app.Application
import androidx.room.Room
import ch.hes_so.master.beerfest.data.AppDatabase
import ch.hes_so.master.beerfest.data.entities.MIGRATION_2_3
import ch.hes_so.master.beerfest.models.ConfigModel
import ch.hes_so.master.beerfest.models.LanguageModel
import com.google.firebase.FirebaseApp
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class BeerFestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "beerfest-db")
            .createFromAsset("database/beerfest-db")
            .addMigrations(MIGRATION_2_3)
            .build()

        startKoin {
            androidContext(this@BeerFestApp)
            modules(getModules())
        }
        FirebaseApp.initializeApp(this)
        JodaTimeAndroid.init(this)
    }

    private fun getModules(): Module{
        return module {
            single { database?.beerDao() }
            single { database?.breweryDao() }
            single { database?.eventsDao() }
            single { database?.ratingDao() }
            single { database?.flavourDao() }
            single { ConfigModel(get()) }
            single {LanguageModel(get(), get())}
        }
    }

    companion object {
        var database: AppDatabase? = null
    }
}
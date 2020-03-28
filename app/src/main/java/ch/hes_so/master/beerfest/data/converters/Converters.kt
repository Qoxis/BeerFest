package ch.hes_so.master.beerfest.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.joda.time.DateTime
import java.lang.reflect.Type

class Converters {

    //Time
    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToDate(data: String?): DateTime {
        val type: Type = object : TypeToken<DateTime?>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun dateToString(dateTime: DateTime?): String {
        return gson.toJson(dateTime)
    }
}
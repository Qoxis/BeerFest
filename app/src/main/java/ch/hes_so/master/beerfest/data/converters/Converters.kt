package ch.hes_so.master.beerfest.data.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class Converters {
    private var dateFormat: DateTimeFormatter =
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")


    @TypeConverter
    fun stringToDate(data: String?): DateTime {
        return DateTime.parse(data).toDateTime()
    }

    @TypeConverter
    fun dateToString(dateTime: DateTime): String {
        return dateTime.toString()
    }
}
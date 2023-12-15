package com.kominfo.kalenderevent.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
@Parcelize
@Entity(tableName = "event")
data class ResponseItem(
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val url: String? = null,
    val lokasi: List<String?>? = null,
    val nama: String? = null,
    val maps: List<String?>? = null,
    val calender: Timestamp? = null,
    val deskripsi: String? = null
) : Parcelable {

    fun getDateAsString(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = (calender?.seconds ?: 0) * 1000

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun getTimeAsString(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = (calender?.seconds ?: 0) * 1000

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }
}


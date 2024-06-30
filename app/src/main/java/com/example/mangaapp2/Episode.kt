package com.example.mangaapp2

import android.os.Parcel
import android.os.Parcelable

data class ApiResponse<T>(
    val count: Int,
    val next: String?, // Nullable if API may return null for next
    val previous: String?, // Nullable if API may return null for previous
    val results: T
)

data class Episode(
    val id: Int,
    val title: String,
    val description: String,
    val chapter_number: Int,
    val manga: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(chapter_number)
        parcel.writeInt(manga)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Episode> {
        override fun createFromParcel(parcel: Parcel): Episode {
            return Episode(parcel)
        }

        override fun newArray(size: Int): Array<Episode?> {
            return arrayOfNulls(size)
        }
    }
}

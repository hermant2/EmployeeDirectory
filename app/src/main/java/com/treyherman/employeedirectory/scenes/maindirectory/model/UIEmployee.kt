package com.treyherman.employeedirectory.scenes.maindirectory.model

import android.os.Parcel
import android.os.Parcelable

data class UIEmployee(
    val uuid: String,
    val nameAndTeam: String,
    val phoneNumber: String?,
    val email: String,
    val bio: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val classification: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: ""
    )

    fun isValid(): Boolean {
        return uuid.isNotEmpty() && nameAndTeam.isNotEmpty() && email.isNotEmpty() && classification.isNotEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(nameAndTeam)
        parcel.writeString(phoneNumber)
        parcel.writeString(email)
        parcel.writeString(bio)
        parcel.writeString(photoUrlSmall)
        parcel.writeString(photoUrlLarge)
        parcel.writeString(classification)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UIEmployee> {
        override fun createFromParcel(parcel: Parcel): UIEmployee {
            return UIEmployee(parcel)
        }

        override fun newArray(size: Int): Array<UIEmployee?> {
            return arrayOfNulls(size)
        }
    }
}

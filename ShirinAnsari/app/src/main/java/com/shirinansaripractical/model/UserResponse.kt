package com.shirinansaripractical.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("results")
    var results: ArrayList<UserItem>
) {

    data class UserItem(
        @SerializedName("gender")
        var gender: String?,
        @SerializedName("name")
        var name: UserName? = null,
        @SerializedName("location")
        var location: UserLocation? = null,
        @SerializedName("email")
        var email: String?,
        @SerializedName("phone")
        var phone: String?,
        @SerializedName("login")
        var login: UserLogin? = null,
        @SerializedName("picture")
        var picture: UserPicture? = null

    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            gender = parcel.readString(),
            name = parcel.readArrayList(UserName::class.java.classLoader) as UserName,
            location = parcel.readArrayList(UserLocation::class.java.classLoader) as UserLocation,
            email = parcel.readString(),
            phone = parcel.readString(),
            picture = parcel.readArrayList(UserPicture::class.java.classLoader) as UserPicture
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(gender)
            parcel.writeString(email)
            parcel.writeString(phone)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<UserItem> {
            override fun createFromParcel(parcel: Parcel): UserItem {
                return UserItem(parcel)
            }

            override fun newArray(size: Int): Array<UserItem?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class UserName(
        @SerializedName("title")
        var title: String?,
        @SerializedName("first")
        var first: String?,
        @SerializedName("last")
        var last: String?
    )

    data class UserLocation(
        @SerializedName("city")
        var city: String?,
        @SerializedName("state")
        var state: String?,
        @SerializedName("country")
        var country: String?,
        @SerializedName("coordinates")
        var coordinates: UserCoordinates? = null
    )

    data class UserCoordinates(
        @SerializedName("latitude")
        var latitude: String?,
        @SerializedName("longitude")
        var longitude: String?
    )

    data class UserPicture(
        @SerializedName("medium")
        var medium: String?,
        @SerializedName("thumbnail")
        var thumbnail: String?
    )

    data class UserLogin(
        @SerializedName("uuid")
        var uuid: String?
    )
}
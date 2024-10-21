package com.devjethava.koinboilerplate.model.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardResponse() : Parcelable {
    @SerializedName("results")
    @Expose
    val results: ArrayList<DashboardData>? = null

    @SerializedName("info")
    @Expose
    val info: InfoData? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DashboardResponse> {
        override fun createFromParcel(parcel: Parcel): DashboardResponse {
            return DashboardResponse(parcel)
        }

        override fun newArray(size: Int): Array<DashboardResponse?> {
            return arrayOfNulls(size)
        }
    }
}

class InfoData() : Parcelable {
    @SerializedName("seed")
    @Expose
    val seed: String? = null

    @SerializedName("results")
    @Expose
    val results: String? = null

    @SerializedName("page")
    @Expose
    val page: String? = null

    @SerializedName("version")
    @Expose
    val version: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoData> {
        override fun createFromParcel(parcel: Parcel): InfoData {
            return InfoData(parcel)
        }

        override fun newArray(size: Int): Array<InfoData?> {
            return arrayOfNulls(size)
        }
    }

}

class DashboardData() : Parcelable {
    @SerializedName("gender")
    @Expose
    val gender: String? = null

    @SerializedName("name")
    @Expose
    val name: NameData? = null

    @SerializedName("location")
    @Expose
    val location: LocationData? = null

    @SerializedName("dob")
    @Expose
    val dob: DobData? = null

    @SerializedName("email")
    @Expose
    val email: String? = null

    @SerializedName("phone")
    @Expose
    val phone: String? = null

    @SerializedName("picture")
    @Expose
    val picture: PictureData? = null

    @SerializedName("nat")
    @Expose
    val native: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DashboardData> {
        override fun createFromParcel(parcel: Parcel): DashboardData {
            return DashboardData(parcel)
        }

        override fun newArray(size: Int): Array<DashboardData?> {
            return arrayOfNulls(size)
        }
    }
}

class NameData() : Parcelable {
    @SerializedName("title")
    @Expose
    val title: String? = null

    @SerializedName("first")
    @Expose
    val first: String? = null

    @SerializedName("last")
    @Expose
    val last: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NameData> {
        override fun createFromParcel(parcel: Parcel): NameData {
            return NameData(parcel)
        }

        override fun newArray(size: Int): Array<NameData?> {
            return arrayOfNulls(size)
        }
    }
}

class LocationData() : Parcelable {
    @SerializedName("city")
    @Expose
    val city: String? = null

    @SerializedName("state")
    @Expose
    val state: String? = null

    @SerializedName("country")
    @Expose
    val country: String? = null

    @SerializedName("postcode")
    @Expose
    val postcode: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationData> {
        override fun createFromParcel(parcel: Parcel): LocationData {
            return LocationData(parcel)
        }

        override fun newArray(size: Int): Array<LocationData?> {
            return arrayOfNulls(size)
        }
    }
}

class DobData() : Parcelable {
    @SerializedName("date")
    @Expose
    val date: String? = null

    @SerializedName("age")
    @Expose
    val age: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DobData> {
        override fun createFromParcel(parcel: Parcel): DobData {
            return DobData(parcel)
        }

        override fun newArray(size: Int): Array<DobData?> {
            return arrayOfNulls(size)
        }
    }

}

class PictureData() : Parcelable {
    @SerializedName("large")
    @Expose
    val large: String? = null

    @SerializedName("medium")
    @Expose
    val medium: String? = null

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PictureData> {
        override fun createFromParcel(parcel: Parcel): PictureData {
            return PictureData(parcel)
        }

        override fun newArray(size: Int): Array<PictureData?> {
            return arrayOfNulls(size)
        }
    }
}
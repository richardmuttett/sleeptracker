package com.richardmuttett.sleeptracker.model

import android.os.Parcel
import android.os.Parcelable

data class Sleep(
    val dateOfSleep: String = "",
    val efficiency: Int = 0): Parcelable {

  companion object {
    @JvmField
    val CREATOR: Parcelable.Creator<Sleep> = object: Parcelable.Creator<Sleep> {
      override fun createFromParcel(source: Parcel) = Sleep(source)
      override fun newArray(size: Int): Array<Sleep?> = arrayOfNulls(size)
    }
  }

  constructor(source: Parcel): this(
      source.readString(),
      source.readInt())

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeString(dateOfSleep)
    dest.writeInt(efficiency)
  }

  override fun describeContents() = 0
}

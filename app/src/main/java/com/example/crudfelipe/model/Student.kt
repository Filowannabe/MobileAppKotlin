package com.example.crudfelipe.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*
import kotlin.collections.ArrayList

class Student(
    val code: String?,
    val date: Date,
    val name: String?,
    val grades: FloatArray?
) : Parcelable {

    var friends: ArrayList<Student> = ArrayList()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as Date,
        parcel.readString(),
        parcel.createFloatArray()
    ) {
        friends = parcel.createTypedArrayList(CREATOR) as ArrayList<Student>
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeSerializable(date)
        parcel.writeString(name)
        parcel.writeFloatArray(grades)
        parcel.writeTypedList(friends)
    }

    override fun toString(): String {
        return "Estudiante(codigo=$code, fechaNacimiento=$date, nombre=$name, notas=${grades.contentToString()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        other as Student

        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        return code?.hashCode() ?: 0
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}
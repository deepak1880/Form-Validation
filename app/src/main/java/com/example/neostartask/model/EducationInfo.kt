package com.example.neostartask.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EducationInfo(
    val education: String,
    val yearOfPassing: Int,
    val university: String,
    val grade: String
) : Parcelable

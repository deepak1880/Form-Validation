package com.example.neostartask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val address: String,
    val landmark: String,
    val pinCode: String,
    val city: String,
    val state: String
) : Parcelable

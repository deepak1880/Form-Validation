package com.example.neostartask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfessionalInfo(
    val experience: Int = 0,
    val designation: String,
    val domain: String
) : Parcelable

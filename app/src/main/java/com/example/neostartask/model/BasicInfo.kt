package com.example.neostartask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicInfo(
    val profileImage: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val isMale: Boolean = true,
    val password: String,
    val confirmPassword: String
):Parcelable

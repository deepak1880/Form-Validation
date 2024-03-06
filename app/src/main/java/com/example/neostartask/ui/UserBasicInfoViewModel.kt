package com.example.neostartask.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserBasicInfoViewModel @Inject constructor() : ViewModel() {

    var profileImageUri: String? = null

    fun validateFirstName(firstName: String): Pair<Boolean, String> {
        if (firstName.isEmpty()) {
            return Pair(false, "First name should not be empty")
        }

        if (firstName.length < 3) {
            return Pair(false, "First name should be more than 3 characters")
        }

        return Pair(true, "")

    }

    fun validateLastName(lastName: String): Pair<Boolean, String> {
        if (lastName.isEmpty()) {
            return Pair(false, "Last name should not be empty")
        }
        if (lastName.length < 3) {
            return Pair(false, "Last name should be more than 3 characters")
        }

        return Pair(true, "")

    }

    fun validateEmail(email: String): Pair<Boolean, String> {
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
        return if (emailRegex.matches(email)) {
            Pair(true, "")
        } else {
            Pair(false, "Invalid email format")
        }
    }

    fun validatePhoneNumber(phoneNumber: String): Pair<Boolean, String> {
        if (phoneNumber.isEmpty()) {
            return Pair(false, "Phone number should not be empty")
        }
        val mobileNumberRegex = Regex("^\\d{10}$")
        return if (mobileNumberRegex.matches(phoneNumber)) {
            Pair(true, "") // Mobile number is valid
        } else {
            Pair(
                false,
                "Mobile number must be exactly 10 digits"
            )
        }
    }

    fun validatePassword(password: String): Pair<Boolean, String> {
        if (password.isEmpty()) {
            return Pair(false, "Password should not be empty")
        }
        val passwordRegex = Regex("^(?=.*\\d)(?=.*[!@#\$%^&*])[A-Za-z\\d!@#\$%^&*]{8,}\$")
        return if (passwordRegex.matches(password)) {
            Pair(true, "")
        } else {
            Pair(
                false,
                "Password must contain at least 1 number and 1 special character (!@#\$%^&*) and be at least 8 characters long"
            )
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String> {
        if (confirmPassword.isEmpty()) {
            return Pair(false, "Confirm password should not be empty")
        }
        return if (password == confirmPassword) {
            Pair(true, "")
        } else {
            Pair(false, "Passwords do not match")
        }
    }
}
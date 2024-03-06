package com.example.neostartask.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAddressViewModel @Inject constructor() : ViewModel() {


    fun validateAddress(address: String): Pair<Boolean, String> {
        if (address.isEmpty()) {
            return Pair(false, "Address should not be empty")
        }
        if (address.length < 3) {
            return Pair(false, "Address should be more than 3 characters")
        }

        return Pair(true, "")
    }

    fun validateLandmark(landmark: String): Pair<Boolean, String> {
        if (landmark.isEmpty()) {
            return Pair(false, "Landmark should not be empty")
        }
        if (landmark.length < 3) {
            return Pair(false, "Landmark should be more than 3 characters")
        }

        return Pair(true, "")
    }

    fun validateCity(city: String): Pair<Boolean, String> {
        if (city.isEmpty()) {
            return Pair(false, "City should not be empty")
        }

        if (city.length < 3) {
            return Pair(false, "Landmark should be more than 3 characters")
        }

        return Pair(true, "")
    }

    fun validateState(state: String): Pair<Boolean, String> {
        if (state.isEmpty()) {
            return Pair(false, "Please select a state")
        }


        return Pair(true, "")
    }

    fun validatePinCode(pinCode: String): Pair<Boolean, String> {
        if (pinCode.isEmpty()) {
            return Pair(false, "City should not be empty")
        }

        val pinCodeRegex = Regex("^\\d{6}$")
        return if (pinCodeRegex.matches(pinCode)) {
            Pair(true, "")
        } else {
            Pair(
                false,
                "Pin Code must be exactly 6 digits"
            )
        }
    }

}
package com.example.neostartask.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfessionalInfoViewModel @Inject constructor() : ViewModel() {
    fun validateExperience(experience: String): Pair<Boolean, String> {
        if (experience.isEmpty()) {
            return Pair(false, "Experience should not be empty")
        }
        return Pair(true, "")

    }

    fun validateDesignation(designation: String): Pair<Boolean, String> {
        if (designation.isEmpty()) {
            return Pair(false, "Designation should not be empty")
        }
        return Pair(true, "")
    }

    fun validateDomain(domain: String): Pair<Boolean, String> {
        if (domain.isEmpty()) {
            return Pair(false, "Domain should not be empty")
        }
        return Pair(true, "")
    }

    fun validateQualification(qualification: String): Pair<Boolean, String> {
        if (qualification.isEmpty()) {
            return Pair(false, "Qualification should not be empty")
        }
        return Pair(true, "")
    }

    fun validateYearOfPassing(yop: String): Pair<Boolean, String> {
        if (yop.isEmpty()) {
            return Pair(false, "Year of passing should not be empty")
        }

        val pinCodeRegex = Regex("^\\d{4}$")
        return if (pinCodeRegex.matches(yop)) {
            Pair(true, "")
        } else {
            Pair(
                false,
                "Year of passing must be exactly 4 digits"
            )
        }
    }

    fun validateUniversity(university: String): Pair<Boolean, String> {
        if (university.isEmpty()) {
            return Pair(false, "University name should not be empty")
        }
        return Pair(true, "")
    }

    fun validateGrade(grade: String): Pair<Boolean, String> {
        if (grade.isEmpty()) {
            return Pair(false, "Grade should not be empty")
        }
        return Pair(true, "")
    }
}
package com.example.neostartask.utils

private val LIST_OF_STATE = listOf<String>(
    "Maharashtra", "Gujarat", "Karnataka", "Madhya Pradesh", "Delhi", "Others"
)

private val LIST_OF_QUALIFICATION = listOf<String>(
    "Post Graduate", "Graduate", "HSC/Diploma", "SSC"
)

fun getStateDropDownData() = LIST_OF_STATE
fun getQualificationDropDownData() = LIST_OF_QUALIFICATION
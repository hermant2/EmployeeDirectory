package com.treyherman.employeedirectory.scenes.maindirectory.model

data class UIEmployee(
    val uuid: String,
    val nameAndTeam: String,
    val phoneNumber: String?,
    val email: String,
    val bio: String?,
    val photoUrl: String?,
    val classification: String
)

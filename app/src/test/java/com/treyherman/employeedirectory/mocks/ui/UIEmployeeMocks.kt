package com.treyherman.employeedirectory.mocks.ui

import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import java.util.*

fun mockUIEmployee(
    uuid: String = UUID.randomUUID().toString(),
    nameAndTeam: String = "Name + Team",
    phoneNumber: String? = "3334445566",
    email: String = "test@user.com",
    bio: String? = "bio",
    photoUrl: String? = "img.png",
    classification: String = "Full time employee"
): UIEmployee {
    return UIEmployee(uuid, nameAndTeam, phoneNumber, email, bio, photoUrl, classification)
}

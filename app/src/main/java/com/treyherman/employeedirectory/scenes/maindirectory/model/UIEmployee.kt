package com.treyherman.employeedirectory.scenes.maindirectory.model

data class UIEmployee(
    val uuid: String,
    val nameAndTeam: String,
    val phoneNumber: String?,
    val email: String,
    val bio: String?,
    val photoUrl: String?,
    val classification: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UIEmployee

        if (uuid != other.uuid) return false
        if (nameAndTeam != other.nameAndTeam) return false
        if (phoneNumber != other.phoneNumber) return false
        if (email != other.email) return false
        if (bio != other.bio) return false
        if (photoUrl != other.photoUrl) return false
        if (classification != other.classification) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameAndTeam.hashCode()
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + (bio?.hashCode() ?: 0)
        result = 31 * result + (photoUrl?.hashCode() ?: 0)
        result = 31 * result + classification.hashCode()
        return result
    }
}

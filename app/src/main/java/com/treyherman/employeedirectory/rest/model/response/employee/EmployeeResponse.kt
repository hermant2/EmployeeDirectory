package com.treyherman.employeedirectory.rest.model.response.employee

data class EmployeeResponse(
    val uuid: String,
    val fullName: String,
    val phoneNumber: String?,
    val emailAddress: String,
    val biography: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val team: String,
    private val employeeType: String
) {
    val type
        get() = Type.enumValueFromString(employeeType)

    enum class Type {
        FULL_TIME,
        PART_TIME,
        CONTRACTOR;

        companion object {
            private val typeValueMap = values().map { it.name to it }.toMap()

            fun enumValueFromString(value: String): Type {
                return typeValueMap[value.toUpperCase()] ?: FULL_TIME
            }
        }
    }
}

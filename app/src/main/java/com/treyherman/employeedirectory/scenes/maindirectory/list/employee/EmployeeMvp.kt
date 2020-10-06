package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

interface EmployeeMvp {
    interface View {
        fun displayEmployeeInfo(
            nameAndTeam: String,
            email: String,
            classification: String,
            profileImageUrl: String?
        )

        fun displayPhoneNumber(phoneNumber: String)

        fun hidePhoneNumber()

        fun displayBio(biography: String)

        fun hideBio()
    }

    interface Presenter {
        fun onBind(employee: UIEmployee)
    }
}
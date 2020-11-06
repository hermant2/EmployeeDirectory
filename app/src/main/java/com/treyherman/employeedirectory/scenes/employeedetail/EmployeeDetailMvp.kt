package com.treyherman.employeedirectory.scenes.employeedetail

import android.os.Bundle
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee


interface EmployeeDetailMvp {
    interface View {
        fun displayEmployee(employee: UIEmployee)

        fun displayNonCancelableErrorDialog(message: String)

        fun finish()

        fun openDialScreen(phoneNumber: String)

        fun openEmailScreen(email: String)
    }

    interface Presenter {
        fun onCreate(extras: Bundle?)

        fun onNonCancelableErrorDialogDismissed()

        fun onCallClicked()

        fun onEmailClicked()
    }
}

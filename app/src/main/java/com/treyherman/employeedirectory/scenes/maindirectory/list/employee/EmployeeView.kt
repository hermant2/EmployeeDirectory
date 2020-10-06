package com.treyherman.employeedirectory.scenes.maindirectory.list.employee

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import coil.ImageLoader
import coil.load
import coil.util.CoilUtils
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee
import dagger.android.AndroidInjector
import kotlinx.android.synthetic.main.item_employee.view.*
import okhttp3.OkHttpClient
import javax.inject.Inject

class EmployeeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle), EmployeeMvp.View {

    @Inject
    lateinit var presenter: EmployeeMvp.Presenter

    @Inject
    lateinit var imageLoader: ImageLoader

    fun inject(injector: AndroidInjector<EmployeeView>) {
        injector.inject(this)
    }

    fun bind(employee: UIEmployee) {
        presenter.onBind(employee)
    }

    override fun displayEmployeeInfo(
        nameAndTeam: String,
        email: String,
        classification: String,
        profileImageUrl: String?
    ) {
        tvNameAndTeam.text = nameAndTeam
        tvEmail.text = email
        tvEmployeeClassification.text = classification
        ivEmployee.load(profileImageUrl, imageLoader) {
            placeholder(R.drawable.ic_profile_placeholder)
        }
    }

    override fun displayPhoneNumber(phoneNumber: String) {
        tvPhoneNumber.text = phoneNumber
        tvPhoneNumber.visibility = VISIBLE
        tvPhoneNumberLabel.visibility = VISIBLE
    }

    override fun hidePhoneNumber() {
        tvPhoneNumber.visibility = GONE
        tvPhoneNumberLabel.visibility = GONE
    }

    override fun displayBio(biography: String) {
        tvBiography.text = biography
        tvBiography.visibility = VISIBLE
        tvBiographyLabel.visibility = VISIBLE
    }

    override fun hideBio() {
        tvBiography.visibility = GONE
        tvBiographyLabel.visibility = GONE
    }


}

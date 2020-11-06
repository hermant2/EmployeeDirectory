package com.treyherman.employeedirectory.scenes.employeedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.load
import com.jakewharton.rxbinding3.view.clicks
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.extension.createPhoneIntent
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_employee_detail.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EmployeeDetailActivity : AppCompatActivity(), EmployeeDetailMvp.View {

    companion object {
        const val KEY_EMPLOYEE_PARCELABLE = "employee"

        fun createIntent(context: Context, employee: UIEmployee): Intent =
            Intent(context, EmployeeDetailActivity::class.java)
                .putExtra(KEY_EMPLOYEE_PARCELABLE, employee)
    }

    @Inject
    lateinit var presenter: EmployeeDetailMvp.Presenter

    @Inject
    lateinit var imageLoader: ImageLoader

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        setupView()
        presenter.onCreate(intent.extras)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun displayEmployee(employee: UIEmployee) {
        ivEmployee.load(employee.photoUrlLarge, imageLoader) {
            placeholder(R.drawable.ic_profile_placeholder)
        }
        tvNameAndTeam.text = employee.nameAndTeam
        employee.phoneNumber?.let {
            tvPhoneNumber.text = it
            tvPhoneNumber.visibility = View.VISIBLE
            btCall.visibility = View.VISIBLE
        } ?: run {
            tvPhoneNumber.visibility = View.GONE
            btCall.visibility = View.GONE
        }

        tvEmail.text = employee.email

        employee.phoneNumber?.let {
            tvBio.text = it
            tvBio.visibility = View.VISIBLE
        } ?: run { tvBio.visibility = View.GONE }

        tvClassification.text = employee.classification
    }

    override fun displayNonCancelableErrorDialog(message: String) {

    }

    override fun openDialScreen(phoneNumber: String) {
        startActivity(createPhoneIntent(phoneNumber))
    }

    override fun openEmailScreen(email: String) {
        TODO("Not yet implemented")
    }

    // region private
    private fun setupView() {
        subscribeToCallClickEvent()
        subscribeToEmailClickEvent()
    }

    private fun subscribeToCallClickEvent() {
        btCall.clicks()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.onCallClicked()
            }.addTo(disposables)
    }

    private fun subscribeToEmailClickEvent() {
        btEmail.clicks()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.onEmailClicked()
            }.addTo(disposables)
    }
    // endregion private
}

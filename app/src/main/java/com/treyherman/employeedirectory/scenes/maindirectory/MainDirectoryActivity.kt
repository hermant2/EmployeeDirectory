package com.treyherman.employeedirectory.scenes.maindirectory

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.scenes.maindirectory.list.EmployeeAdapter
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeSubcomponent
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main_directory.*
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import javax.inject.Provider

class MainDirectoryActivity : AppCompatActivity(), MainDirectoryMvp.View {

    @Inject
    lateinit var presenter: MainDirectoryMvp.Presenter

    @Inject
    lateinit var employeeSubcomponentFactoryProvider: Provider<EmployeeSubcomponent.Factory>

    private val disposables = CompositeDisposable()
    private var errorDialog: AlertDialog? = null

    private val adapter by lazy {
        EmployeeAdapter(this, employeeSubcomponentFactoryProvider.get())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_directory)
        presenter.onCreate()
        setupView()
    }

    override fun onDestroy() {
        disposables.dispose()
        errorDialog?.dismiss()
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun displayEmployees(employees: List<UIEmployee>) {
        adapter.setData(employees)
    }

    override fun displayErrorDialog(title: String, message: String) {
        errorDialog?.dismiss()
        errorDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.ok, null)
            .show()
    }

    override fun displayLoading() {
        vProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vProgress.visibility = View.GONE
        vRefresh.isRefreshing = false
    }

    // region private
    private fun setupView() {
        rvEmployees.adapter = adapter
        subscribeToRefreshEvents()
    }

    private fun subscribeToRefreshEvents() {
        vRefresh.refreshes().subscribe {
            presenter.onRefresh()
        }.addTo(disposables)
    }
    // endregion private
}

package com.treyherman.employeedirectory.scenes.maindirectory

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.itemSelections
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.scenes.employeedetail.EmployeeDetailActivity
import com.treyherman.employeedirectory.scenes.maindirectory.list.EmployeeAdapter
import com.treyherman.employeedirectory.scenes.maindirectory.list.employee.EmployeeSubcomponent
import com.treyherman.employeedirectory.scenes.maindirectory.model.DataSelectionType
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

    private val rvAdapter by lazy {
        EmployeeAdapter(this, employeeSubcomponentFactoryProvider.get())
    }

    private val tvEmptyContentMessage by lazy {
        vEmptyContent.findViewById<TextView>(R.id.tvMessage)
    }

    private val btTryAgain by lazy {
        vEmptyContent.findViewById<Button>(R.id.btTryAgain)
    }

    private val spinnerAdapter by lazy {
        ArrayAdapter<DataSelectionType>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            DataSelectionType.values()
        )
    }

    private val spinnerDataSelection
        get() = spinnerAdapter.getItem(vSpinner.selectedItemPosition) ?: DataSelectionType.DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_directory)
        presenter.onCreate()
        setupView()
    }

    override fun onDestroy() {
        disposables.dispose()
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun displayEmployees(employees: List<UIEmployee>) {
        vEmptyContent.visibility = View.GONE
        rvEmployees.visibility = View.VISIBLE
        rvAdapter.setData(employees)
    }

    override fun updateEmployees(employees: List<UIEmployee>, diffResult: DiffUtil.DiffResult) {
        vEmptyContent.visibility = View.GONE
        rvEmployees.visibility = View.VISIBLE
        rvAdapter.setDataQuietly(employees)
        diffResult.dispatchUpdatesTo(rvAdapter)
    }

    override fun displayEmptyContent(message: String) {
        rvEmployees.visibility = View.GONE
        vEmptyContent.visibility = View.VISIBLE
        tvEmptyContentMessage.text = message
        rvAdapter.setData(emptyList())
    }

    override fun displayLoading() {
        vProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vProgress.visibility = View.GONE
        vRefresh.isRefreshing = false
    }

    override fun openEmployeeDetails(employee: UIEmployee) {
        startActivity(EmployeeDetailActivity.createIntent(this, employee))
    }

    // region private
    private fun setupView() {
        rvEmployees.adapter = rvAdapter
        vSpinner.adapter = spinnerAdapter
        subscribeToTryAgainClickEvents()
        subscribeToSpinnerSelectionEvents()
        subscribeToRefreshEvents()
    }

    private fun subscribeToTryAgainClickEvents() {
        btTryAgain.clicks().subscribe {
            presenter.onTryAgainClicked(spinnerDataSelection, rvAdapter.data)
        }.addTo(disposables)
    }

    private fun subscribeToRefreshEvents() {
        vRefresh.refreshes().subscribe {
            presenter.onRefresh(spinnerDataSelection, rvAdapter.data)
        }.addTo(disposables)
    }

    private fun subscribeToSpinnerSelectionEvents() {
        vSpinner.itemSelections().subscribe {
            presenter.onDataTypeSelected(spinnerDataSelection, rvAdapter.data)
        }.addTo(disposables)
    }
    // endregion private
}

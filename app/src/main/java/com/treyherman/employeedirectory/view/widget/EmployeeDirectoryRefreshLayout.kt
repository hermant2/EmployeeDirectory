package com.treyherman.employeedirectory.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.treyherman.employeedirectory.R
import com.treyherman.employeedirectory.extension.findColor

class EmployeeDirectoryRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {
    init {
        setColorSchemeColors(
            resources.findColor(R.color.color_blue),
            resources.findColor(R.color.color_red)
        )
    }
}

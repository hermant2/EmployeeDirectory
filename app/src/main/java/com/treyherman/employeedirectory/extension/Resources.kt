package com.treyherman.employeedirectory.extension

import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

fun Resources.findColor(@ColorRes resourceId: Int, theme: Resources.Theme? = null): Int {
    return ResourcesCompat.getColor(this, resourceId, theme)
}

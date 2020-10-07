package com.treyherman.employeedirectory.scenes.maindirectory.diff

import androidx.recyclerview.widget.DiffUtil
import com.treyherman.employeedirectory.scenes.maindirectory.model.UIEmployee

class EmployeeDiffCallback(
    private val oldItems: List<UIEmployee>,
    private val newItems: List<UIEmployee>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}

package com.treyherman.employeedirectory.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class AbsItemsAdapter<T, VH : AbsViewHolder<out T>>(context: Context) :
    RecyclerView.Adapter<VH>() {

    protected val inflater = LayoutInflater.from(context)
    protected val data = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewId: Int): VH {
        val view = inflater.inflate(viewId, parent, false)
        return createViewHolderFromView(view = view, viewId = viewId)
    }

    override fun getItemViewType(position: Int): Int {
        return itemLayoutResource(position = position)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        if (position < data.size) {
            onBind(viewHolder, data[position], position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(list: List<out T>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun setDataQuietly(list: List<out T>) {
        data.clear()
        data.addAll(list)
    }

    abstract fun createViewHolderFromView(view: View, @LayoutRes viewId: Int): VH

    @LayoutRes
    abstract fun itemLayoutResource(position: Int): Int

    abstract fun onBind(viewHolder: VH, data: T, position: Int)
}
package com.aminography.primedatepicker.calendarview.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.aminography.primedatepicker.calendarview.callback.IMonthViewHolderCallback
import com.aminography.primedatepicker.calendarview.dataholder.MonthDataHolder
import com.aminography.primedatepicker.calendarview.other.SkipDividerItemDecorator
import com.aminography.primedatepicker.calendarview.viewholder.MonthViewHolder
import com.aminography.primedatepicker.monthview.PrimeMonthView
import com.aminography.primedatepicker.tools.dp2px

/**
 * @author aminography
 */
class MonthListAdapter(
        private val recyclerView: RecyclerView
) : RecyclerView.Adapter<MonthViewHolder>() {

    var iMonthViewHolderCallback: IMonthViewHolderCallback? = null
    private var dataList = mutableListOf<MonthDataHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val monthView = PrimeMonthView(recyclerView.context).apply {
            layoutParams = parent.layoutParams
            val padding = recyclerView.context.dp2px(16f)
            setPadding(padding, padding, padding, padding) // Default padding
        }
        return MonthViewHolder(monthView, iMonthViewHolderCallback)
    }

    override fun onBindViewHolder(viewHolder: MonthViewHolder, position: Int) {
        val dataHolder = dataList[position]
        dataHolder.listPosition = position
        viewHolder.bindDataToView(dataHolder)
    }

    private fun setDivider(
            dividerDrawable: Drawable?,
            insetLeft: Int = 0,
            insetTop: Int = 0,
            insetRight: Int = 0,
            insetBottom: Int = 0
    ) {
        val itemDecorationCount = recyclerView.itemDecorationCount
        if (itemDecorationCount > 0) {
            recyclerView.removeItemDecorationAt(0)
        }
        if (recyclerView.layoutManager is LinearLayoutManager) {
            dividerDrawable?.let {
                val insetDrawable = InsetDrawable(it, insetLeft, insetTop, insetRight, insetBottom)
                val dividerItemDecoration = SkipDividerItemDecorator(insetDrawable)
                recyclerView.addItemDecoration(dividerItemDecoration)
            }
        }
    }

    fun setDivider(
            @ColorInt color: Int = Color.parseColor("#BDBDBD"),
            thickness: Int = 1,
            insetLeft: Int = 0,
            insetTop: Int = 0,
            insetRight: Int = 0,
            insetBottom: Int = 0
    ) {
        val dividerDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setSize(thickness, thickness)
            setColor(color)
        }
        setDivider(dividerDrawable, insetLeft, insetTop, insetRight, insetBottom)
    }

    override fun getItemCount(): Int = dataList.size

    fun getItem(position: Int): MonthDataHolder = dataList[position]

    fun replaceDataList(list: List<MonthDataHolder>) {
        dataList = list as MutableList<MonthDataHolder>
        notifyDataSetChanged()
    }

}

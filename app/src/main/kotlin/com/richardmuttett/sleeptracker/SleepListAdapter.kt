package com.richardmuttett.sleeptracker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.sleep_item.view.date
import kotlinx.android.synthetic.main.sleep_item.view.efficiency
import kotlinx.android.synthetic.main.sleep_item.view.separator
import java.text.SimpleDateFormat
import java.util.Locale

class SleepListAdapter(val presenter: MainPresenter): RecyclerView.Adapter<SleepListAdapter.ViewHolder>() {
  val fitbitDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
  val longDateFormatter = SimpleDateFormat("E dd MMM yyyy", Locale.US)

  class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val date: TextView = itemView.date
    val efficiency: TextView = itemView.efficiency
    val separator: View = itemView.separator
  }

  override fun getItemCount() = presenter.sleepArray.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sleep_item, parent, false))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) = bindView(holder, position)

  fun bindView(holder: ViewHolder, position: Int) {
    with(presenter.sleepArray[position]) {
      val date = fitbitDateFormatter.parse(dateOfSleep)
      holder.date.text = holder.itemView.context.getString(R.string.date_string, longDateFormatter.format(date), longDateFormatter.format(date.nextDay()))
      holder.efficiency.text = holder.itemView.context.getString(R.string.efficiency, efficiency)
    }

    holder.separator.visibility = if (position < presenter.sleepArray.size - 1) View.VISIBLE else View.GONE
  }
}

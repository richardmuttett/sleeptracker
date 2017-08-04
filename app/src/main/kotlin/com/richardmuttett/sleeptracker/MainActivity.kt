package com.richardmuttett.sleeptracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.richardmuttett.sleeptracker.model.Sleep
import kotlinx.android.synthetic.main.activity_main.sleepList

class MainActivity: Activity(), Main.View {

  companion object {
    private const val KEY_SLEEP = "KEY_SLEEP"

    fun intent(context: Context) = Intent(context, MainActivity::class.java)
  }

  val presenter = MainPresenter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    startService(SchedulerService.intent(this))
    initializeView(savedInstanceState)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putParcelableArray(KEY_SLEEP, presenter.sleepArray)
  }

  override fun onDestroy() {
    presenter.destroy()
    super.onDestroy()
  }

  override fun refreshList() = sleepList.adapter.notifyDataSetChanged()

  override fun showError(ex: Throwable) {
    Toast.makeText(this, R.string.unable_to_get_data, Toast.LENGTH_LONG).show()
  }

  private fun initializeView(savedInstanceState: Bundle?) {
    sleepList.adapter = SleepListAdapter(presenter)
    sleepList.layoutManager = LinearLayoutManager(this)

    if (savedInstanceState != null) {
      @Suppress("UNCHECKED_CAST")
      presenter.sleepArray = savedInstanceState.getParcelableArray(KEY_SLEEP) as Array<Sleep>
      refreshList()
    } else {
      presenter.fetchSleepData()
    }
  }
}

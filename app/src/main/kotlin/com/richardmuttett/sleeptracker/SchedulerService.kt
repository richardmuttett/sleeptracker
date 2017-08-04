package com.richardmuttett.sleeptracker

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class SchedulerService: IntentService("SchedulerService") {

  companion object {
    private const val HOUR_OF_DAY = 7
    private const val MINUTE = 45
    private const val SECOND = 0
    private const val TRY_AGAIN_INTERVAL = 15

    fun intent(context: Context) = Intent(context, SchedulerService::class.java)
  }

  override fun onHandleIntent(intent: Intent) {
    with(getSystemService(Context.ALARM_SERVICE) as AlarmManager) {
      cancel(buildPendingIntent())
      setExact(AlarmManager.RTC_WAKEUP, calculateTriggerTime(), buildPendingIntent())
    }
  }

  private fun calculateTriggerTime(): Long {
    with(Calendar.getInstance()) {
      if (hasNotificationBeenShownToday(this)) {
        set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
        set(Calendar.MINUTE, MINUTE)
        set(Calendar.SECOND, SECOND)
        add(Calendar.DAY_OF_YEAR, 1)
      } else {
        val triggerTimeForToday = calculateTriggerTimeForToday()

        if (triggerTimeForToday.before(this)) {
          add(Calendar.MINUTE, TRY_AGAIN_INTERVAL)
        } else {
          set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
          set(Calendar.MINUTE, MINUTE)
          set(Calendar.SECOND, SECOND)
        }
      }

      return time.time
    }
  }

  private fun calculateTriggerTimeForToday(): Calendar {
    with(Calendar.getInstance()) {
      set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY)
      set(Calendar.MINUTE, MINUTE)
      set(Calendar.SECOND, SECOND)
      return this
    }
  }

  private fun hasNotificationBeenShownToday(now: Calendar): Boolean {
    with(Calendar.getInstance()) {
      time = getLastNotificationDate()
      return now.isSameDay(this)
    }
  }

  private fun buildPendingIntent() = PendingIntent.getBroadcast(this, 0, AlarmReceiver.intent(this), PendingIntent.FLAG_UPDATE_CURRENT)
}

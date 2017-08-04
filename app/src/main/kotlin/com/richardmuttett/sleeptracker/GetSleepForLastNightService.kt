package com.richardmuttett.sleeptracker

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.richardmuttett.sleeptracker.api.FitbitApi
import com.richardmuttett.sleeptracker.model.Sleep
import com.richardmuttett.sleeptracker.model.SleepResponse
import java.util.Date

class GetSleepForLastNightService: IntentService("GetSleepForLastNightService") {

  companion object {
    private const val NOTIFICATION_ID = 1

    fun intent(context: Context) = Intent(context, GetSleepForLastNightService::class.java)
  }

  override fun onHandleIntent(intent: Intent) {
    fetchSleep()
    AlarmReceiver.completeWakefulIntent(intent)
  }

  private fun fetchSleep() = FitbitApi()
      .getSleep(buildStartDate(offset = -1), limit = 100)
      .doAfterTerminate { startService(SchedulerService.intent(this)) }
      .subscribe(this::onGotSleep)

  private fun onGotSleep(sleepResponse: SleepResponse) {
    with(sleepResponse.sleep) {
      if (isNotEmpty()) {
        showNotification(last())
        setLastNotificationDate(Date())
      }
    }

    startService(SchedulerService.intent(this))
  }

  private fun showNotification(sleep: Sleep) {
    val notification = NotificationCompat.Builder(this)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_body, sleep.efficiency))
        .setSmallIcon(R.drawable.ic_notification)
        .setContentIntent(PendingIntent.getActivity(this, 1, MainActivity.intent(this), 0))
        .build()

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
    notification.defaults = notification.defaults or Notification.DEFAULT_SOUND
    notification.defaults = notification.defaults or Notification.DEFAULT_VIBRATE
    notificationManager.notify(NOTIFICATION_ID, notification)
  }
}

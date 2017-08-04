package com.richardmuttett.sleeptracker

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

open class AlarmReceiver: WakefulBroadcastReceiver() {
  companion object {
    fun intent(context: Context) = Intent(context, AlarmReceiver::class.java)
    fun completeWakefulIntent(intent: Intent) = WakefulBroadcastReceiver.completeWakefulIntent(intent)
  }

  override fun onReceive(context: Context, intent: Intent) {
    startWakefulService(context, GetSleepForLastNightService.intent(context))
  }
}

package com.richardmuttett.sleeptracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    context.startService(SchedulerService.intent(context))
  }
}

package com.richardmuttett.sleeptracker

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Calendar.isSameDay(calendar: Calendar) = get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)

fun Calendar.formattedDate(): String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(time)

fun buildStartDate(offset: Int): String {
  with(Calendar.getInstance()) {
    add(Calendar.DAY_OF_YEAR, offset)
    return formattedDate()
  }
}

fun Date.nextDay(): Date {
  val calendar = Calendar.getInstance()
  calendar.time = this
  calendar.add(Calendar.DAY_OF_YEAR, 1)
  return calendar.time
}

private const val PREFERENCES = "PREFERENCES"
private const val LAST_NOTIFICATION_DATE = "LAST_NOTIFICATION_DATE"

fun Context.getLastNotificationDate(): Date {
  val preferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
  val time = preferences.getLong(LAST_NOTIFICATION_DATE, 0)
  return Date(time)
}

fun Context.setLastNotificationDate(date: Date) {
  val editor = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).edit()
  editor.putLong(LAST_NOTIFICATION_DATE, date.time).apply()
}

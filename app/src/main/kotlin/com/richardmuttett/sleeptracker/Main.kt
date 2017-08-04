package com.richardmuttett.sleeptracker

interface Main {
  interface View {
    fun refreshList()
    fun showError(ex: Throwable)
  }

  interface Presenter {
    fun fetchSleepData()
    fun destroy()
  }
}

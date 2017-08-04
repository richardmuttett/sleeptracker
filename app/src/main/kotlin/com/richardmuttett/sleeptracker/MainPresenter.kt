package com.richardmuttett.sleeptracker

import com.richardmuttett.sleeptracker.api.FitbitApi
import com.richardmuttett.sleeptracker.model.Sleep
import com.richardmuttett.sleeptracker.model.SleepResponse
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MainPresenter(private val view: Main.View): Main.Presenter {

  companion object {
    private const val START_DATE_OFFSET = -60
    private const val PAGE_LIMIT = 100
  }

  var sleepArray = emptyArray<Sleep>()
  private val subscriptions = CompositeSubscription()

  override fun fetchSleepData() {
    subscriptions.add(FitbitApi().getSleep(buildStartDate(START_DATE_OFFSET), PAGE_LIMIT)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onSuccess, this::onError))
  }

  private fun onSuccess(sleepResponse: SleepResponse) {
    sleepArray = sleepResponse.sleep
    view.refreshList()
  }

  private fun onError(ex: Throwable) = view.showError(ex)

  override fun destroy() = subscriptions.unsubscribe()
}

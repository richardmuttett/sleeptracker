package com.richardmuttett.sleeptracker.api

import com.richardmuttett.sleeptracker.model.SleepResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface FitbitService {
  @GET("/1.2/user/-/sleep/list.json")
  fun getSleep(@Query("afterDate") afterDate: String, @Query("limit") limit: Int, @Query("offset") offset: Int = 1, @Query("sort") sort: String = "desc"): Observable<SleepResponse>
}

package com.tejachodavarapu.holdingsdemo.data.remote.api

import com.tejachodavarapu.holdingsdemo.data.remote.dto.HoldingsResponse
import retrofit2.http.GET

interface HoldingsApi {
    @GET("/")
    suspend fun getUserHoldings(): HoldingsResponse
}

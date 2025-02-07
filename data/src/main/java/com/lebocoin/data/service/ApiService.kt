package com.lebocoin.data.service

import com.lebocoin.data.model.InformationDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("img/shared/technical-test.json")
    suspend fun getInformation(): Response<List<InformationDTO>>

}
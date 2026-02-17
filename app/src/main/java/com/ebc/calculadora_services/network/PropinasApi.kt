package com.ebc.calculadora_services.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PropinasApi {

    @GET("propinas/random")
    suspend fun propinaRandom(
        @Query("cantidad") cantidad: Double
    ) : Response<String>
}
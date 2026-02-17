package com.ebc.calculadora_services.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object Network {

    private const val BASE_URL = "https://loteriasvarias.onrender.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    val propinasApi: PropinasApi by lazy {
        retrofit.create(PropinasApi::class.java)
    }
}
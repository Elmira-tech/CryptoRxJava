package com.elmiramammadova.cryptorxjava.service

import com.elmiramammadova.cryptorxjava.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {
    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData() :Observable<List<CryptoModel>>
}
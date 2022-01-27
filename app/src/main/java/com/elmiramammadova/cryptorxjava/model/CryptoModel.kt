package com.elmiramammadova.cryptorxjava.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    @SerializedName("currency")
    val name: String,
    val price: String
)
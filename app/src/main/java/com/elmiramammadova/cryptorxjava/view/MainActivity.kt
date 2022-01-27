package com.elmiramammadova.cryptorxjava.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elmiramammadova.cryptorxjava.R
import com.elmiramammadova.cryptorxjava.adapter.CryptoAdapter
import com.elmiramammadova.cryptorxjava.model.CryptoModel
import com.elmiramammadova.cryptorxjava.service.CryptoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),CryptoAdapter.Listener {
    private val BASE_URL="https://raw.githubusercontent.com/"
    private var cryptoAdapter:CryptoAdapter?=null
    private var cryptoModels : ArrayList<CryptoModel>?=null
    private lateinit var compositeDisposable:CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
        compositeDisposable= CompositeDisposable()
        loadData()
    }

    fun loadData(){
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CryptoApi::class.java)
            .getData()

        compositeDisposable.add(retrofit
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handlerResponse))

   /*     retrofit.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels=ArrayList(it)
                        cryptoModels?.let {
                            for (cryptoModel : CryptoModel in cryptoModels!!) {
                                println(cryptoModel.name)
                                println(cryptoModel.price)
                            }
                            cryptoAdapter= CryptoAdapter(it,this@MainActivity)
                            recyclerView.adapter=cryptoAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })*/
    }

    fun handlerResponse(cryptoList:List<CryptoModel>){
        cryptoModels=ArrayList(cryptoList)
        cryptoModels?.let {
            cryptoAdapter= CryptoAdapter(it,this@MainActivity)
            recyclerView.adapter=cryptoAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked: ${cryptoModel.name}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
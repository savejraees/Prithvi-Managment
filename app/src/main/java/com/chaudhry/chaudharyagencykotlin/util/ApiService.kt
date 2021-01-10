package com.chaudhry.chaudharyagencykotlin.util

import com.chaudhry.chaudharyagencykotlin.model.DetailModel
import com.chaudhry.chaudharyagencykotlin.model.HomeModel
import com.chaudhry.chaudharyagencykotlin.model.LoginModel
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("device_id") device_id: String,
        @Field("mobile") email: String,
        @Field("password") password: String,
    ): Call<LoginModel>

    @FormUrlEncoded
    @POST("check-active")
    fun postCheck(
        @Field("user_id") user_id: String,
    ): Call<LoginModel>


    @GET("details")
    fun getDetail(
        @Query("vehicle_id") vehicle_id: String,
    ): Call<DetailModel>


    @GET("search")
    fun getHome(
    ) : Call<HomeModel>

}
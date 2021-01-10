package com.chaudhry.chaudharyagencykotlin.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: Data? = null
    @JvmName("getStatus1")
    fun getStatus(): String? {
        return status
    }

    @JvmName("setStatus1")
    fun setStatus(status: String?) {
        this.status = status
    }

    @JvmName("getMessage1")
    fun getMessage(): String? {
        return message
    }

    @JvmName("setMessage1")
    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): Data? {
        return data
    }

    fun setData(data: Data?) {
        this.data = data
    }

    class Data {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mobile")
        @Expose
        var mobile: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}
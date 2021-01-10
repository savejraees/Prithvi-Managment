package com.chaudhry.chaudharyagencykotlin.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DetailModel {

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
        var id: Int? = null

        @SerializedName("loan_no")
        @Expose
        var loanNo: String? = null

        @SerializedName("asset_bajaj_vehicle")
        @Expose
        var assetBajajVehicle: String? = null

        @SerializedName("regno")
        @Expose
        var regno: String? = null

        @SerializedName("engineno")
        @Expose
        var engineno: String? = null

        @SerializedName("chassino")
        @Expose
        var chassino: String? = null

        @SerializedName("cust_name")
        @Expose
        var custName: String? = null

        @SerializedName("father_nm")
        @Expose
        var fatherNm: String? = null

        @SerializedName("sheet_no")
        @Expose
        var sheetNo: String? = null
    }
}
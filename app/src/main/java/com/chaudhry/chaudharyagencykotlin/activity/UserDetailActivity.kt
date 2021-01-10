package com.chaudhry.chaudharyagencykotlin.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaudhry.chaudharyagencykotlin.R
import com.chaudhry.chaudharyagencykotlin.model.DetailModel
import com.chaudhry.chaudharyagencykotlin.util.ApiClient
import kotlinx.android.synthetic.main.activity_user_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    private lateinit var progressDilog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.hide()
        progressDilog = ProgressDialog(this);
        progressDilog.setCancelable(false)
        progressDilog.setTitle("Wait....")

        val id  =  intent.getStringExtra("id")
       // Log.d("adsjkwqww",id.toString())
        hitApi(id)


    }

    private fun hitApi(id: String?) {
        progressDilog.show()
        val call: Call<DetailModel> = ApiClient.getClient.getDetail(id.toString())
        call.enqueue(object : Callback<DetailModel> {
            override fun onResponse(call: Call<DetailModel>, response: Response<DetailModel>) {
                if (response.isSuccessful) {
                    progressDilog.dismiss()
                    val statusModel: DetailModel? = response.body()
                    var msg = statusModel?.message
                    var status = statusModel?.status

                    if (status.equals("success")) {
//                        val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
//                        toast.show()

                        var dataMOdel: DetailModel.Data? = statusModel?.getData()

                        tv_serviceName.setText(dataMOdel?.custName)
                        tv_fname.setText(dataMOdel?.fatherNm)
                        tv_chessNo.setText(dataMOdel?.chassino)
                        tv_engineno.setText(dataMOdel?.engineno)
                        tv_registration.setText(dataMOdel?.regno)
                        tv_asset_bajaj_vechele.setText(dataMOdel?.assetBajajVehicle)
                        tv_loanNo.setText(dataMOdel?.loanNo)



                    } else {
                        val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            }

            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                progressDilog.dismiss()
                val toast = Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_LONG)
                toast.show()
            }

        })
    }
}
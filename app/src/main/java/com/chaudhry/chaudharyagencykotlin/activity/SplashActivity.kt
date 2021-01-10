package com.chaudhry.chaudharyagencykotlin.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.chaudhry.chaudharyagencykotlin.R
import com.chaudhry.chaudharyagencykotlin.model.LoginModel
import com.chaudhry.chaudharyagencykotlin.util.ApiClient
import com.chaudhry.chaudharyagencykotlin.util.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var progressDilog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
         supportActionBar?.hide()
        progressDilog = ProgressDialog(this);
        progressDilog.setCancelable(false)
        progressDilog.setTitle("Wait....")

        var shared : SharedPrefs = SharedPrefs.getInstance(this)!!
        var token = shared.uSerId

        Handler().postDelayed({
            if(token.toString().isEmpty()){
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                finishAffinity()
            }else{
//                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
//                finishAffinity()
                hitCheckAvailabilty(token);
            }

        },4000)
    }

    private fun hitCheckAvailabilty(token: String?) {
        Log.d("salkslada",token.toString())
        progressDilog.show()
        val call: Call<LoginModel> = ApiClient.getClient.postCheck(token.toString())
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    progressDilog.dismiss()
                    val statusModel: LoginModel? = response.body()
                    var msg = statusModel?.message
                    var status = statusModel?.status

                    if (status.equals("success")) {
//                        val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
//                        toast.show()

                        var dataMOdel: LoginModel.Data? = statusModel?.getData()

                        var token = dataMOdel?.id
                        var shared = SharedPrefs.getInstance(this@SplashActivity)
                        shared?.uSerId = token.toString()

                        var intent = Intent(this@SplashActivity, MainActivity::class.java)
                        //  intent.putExtra("Username","John Doe")
                        startActivity(intent)

                    } else {
                        val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
                        toast.show()

                        var intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                progressDilog.dismiss()
                val toast = Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_LONG)
                toast.show()
            }

        })
    }
}
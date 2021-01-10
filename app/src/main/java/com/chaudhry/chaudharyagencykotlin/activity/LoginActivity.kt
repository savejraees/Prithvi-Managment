package com.chaudhry.chaudharyagencykotlin.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import com.chaudhry.chaudharyagencykotlin.R
import com.chaudhry.chaudharyagencykotlin.model.LoginModel
import com.chaudhry.chaudharyagencykotlin.util.ApiClient
import com.chaudhry.chaudharyagencykotlin.util.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var et_user_name_login: EditText
    private lateinit var et_password_login: EditText
    private lateinit var btnLinkToLoginScreen: Button
    private lateinit var progressDilog: ProgressDialog

    val messgae = "Can't be blank"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        et_user_name_login = findViewById(R.id.et_mobile);
        et_password_login = findViewById(R.id.et_password);
        btnLinkToLoginScreen = findViewById(R.id.button_signIn);
        progressDilog = ProgressDialog(this);
        progressDilog.setCancelable(false)
        progressDilog.setTitle("Wait....")

         var android_id : String = Settings.Secure.getString(getContentResolver(),
             Settings.Secure.ANDROID_ID);

        Log.d("salkasjasksj",android_id);

        btnLinkToLoginScreen.setOnClickListener {
            if(et_user_name_login.text.toString().trim().isEmpty()){
                et_user_name_login.error = messgae
            }
            else if(et_password_login.text.toString().trim().isEmpty()){
                et_password_login.error = messgae
            }else{
                hitLoginApi(android_id);
            }
        }
    }

    private fun hitLoginApi(id : String) {
        progressDilog.show()
        val call: Call<LoginModel> = ApiClient.getClient.postLogin(id,
            et_user_name_login.text.toString(),
            et_password_login.text.toString()
        )
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.isSuccessful) {
                    progressDilog.dismiss()
                    val statusModel: LoginModel? = response.body()
                    val msg = statusModel?.message
                    val status = statusModel?.status

                    statusModel.let {
                        if (status.equals("success")) {
                            val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
                            toast.show()

                            val dataMOdel: LoginModel.Data? = statusModel?.getData()


                            if (dataMOdel != null) {
                                val token: String? = dataMOdel?.id
                                Log.d("sadaskjdas", token.toString())

                                val shared = SharedPrefs.getInstance(this@LoginActivity)
                                shared?.uSerId = token

                                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                //  intent.putExtra("Username","John Doe")
                                startActivity(intent)

                            }


                        } else {
                            val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_LONG)
                            toast.show()
                        }
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
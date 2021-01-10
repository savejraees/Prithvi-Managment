package com.chaudhry.chaudharyagencykotlin.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.chaudhry.chaudharyagencykotlin.R
import com.chaudhry.chaudharyagencykotlin.adapter.HomeAdapter
import com.chaudhry.chaudharyagencykotlin.model.HomeModel
import com.chaudhry.chaudharyagencykotlin.util.ApiClient
import com.chaudhry.chaudharyagencykotlin.util.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var txtLogOut : TextView
    private lateinit var shared: SharedPrefs
    private lateinit var rvSearch: RecyclerView
    private lateinit var editSearch: EditText
    private lateinit var editSearchNumber: EditText
    private lateinit var listHome: ArrayList<HomeModel.Datum>
    lateinit var adapter: HomeAdapter
    private lateinit var progressDilog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        shared = SharedPrefs.getInstance(this@MainActivity)!!
        rvSearch = findViewById(R.id.rvSearch);
        editSearch = findViewById(R.id.editSearch);
        editSearchNumber = findViewById(R.id.editSearchNumber);
        txtLogOut = findViewById(R.id.txtLogOut);
        progressDilog = ProgressDialog(this);
        progressDilog.setCancelable(false)
        progressDilog.setTitle("Wait....")
         
        hitApi();

        onClick();
    }

    private fun hitApi() {
        progressDilog.show()
        val call: Call<HomeModel> = ApiClient.getClient.getHome()
        call.enqueue(object : Callback<HomeModel> {
            override fun onResponse(call: Call<HomeModel>, response: Response<HomeModel>) {
                if(response.isSuccessful){
                    progressDilog.dismiss()
                    val statusModel: HomeModel? = response.body()
                    var msg  = statusModel?.message
                    var status  = statusModel?.status

                    statusModel.let {
                        if(status.equals("success")){
//                            val toast = Toast.makeText(applicationContext, ""+msg, Toast.LENGTH_LONG)
//                            toast.show()

                            if (statusModel != null) {
                                listHome = statusModel.data
                                setRv();
                            }

                        }
                        else{
                            val toast = Toast.makeText(applicationContext, ""+msg, Toast.LENGTH_LONG)
                            toast.show()
                        }
                    }

                }

            }

            override fun onFailure(call: Call<HomeModel>, t: Throwable) {
                progressDilog.dismiss()
                val toast = Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_LONG)
                toast.show()
            }

        })
    }

    private fun setRv(){
        adapter = HomeAdapter(listHome)
        rvSearch.adapter = adapter

    }

    private fun onClick() {
        txtLogOut.setOnClickListener {
            val builder1 = AlertDialog.Builder(this@MainActivity)
            builder1.setMessage("Are you sure you want to Logout?")
            builder1.setCancelable(false)
            builder1.setPositiveButton(
                "Yes"
            ) { dialog, id ->
                dialog.cancel()
                shared.uSerId = ""
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }

            builder1.setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    return@OnClickListener
                })

            val alert11 = builder1.create()
            alert11.show()
        }

        editSearch.addTextChangedListener(textWatcher)
        editSearchNumber.addTextChangedListener(textWatcher1)
    }

    private fun filter(text: String) {
        val filteredList: java.util.ArrayList<HomeModel.Datum> =
            java.util.ArrayList<HomeModel.Datum>()
        for (item in listHome) {
            if (item.getRegno().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter.filterList(filteredList)
    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            editSearchNumber.getText().clear()

            if(s?.length==0){

            }else{
                filter(s.toString())
            }

            if (s!!.length == 5) {
                editSearch.getText().clear()
            }
        }

    }

    private val textWatcher1 = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            editSearch.getText().clear()

            if(s?.length==0){

            }else{
                filter(s.toString())
            }

            if (s!!.length == 4) {
                editSearchNumber.getText().clear()
            }
        }

    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

}
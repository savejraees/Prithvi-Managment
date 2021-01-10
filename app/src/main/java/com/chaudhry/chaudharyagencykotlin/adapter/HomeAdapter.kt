package com.chaudhry.chaudharyagencykotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.chaudhry.chaudharyagencykotlin.R
import com.chaudhry.chaudharyagencykotlin.activity.UserDetailActivity
import com.chaudhry.chaudharyagencykotlin.model.HomeModel
import kotlinx.android.synthetic.main.rv_home_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(private var regNumberList: ArrayList<HomeModel.Datum>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var regNumberFilterList = ArrayList<HomeModel.Datum>()
    lateinit var mcontext: Context

    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        regNumberFilterList = regNumberList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mcontext = parent.context

        val regNumberListView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_home_item, parent, false)
        val sch = CountryHolder(regNumberListView)
        return sch
    }

    override fun getItemCount(): Int {
        return regNumberFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        
        holder.itemView.tv_name1.text = regNumberFilterList[position].regno
      //  holder.itemView.tv_name2.text = regNumberFilterList[position].id.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, UserDetailActivity::class.java)
            intent.putExtra("id", regNumberFilterList[position].getId().toString())
//            intent.putExtra("getLoan_no", regNumberFilterList[position].loanNo)
//            intent.putExtra("asset_bajaj_vechele", regNumberFilterList[position].assetBajajVechele)
//            intent.putExtra("regno", regNumberFilterList[position].getRegno())
//            intent.putExtra("engineno", regNumberFilterList[position].getEngineno())
//            intent.putExtra("chassino", regNumberFilterList[position].getChassino())
//            intent.putExtra("cust_name", regNumberFilterList[position].custName)
//            intent.putExtra("father_nm", regNumberFilterList[position].fatherNm)
            mcontext.startActivity(intent)
//            Log.d("Selected:", regNumberFilterList[position])
        }
    }


    fun filterList(filteredList: java.util.ArrayList<HomeModel.Datum>) {
        regNumberFilterList = filteredList
        notifyDataSetChanged()
    }
}
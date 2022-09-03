package com.KotlinAss.Activtiy

import Utils.Utils
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.KotlinAss.Adapter.UserAdapter
import com.KotlinAss.App.APIController
import com.KotlinAss.App.ServiceVolley
import com.KotlinAss.Model.UserDetails
import com.KotlinAss.R
import com.KotlinAss.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    val data = ArrayList<UserDetails>()
    private lateinit var recyclerview :RecyclerView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerview = findViewById<RecyclerView>(R.id.recycler)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val bool = Utils.isNetworkAvailable(this)
        if(bool == true){
            Hitapi();
        }
    }

    private fun Hitapi() {
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path = "https://randomuser.me/api/?results=50"
        val params = JSONObject()
        apiController.get(path, params) { response ->
             parseJson(response.toString())

        }
    }
    private fun parseJson(jsonResponse: String){
        data.clear()
        try {
            val jsonObject = JSONObject(jsonResponse)
            var results = jsonObject.getString("results");
            val jsonArray = JSONArray(results)
            for (i in 0 until jsonArray.length()){
                var jso = jsonArray.getJSONObject(i)
                var Gender = jso.getString("gender")
                var jso1 = jso.getJSONObject("name")
                var first = jso1.optString("first")
                Log.e("name","name"+first)
                var last  = jso1.getString("last")
                var jso2 = jso.getJSONObject("location")
                var jso3 = jso2.getJSONObject("street")
                var number = jso3.getString("number")
                var strname = jso3.getString("name")
                var city = jso2.getString("city")
                var state = jso2.getString("state")
                var country = jso2.getString("country")
                var postcode = jso2.getString("postcode")
                var jso4 = jso.getJSONObject("dob")
                var date = jso4.getString("date")
                var age = jso4.getString("age");
                var jso5 = jso.getJSONObject("picture")
                Log.e("jso5","jso5"+jso5)
                var image = jso5.getString("large")
                var mimage = jso5.getString("medium")
                var thumbnail = jso5.getString("thumbnail")
                var name  = first+" "+last

                data.add(UserDetails(image,Gender,first,last,city,state,country))
            }
            val adapter = UserAdapter(data,this)
            recyclerview.adapter = adapter
        }catch (ex:Exception){}
    }
}


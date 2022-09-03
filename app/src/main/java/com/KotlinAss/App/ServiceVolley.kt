package com.KotlinAss.App

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ServiceVolley : ServiceInterface {
    val TAG = ServiceVolley::class.java.simpleName

    override fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST,  path, params,
            Response.Listener<JSONObject> { response ->
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                completionHandler(null)
            }) {}

        BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
    }
  override fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit){
      val jsonObjReq = object : JsonObjectRequest(Method.GET,  path, null,
          Response.Listener<JSONObject> { response ->
              completionHandler(response)
          },
          Response.ErrorListener { error ->
              completionHandler(null)
          }) {}

      BackendVolley.instance?.addToRequestQueue(jsonObjReq, TAG)
  }
}
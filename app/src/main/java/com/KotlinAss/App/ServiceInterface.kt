package com.KotlinAss.App

import org.json.JSONObject

interface ServiceInterface {
    fun post(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
    fun get(path: String, params: JSONObject, completionHandler: (response: JSONObject?) -> Unit)
}
package com.graceful.skyward.api.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.json.simple.JSONObject

object HttpClientUtils {
    private val client = HttpClients.createDefault()
    private val gson = Gson()

    fun post(url: String, jsonObject: JsonObject): HttpResponse {
        val request = HttpPost(url)
        request.entity = StringEntity(gson.toJson(jsonObject), ContentType.APPLICATION_JSON)
        return client.execute(request)
    }

    fun get(url: String): HttpResponse {
        return client.execute(HttpGet(url))
    }

    fun update(url: String, jsonObject: JsonObject): HttpResponse {
        val request = HttpPut(url)
        request.entity = StringEntity(gson.toJson(jsonObject), ContentType.APPLICATION_JSON)
        return client.execute(request)
    }

    fun delete(url: String): HttpResponse {
        return client.execute(HttpDelete(url))
    }

}
package com.graceful.skyward.api.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.graceful.skyward.api.dto.City
import com.graceful.skyward.api.utils.HttpClientUtils
import com.graceful.skyward.gson.GsonUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class SkywardRepository {
    fun getModProperties(): Map<String, String> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/data/properties")).GET().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        return GsonUtils.getMapFromJson(response)
    }

    fun getCities(): List<City> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/cities")).GET().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        val listType = object : TypeToken<List<City?>?>() {}.type
        return Gson().fromJson(response, listType)
    }
}
package com.graceful.skyward.api.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.graceful.skyward.api.utils.HttpClientUtils
import com.graceful.skyward.gson.GsonUtils
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class AttributeRepository {

    fun getPlayerAttributes(uuid: String): MutableMap<String, String> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/player/${uuid}/attributes")).GET().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        return GsonUtils.getMutableMapFromJson(response)
    }

    fun savePlayerAttributes(body: JsonObject) {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/player/persistAttributes"))
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(body))).build()
        client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }
}
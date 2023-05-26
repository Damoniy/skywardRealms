package com.graceful.skyward.api.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.graceful.skyward.api.dto.Residence
import com.graceful.skyward.api.utils.HttpClientUtils
import net.minecraft.world.entity.player.Player
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object PlayerService {

    fun authenticatePlayer(uuid: String, password: String): Boolean {
        val client = HttpClient.newBuilder().build()
        val jsonObject = JsonObject()
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("uuid", uuid)
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/data/properties"))
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(jsonObject))).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        val type = object : TypeToken<Map<String?, Any?>?>() {}.type
        val responseMap: Map<String, Any> = Gson().fromJson(response, type)
        return responseMap["isValid"] == "true"
    }

    fun createUser(uuid: String, password: String): Boolean {
        val client = HttpClient.newBuilder().build()
        val jsonObject = JsonObject()
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("uuid", uuid)
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/player/createPassword"))
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(jsonObject))).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        val type = object : TypeToken<Map<String?, Any?>?>() {}.type
        val responseMap: Map<String, Any> = Gson().fromJson(response, type)
        return responseMap["isCreated"] == "true"
    }

     fun savePlayerToDatabase(player: Player) {
         val client = HttpClient.newBuilder().build()
         val jsonObject = JsonObject()
         jsonObject.addProperty("uuid", player.uuid.toString())
         jsonObject.addProperty("username", player.displayName.string)
         val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/data/player"))
             .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(jsonObject))).build()
         client.send(request, HttpResponse.BodyHandlers.ofString()).body()
     }

    fun getResidences(uuid: String): List<Residence> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("${HttpClientUtils.appUrl}/api/v1/player/$uuid/residences")).GET().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        val listType = object : TypeToken<List<Residence?>?>() {}.type
        return Gson().fromJson(response, listType)
    }
}
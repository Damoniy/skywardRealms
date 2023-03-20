package com.graceful.skyward.api.repository

import com.graceful.skyward.api.utils.HttpClientUtils
import com.graceful.skyward.gson.GsonUtils
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


class SkywardRepository {
    fun getModProperties(): Map<String, String> {
        val response = HttpClientUtils.get("https://skywardrealms.herokuapp.com/api/v1/data/properties")
        return GsonUtils.getMapFromJson(EntityUtils.toString(response.entity))
    }
}
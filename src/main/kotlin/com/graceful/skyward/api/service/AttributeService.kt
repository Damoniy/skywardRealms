package com.graceful.skyward.api.service

import com.google.gson.JsonObject
import com.graceful.skyward.api.repository.AttributeRepository

object AttributeService {

    private val repository = AttributeRepository()

    fun getAttributes(uuid: String): MutableMap<String, String>{
        return repository.getPlayerAttributes(uuid)
    }

    fun saveAttributes(uuid: String, attributes: MutableMap<String, String>) {
        val attributesJson = JsonObject()
        attributesJson.addProperty("uuid", uuid)

        for(key in attributes.keys) {
            attributesJson.addProperty(key, attributes.getOrDefault(key, "0"))
        }

        repository.savePlayerAttributes(attributesJson)
    }
}
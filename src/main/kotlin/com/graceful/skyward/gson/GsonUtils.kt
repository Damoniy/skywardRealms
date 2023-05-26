package com.graceful.skyward.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtils {
    private val gson = Gson()

    fun <T: Any> createObjectFromJson(jsonString: String, clazz: Class<T>): T {
        return gson.fromJson(jsonString, clazz)
    }

    fun getMapFromJson(jsonString: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(jsonString, mapType)
    }

    fun getMutableMapFromJson(jsonString: String): MutableMap<String, String> {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(jsonString, mapType)
    }
}
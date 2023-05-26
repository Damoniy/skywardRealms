package com.graceful.skyward.api.service

import com.graceful.skyward.api.dto.City
import com.graceful.skyward.api.repository.SkywardRepository

object SkywardService {

    private val repository = SkywardRepository()

    fun getCurrentServerVersion(): String? {
        return repository.getModProperties()["serverVersion"]
    }

    fun getCurrentClientVersion(): String? {
        return repository.getModProperties()["clientVersion"]
    }

    fun getCities(): List<City> {
        return repository.getCities()
    }
}
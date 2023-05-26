package com.graceful.skyward.api.dto

import com.graceful.skyward.api.dto.Area

data class City(
        val cityId: Int,
        val dimensionId: Int,
        val cityName: String,
        val area: Area
    )

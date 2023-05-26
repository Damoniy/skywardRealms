package com.graceful.skyward.client.player

import com.graceful.skyward.api.dto.Residence

data class PlayerData(
    val uuid: String,
    val attributeMap: MutableMap<String, String>,
    val residences: List<Residence>
)
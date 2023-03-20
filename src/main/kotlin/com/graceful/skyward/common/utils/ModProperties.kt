package com.graceful.skyward.common.utils

import com.graceful.skyward.api.service.SkywardService
import com.graceful.skyward.gson.GsonUtils
import java.io.File

object ModProperties {
     private fun createServerPropertyFile(): File {
        val versionFile = File("config/mod_version.json")

        if (!versionFile.exists()) {
            versionFile.createNewFile()
        }

        versionFile.writeText("{ \"clientVersion\": \"${SkywardService.getCurrentServerVersion()}\" }")
        return versionFile
    }

     fun getLocalClientVersion(): String? {
        val versionFile = createServerPropertyFile()
        val jsonString = versionFile.readText()
        return GsonUtils.getMapFromJson(jsonString)["clientVersion"]
    }

    fun updateServerVersion(): String? {
        val versionFile = createServerPropertyFile()
        val jsonString = versionFile.readText()
        return GsonUtils.getMapFromJson(jsonString)["clientVersion"]
    }
}
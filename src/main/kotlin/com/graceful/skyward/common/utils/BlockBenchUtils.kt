package com.graceful.skyward.common.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.resources.ResourceLocation
import java.io.IOException
import java.io.InputStreamReader

object BlockBenchUtils {
    private val GSON = Gson()

    fun loadModelPartFromJson(modelId: ResourceLocation): ModelPart? {
        try {
            val stream = Minecraft.getInstance().resourceManager.getResource(modelId).get().open()
            val reader = InputStreamReader(stream)

            val json: JsonElement = JsonParser.parseReader(reader)
            val root: JsonObject = json.asJsonObject

            val elements: JsonObject = root.get("bones").asJsonObject

            val cubes = GSON.fromJson<List<ModelPart.Cube>>(elements.get("bones"), object : TypeToken<List<ModelPart.Cube>>() {}.type)
            val children = mutableMapOf<String, ModelPart>()

            return ModelPart(cubes, children)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}
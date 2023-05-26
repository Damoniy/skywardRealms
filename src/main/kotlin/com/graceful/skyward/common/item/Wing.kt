package com.graceful.skyward.common.item

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.itemgroup.SkywardItemGroup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Wearable
import top.theillusivec4.curios.api.type.capability.ICurioItem

class Wing(private val textureName: String): Item(Properties().tab(SkywardItemGroup.instance)), Wearable, ICurioItem {

    fun getWingResourceLocation(): ResourceLocation {
        return ResourceLocation(Skyward.modId, "textures/wing/$textureName")
    }
}
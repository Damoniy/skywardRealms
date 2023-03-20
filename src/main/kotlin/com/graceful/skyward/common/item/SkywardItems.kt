package com.graceful.skyward.common.item

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.itemgroup.SkywardItemGroup
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus

object SkywardItems {
    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, Skyward.modId)

    val purpleGlowstone = items.register("purple_glowstone") { Item(Item.Properties().tab(SkywardItemGroup())) }

    fun register(eventBus: IEventBus) {
        items.register(eventBus)
    }
}
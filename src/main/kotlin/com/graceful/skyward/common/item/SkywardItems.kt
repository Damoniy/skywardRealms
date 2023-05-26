package com.graceful.skyward.common.item

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.itemgroup.SkywardItemGroup
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject


object SkywardItems {
    private val items: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, Skyward.modId)

    val purpleGlowstone: RegistryObject<Item> = items.register("purple_glowstone_dust") { Item(Item.Properties().tab(SkywardItemGroup.instance)) }
    val wingBase: RegistryObject<Wing> = items.register("wing_base") { Wing("base/wings_base.png") }
    val wingJade: RegistryObject<Wing> = items.register("wing_jade") { Wing("wyvern/wyvern_wings_jade.png") }
    val wingRuby: RegistryObject<Wing> = items.register("wing_ruby") { Wing("wyvern/wyvern_wings_ruby.png") }
    val wingSapphire: RegistryObject<Wing> = items.register("wing_sapphire") { Wing("wyvern/wyvern_wings_sapphire.png") }
    val wingTopaz: RegistryObject<Wing> = items.register("wing_topaz") { Wing("wyvern/wyvern_wings_topaz.png") }
    val wingTourmaline: RegistryObject<Wing> = items.register("wing_tourmaline") { Wing("wyvern/wyvern_wings_tourmaline.png") }

    fun register(eventBus: IEventBus) {
        items.register(eventBus)
    }
}
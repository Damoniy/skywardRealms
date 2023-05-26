package com.graceful.skyward.common.item.itemgroup

import com.graceful.skyward.common.item.SkywardItems
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object SkywardItemGroup: CreativeModeTab("Skyward Tab") {

    val instance = SkywardItemGroup

    override fun makeIcon(): ItemStack {
        return ItemStack(SkywardItems.purpleGlowstone.get())
    }
}
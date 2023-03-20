package com.graceful.skyward.common.item.itemgroup

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.SkywardItems
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

class SkywardItemGroup: CreativeModeTab("Skyward Tab") {

    override fun makeIcon(): ItemStack {
        return ItemStack(SkywardItems.purpleGlowstone.get())
    }
}
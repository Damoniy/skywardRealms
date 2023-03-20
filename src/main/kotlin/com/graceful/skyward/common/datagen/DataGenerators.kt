package com.graceful.skyward.common.datagen

import com.graceful.skyward.common.Skyward
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Skyward.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
class DataGenerators {
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val dataGenerator = event.generator
        val existingFileHelper = event.existingFileHelper

        dataGenerator.addProvider(true, SkywardItemModelProvider(dataGenerator, existingFileHelper))
    }
}
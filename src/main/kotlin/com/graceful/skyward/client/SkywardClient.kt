package com.graceful.skyward.client

import com.graceful.skyward.api.service.SkywardService
import com.graceful.skyward.common.Skyward
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger

@OnlyIn(Dist.CLIENT)
class SkywardClient(event: FMLClientSetupEvent, logger: Logger) {

    init {
        logger.log(Level.INFO, "Client Version: ${SkywardService.getCurrentClientVersion()}")
    }

}
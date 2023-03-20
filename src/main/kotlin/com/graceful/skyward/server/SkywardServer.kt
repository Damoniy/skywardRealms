package com.graceful.skyward.server

import com.graceful.skyward.api.service.SkywardService
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger


@OnlyIn(Dist.DEDICATED_SERVER)
class SkywardServer(
    private val event: FMLDedicatedServerSetupEvent, logger: Logger) {

    private val service = SkywardService
    init {
        logger.log(Level.INFO, "Server Version: ${SkywardService.getCurrentServerVersion()}")
    }
}

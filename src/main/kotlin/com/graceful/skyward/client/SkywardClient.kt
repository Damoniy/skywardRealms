package com.graceful.skyward.client

import com.graceful.skyward.api.service.SkywardService
import com.graceful.skyward.client.player.PlayerData
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Logger

class SkywardClient(event: FMLClientSetupEvent, logger: Logger) {

    var playerData = PlayerData("0", mutableMapOf(), arrayListOf())

    companion object {
        var isOnSkywardRealms = false
        var isPlayerLoggedIn = false
        var isLoginScreenOpened = false
    }

    init {
        logger.log(Level.INFO, "Client Version: ${SkywardService.getCurrentClientVersion()}")
    }
}
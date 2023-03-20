package com.graceful.skyward.common

import com.graceful.skyward.client.SkywardClient
import com.graceful.skyward.client.event.EventHandler
import com.graceful.skyward.common.item.SkywardItems
import com.graceful.skyward.server.SkywardServer
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(Skyward.modId)
object Skyward {
    const val modId = "skyward"
    val modContainer = ModList.get().getModContainerById(modId)
    val modVersion = modContainer.get().modInfo.version

    // the logger for our mod
    val logger: Logger = LogManager.getLogger(modId)

    init {

        MinecraftForge.EVENT_BUS.register(EventHandler())

        logger.log(Level.INFO, "Starting Skyward Realms Minecraft Modification - Version $modVersion")

        SkywardItems.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientStart)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerStart)
            })

        println(obj)
    }

    private fun onClientStart(event: FMLClientSetupEvent) {
        logger.log(Level.INFO, "Initializing client...")
        SkywardClient(event, logger)
    }

    private fun onServerStart(event: FMLDedicatedServerSetupEvent) {
        logger.log(Level.INFO, "Server starting...")
        SkywardServer(event, logger)
    }
}
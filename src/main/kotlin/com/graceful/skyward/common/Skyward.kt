package com.graceful.skyward.common

import com.graceful.skyward.api.dto.City
import com.graceful.skyward.api.service.SkywardService
import com.graceful.skyward.client.CuriosHandler
import com.graceful.skyward.client.SkywardClient
import com.graceful.skyward.client.event.EventHandler
import com.graceful.skyward.common.block.SkywardBlocks
import com.graceful.skyward.common.datagen.SkywardBlockstateProvider
import com.graceful.skyward.common.datagen.SkywardItemModelProvider
import com.graceful.skyward.common.item.SkywardItems
import com.graceful.skyward.common.item.itemgroup.SkywardItemGroup
import com.graceful.skyward.server.SkywardServer
import com.graceful.skyward.server.event.ServerEventHandler
import net.minecraft.client.Minecraft
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegisterEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist
import top.theillusivec4.curios.api.SlotTypeMessage
import top.theillusivec4.curios.api.SlotTypePreset
import java.util.function.Supplier


@Mod(Skyward.modId)
object Skyward {
    const val modId = "skyward"
    val modContainer = ModList.get().getModContainerById(modId)
    val modVersion = modContainer.get().modInfo.version

    val logger: Logger = LogManager.getLogger(modId)

    var cities: List<City>

    lateinit var client: SkywardClient

    init {
        SkywardItems.register(MOD_BUS)
        SkywardBlocks.register(MOD_BUS)

        cities = SkywardService.getCities()

        logger.log(Level.INFO, "Starting Skyward Realms Minecraft Modification - Version $modVersion")

        val obj = runForDist(
            clientTarget = {
                MinecraftForge.EVENT_BUS.register(EventHandler())
                MOD_BUS.addListener(::onClientStart)
                MOD_BUS.addListener(::onRegisterLayerDefinitions)
                Minecraft.getInstance()
            },
            serverTarget = {
                MinecraftForge.EVENT_BUS.register(ServerEventHandler())
                MOD_BUS.addListener(::onServerStart)
            })
    }

    private fun onClientStart(event: FMLClientSetupEvent) {
        logger.log(Level.INFO, "Initializing client...")
        client = SkywardClient(event, logger)
        CuriosHandler.register(event)
    }

    private fun onServerStart(event: FMLDedicatedServerSetupEvent) {
        logger.log(Level.INFO, "Server starting...")
        SkywardServer(event, logger)
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    object DataGenerators {
        @SubscribeEvent
        fun gatherData(event: GatherDataEvent) {
            val eventGenerator = event.generator
            eventGenerator.addProvider(true, SkywardBlockstateProvider(eventGenerator, event.existingFileHelper))
            eventGenerator.addProvider(true, SkywardItemModelProvider(eventGenerator, event.existingFileHelper))
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    object ItemBlockRegister{
        @SubscribeEvent
        fun onRegisterItems(event: RegisterEvent) {
            if (event.registryKey == ForgeRegistries.Keys.ITEMS) {
                SkywardBlocks.blocks.entries.forEach {
                    val block: Block = it.get()
                    val properties = Item.Properties().tab(SkywardItemGroup.instance)
                    val blockItemFactory = Supplier<Item> { BlockItem(block, properties) }
                    event.register(ForgeRegistries.Keys.ITEMS, it.id, blockItemFactory)
                }
            }
        }
    }

    private fun onRegisterLayerDefinitions(event: RegisterLayerDefinitions) {
        CuriosHandler.register(event)
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    object InterModCommunicationEventHandler {

        @SubscribeEvent
        fun onInterModEnqueue(event: InterModEnqueueEvent) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE) { SlotTypePreset.BACK.messageBuilder.cosmetic().build() }
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE) { SlotTypePreset.CHARM.messageBuilder.build() }
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE) { SlotTypePreset.NECKLACE.messageBuilder.build() }
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE) { SlotTypePreset.HANDS.messageBuilder.build() }
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE) { SlotTypePreset.RING.messageBuilder.build() }
        }

        @SubscribeEvent
        fun onInterModProcess(event: InterModProcessEvent) {

        }
    }
}
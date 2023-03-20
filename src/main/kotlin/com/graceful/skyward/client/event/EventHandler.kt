package com.graceful.skyward.client.event

import com.google.gson.JsonObject
import com.graceful.skyward.api.utils.HttpClientUtils
import com.graceful.skyward.client.keybinding.KeyHandler
import com.graceful.skyward.common.datagen.SkywardItemModelProvider
import net.minecraft.data.DataGenerator
import net.minecraft.world.entity.player.Player
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.entity.EntityJoinLevelEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.server.ServerLifecycleHooks


class EventHandler {

    private var playerExperience = 0

    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        val entity = event.entity
        if (entity is Player) {
            playerExperience = entity.experienceLevel
        }
    }

    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        val oldPlayer = event.original
        val newPlayer = event.entity

        if(oldPlayer is Player && newPlayer is Player) {
            newPlayer.respawn()
            newPlayer.experienceLevel = oldPlayer.experienceLevel
        }
    }

    @SubscribeEvent
    fun onPlayerJoinWorld(event: EntityJoinLevelEvent) {
        val player = event.entity
        if(player is Player) {
            val server = ServerLifecycleHooks.getCurrentServer()
            server.commands.performPrefixedCommand(server.createCommandSourceStack(), "/gamerule keepInventory true")
            val jsonObject = JsonObject()
            jsonObject.addProperty("uuid", player.uuid.toString())
            jsonObject.addProperty("username", player.displayName.string)
            HttpClientUtils.post("https://skywardrealms.herokuapp.com/api/v1/data/player", jsonObject)
        }
    }

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.Key) {
        KeyHandler.handleKeyEvents(event)
    }
}
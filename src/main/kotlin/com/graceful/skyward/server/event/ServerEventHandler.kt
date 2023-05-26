package com.graceful.skyward.server.event

import com.graceful.skyward.common.Skyward
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.FenceGateBlock
import net.minecraft.world.level.block.FurnaceBlock
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class ServerEventHandler {

    @SubscribeEvent
    fun onBlockInteraction(event: PlayerInteractEvent.RightClickBlock) {
        if(isCity(event.pos) && !this.isOwnerOf(event.pos)) {
            if (event.level.getBlockState(event.pos).block is DoorBlock
                || event.level.getBlockState(event.pos).block is FurnaceBlock
                || event.level.getBlockState(event.pos).block is ChestBlock
                || event.level.getBlockState(event.pos).block is FenceGateBlock
            ) {
                event.isCanceled = true
            }
        }
    }

    @SubscribeEvent
    fun onBlockBreakEvent(event: BlockEvent.BreakEvent) {
        val breakingPos = event.pos
        if(isCity(breakingPos) && !this.isOwnerOf(breakingPos)) {
            event.isCanceled = true
        }
    }

    @SubscribeEvent
    fun onBlockHitEvent(event: BlockEvent.EntityPlaceEvent) {

    }

    private fun isCity(pos: BlockPos): Boolean {
        for(city in Skyward.cities) {
            if(pos.x in city.area.startX..city.area.finalX
                && pos.y in city.area.startY..city.area.finalY
                && pos.z in city.area.startZ..city.area.finalZ) {
                return true
            }
        }

        return false
    }

    private fun isOwnerOf(pos: BlockPos): Boolean {
        val residences = Skyward.client.playerData.residences
        for(residence in residences) {
            if(pos.x in residence.area.startX..residence.area.finalX
                && pos.y in residence.area.startY..residence.area.finalY
                && pos.z in residence.area.startZ..residence.area.finalZ) {
                return true
            }

            if(isAdmin()) return true
        }
        return false
    }

    private fun isAdmin(): Boolean {
        if(Minecraft.getInstance().player?.displayName?.string == "Dev") {
            return true
        }
        return false
    }
}
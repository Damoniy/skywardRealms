package com.graceful.skyward.common.block

import com.graceful.skyward.common.Skyward
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject


object SkywardBlocks {
    val blocks: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, Skyward.modId)

    val purpleGlowstoneBlock by blocks.registerObject("block_purple_glowstone") {
        BaseBlock(BlockBehaviour.Properties.copy(Blocks.GLOWSTONE), "block_purple_glowstone") }

    fun register(eventBus: IEventBus) {
        blocks.register(eventBus)
    }
}
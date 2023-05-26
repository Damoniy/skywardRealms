package com.graceful.skyward.common.block

import net.minecraft.world.level.block.Block

open class BaseBlock(
    properties: Properties,
    private val textureName: String): Block(properties) {

    val blockName: String get() = textureName

    fun getModdedBlock(): BaseBlock {
        return this.defaultBlockState().block as BaseBlock
    }
}
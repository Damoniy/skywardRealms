package com.graceful.skyward.client.render.layers

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.world.entity.player.Player

class WingLayer<T: Player, M: PlayerModel<T>>(renderer: RenderLayerParent<T, M>): RenderLayer<T, M>(renderer) {
    override fun render(
        p_117349_: PoseStack,
        p_117350_: MultiBufferSource,
        p_117351_: Int,
        p_117352_: T,
        p_117353_: Float,
        p_117354_: Float,
        p_117355_: Float,
        p_117356_: Float,
        p_117357_: Float,
        p_117358_: Float
    ) {
        TODO("Not yet implemented")
    }
}
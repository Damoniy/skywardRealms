package com.graceful.skyward.client.gui

import com.graceful.skyward.common.Skyward
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class GuiAttributes(): Screen(Component.literal("")) {

    private val cursor_texture = ResourceLocation(Skyward.modId, "item/purple_glowstone.png")

    init {
        super.init(Minecraft.getInstance(), 190, 205)

    }

    companion object {
        fun openGui() {
            Minecraft.getInstance().screen = GuiAttributes()
        }
        fun close() {
            Minecraft.getInstance().screen = null
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(poseStack)
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, cursor_texture);
        this.blit(poseStack, mouseX, mouseY,0, 0, 24, 24)
        font.draw(poseStack, Component.literal("Attributes"), (width / 2f) - font.width("Attributes"), 20f, 0xFFFFFF)
        super.render(poseStack, 0, 0, partialTicks)
    }

    override fun renderBackground(p_96559_: PoseStack) {
        if (minecraft!!.level != null) {
            this.fillGradient(p_96559_, 15, 15, width, height, -1072689136, -804253680)
        } else {
            renderDirtBackground(0)
        }
    }
}
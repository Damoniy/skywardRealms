package com.graceful.skyward.client.gui

import com.graceful.skyward.api.service.AttributeService
import com.graceful.skyward.common.Skyward
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player


class GuiAttributes(): Screen(Component.literal("")) {

    private val background = ResourceLocation(Skyward.modId, "textures/gui/attributes.png")
    private var attributes = Skyward.client.playerData.attributeMap
    private val startX = 85
    private val startY = 10
    private val wSize = 256
    private val hSize = 201

    init {
        super.init(Minecraft.getInstance(), this.wSize, this.hSize)
        this.minecraft!!.mouseHandler.releaseMouse()

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 19,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_strength"] = (attributes.getOrDefault("qt_strength", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 44,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_agility"] = (attributes.getOrDefault("qt_agility", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 69,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_vitality"] = (attributes.getOrDefault("qt_vitality", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 94,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_intelligence"] = (attributes.getOrDefault("qt_intelligence", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 119,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_wisdom"] = (attributes.getOrDefault("qt_wisdom", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 144,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_charisma"] = (attributes.getOrDefault("qt_charisma", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 15,
                startY + 169,
                20,
                20,
                Component.literal("+")
            ) {
                attributes["qt_luck"] = (attributes.getOrDefault("qt_luck", "0").toInt() + 1).toString()
            })

        this.addRenderableWidget(
            Button(
                startX + 150,
                startY + 169,
                50,
                20,
                Component.literal("Save")
            ) {
                this.saveAttributeData(minecraft!!.player as Player)
            })
    }

    companion object {
        fun openGui() {
            val minecraft = Minecraft.getInstance()
            if(minecraft.screen !is RegisterScreen) {
                minecraft.screen = GuiAttributes()
            }
        }
        fun close() {
            val minecraft = Minecraft.getInstance()
            minecraft.screen = null
            minecraft.mouseHandler.grabMouse()
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBg(poseStack)
        this.renderText(poseStack, "Attributes", 190, 15)
        this.renderText(poseStack, "Str: ${attributes["qt_strength"]}", startX + 45, startY + 25)
        this.renderText(poseStack, "Agi: ${attributes["qt_agility"]}", startX + 45, startY + 50)
        this.renderText(poseStack, "Vit: ${attributes["qt_vitality"]}", startX + 45, startY + 75)
        this.renderText(poseStack, "Int: ${attributes["qt_intelligence"]}", startX + 45, startY + 100)
        this.renderText(poseStack, "Wis: ${attributes["qt_wisdom"]}", startX + 45, startY + 125)
        this.renderText(poseStack, "Cha: ${attributes["qt_charisma"]}", startX + 45, startY + 150)
        this.renderText(poseStack, "Luc: ${attributes["qt_luck"]}", startX + 45, startY + 175)

        super.render(poseStack, mouseX, mouseY, partialTicks)
    }

    private fun renderText(poseStack: PoseStack, text: String, posX: Int, posY: Int) {
        font.draw(poseStack, Component.literal(text), posX.toFloat(), posY.toFloat(), 0x0d0d0d)
    }

    private fun renderBg(poseStack: PoseStack) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, background)
        this.blit(poseStack, startX, startY, 0, 0, wSize, hSize)
    }

    private fun saveAttributeData(player: Player) {
        AttributeService.saveAttributes(player.stringUUID, this.attributes)
    }
}
package com.graceful.skyward.client.gui

import com.graceful.skyward.api.service.PlayerService
import com.graceful.skyward.common.Skyward
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox

import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.server.ServerLifecycleHooks

class RegisterScreen: Screen(Component.literal("")) {

    private val background = ResourceLocation(Skyward.modId, "textures/gui/login.png")
    private val startX = 120
    private val startY = 20
    private val wSize = 186
    private val hSize = 121

    init {
        super.init(Minecraft.getInstance(), this.wSize, this.hSize)
        this.minecraft!!.mouseHandler.releaseMouse()

        val sndPassword = EditBox(font, startX + 65, startY + 20, 100, 15, Component.literal(""))
        val passwordBox = EditBox(font, startX + 65, startY + 40, 100, 15, Component.literal(""))

        this.addRenderableWidget(passwordBox)
        this.addRenderableWidget(sndPassword)

        this.addRenderableWidget(
            Button(
                startX + 115,
                startY + 85,
                50,
                20,
                Component.literal("Submit")
            ) {
                this.register(sndPassword.value, passwordBox.value)
            }
        )
    }

    companion object {
        fun openGui() {
            val minecraft = Minecraft.getInstance()
            minecraft.screen = RegisterScreen()
        }
        fun close() {
            val minecraft = Minecraft.getInstance()
            minecraft.screen = null
            minecraft.mouseHandler.grabMouse()
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBg(poseStack)
        this.renderText(poseStack, "Register", 195, 25)
        this.renderText(poseStack, "Username", startX + 5, startY + 23)
        this.renderText(poseStack, "Password", startX + 5, startY + 43)

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

    private fun register(password: String, sndPassword: String) {
        val uuid = this.minecraft?.player!!.stringUUID
        if(password == sndPassword) {
            val created = PlayerService.createUser(uuid, password)
            val server = ServerLifecycleHooks.getCurrentServer()
            val player = minecraft!!.player!!

            if(created) {
                server.commands.performPrefixedCommand(server.createCommandSourceStack(), "/tell ${player.gameProfile.name} §eWell done! You are now registered.")
                return
            }

            server.commands.performPrefixedCommand(server.createCommandSourceStack(), "/tell ${player.gameProfile.name} §cPlayer has already registered. Try to log in.")
        }
    }
}
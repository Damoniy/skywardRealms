package com.graceful.skyward.client.gui

import com.graceful.skyward.api.service.PlayerService
import com.graceful.skyward.client.SkywardClient
import com.graceful.skyward.common.Skyward
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.server.ServerLifecycleHooks

class LoginScreen: Screen(Component.literal("")) {

    private val background = ResourceLocation(Skyward.modId, "textures/gui/login.png")
    private val startX = 120
    private val startY = 20
    private val wSize = 186
    private val hSize = 121

    init {
        super.init(Minecraft.getInstance(), this.wSize, this.hSize)
        this.minecraft!!.mouseHandler.releaseMouse()

        val usernameBox = EditBox(font, startX + 65, startY + 20, 100, 15, Component.literal(""))
        val passwordBox = EditBox(font, startX + 65, startY + 40, 100, 15, Component.literal(""))

        this.addRenderableWidget(usernameBox)
        this.addRenderableWidget(passwordBox)

        this.addRenderableWidget(
            Button(
                startX + 115,
                startY + 85,
                50,
                20,
                Component.literal("Login")
            ) {
                this.tryToLogIn(passwordBox.value)
            }
        )

        this.addRenderableWidget(
            Button(
                startX + 40,
                startY + 85,
                50,
                20,
                Component.literal("Register")
            ) { RegisterScreen.openGui() }
        )
    }

    companion object {
        fun openGui() {
            val minecraft = Minecraft.getInstance()
            minecraft.screen = LoginScreen()
        }
        fun close() {
            val minecraft = Minecraft.getInstance()
            minecraft.screen = null
            minecraft.mouseHandler.grabMouse()
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBg(poseStack)
        this.renderText(poseStack, "Login", 200, 25)
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

    private fun tryToLogIn(password: String) {
        val uuid = minecraft?.player!!.stringUUID
        val loggedIn = PlayerService.authenticatePlayer(uuid, password)
        val server = ServerLifecycleHooks.getCurrentServer()
        val player = minecraft!!.player!!

        if(loggedIn) {
            server.commands.performPrefixedCommand(server.createCommandSourceStack(), "/tell ${player.gameProfile.name} §aWelcome to SkyWard Realms!")
            SkywardClient.isPlayerLoggedIn = true
            close()
            return
        }

        server.commands.performPrefixedCommand(server.createCommandSourceStack(), "/tell ${player.gameProfile.name} §cWrong password!")
    }
}
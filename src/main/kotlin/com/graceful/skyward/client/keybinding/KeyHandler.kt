package com.graceful.skyward.client.keybinding

import com.graceful.skyward.client.gui.GuiAttributes
import net.minecraftforge.client.event.InputEvent
import org.lwjgl.glfw.GLFW

object KeyHandler {

    private var isOpened = false

    fun handleKeyEvents(event: InputEvent.Key) {
        val key = event.key

        when(event.action) {
            GLFW.GLFW_PRESS -> handlePress(key)
            GLFW.GLFW_REPEAT -> handleType(key)
            GLFW.GLFW_RELEASE -> handleRelease(key)
        }
    }

    private fun handlePress(key: Int) {
        when(key) {
            GLFW.GLFW_KEY_X -> { isOpened = !isOpened; when(isOpened) { true -> GuiAttributes.openGui(); false -> GuiAttributes.close() } }
        }
    }

    private fun handleRelease(key: Int) {
        when(key) {
        }
    }

    private fun handleType(key: Int) {
        when(key) {
        }
    }
}
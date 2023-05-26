package com.graceful.skyward.client.event

import com.graceful.skyward.api.service.AttributeService
import com.graceful.skyward.api.service.PlayerService
import com.graceful.skyward.client.SkywardClient
import com.graceful.skyward.client.gui.LoginScreen
import com.graceful.skyward.client.gui.RegisterScreen
import com.graceful.skyward.client.keybinding.KeyHandler
import com.graceful.skyward.client.player.PlayerData
import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.Wing
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.FenceGateBlock
import net.minecraft.world.level.block.FurnaceBlock
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RenderGuiEvent
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.event.entity.EntityJoinLevelEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.server.ServerLifecycleHooks
import top.theillusivec4.curios.api.event.CurioChangeEvent
import kotlin.random.Random


class EventHandler {
    private var playerExperience = 0
    private var health = ResourceLocation("skyward", "textures/gui/health.png")
    private var mana = ResourceLocation("skyward", "textures/gui/mana.png")
    private var experience = ResourceLocation("skyward", "textures/gui/experience.png")

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

    private fun preparePlayer(player: Player) {
            Skyward.client.playerData = PlayerData(
                player.stringUUID,
                AttributeService.getAttributes(player.stringUUID),
                PlayerService.getResidences(player.stringUUID)
            )

            PlayerService.savePlayerToDatabase(player)
    }

    @SubscribeEvent
    fun onPlayerLogin(event: EntityJoinLevelEvent) {
        if(event.entity is Player) {
            preparePlayer(event.entity as Player)

            if(event.entity.server != null) {
                SkywardClient.isOnSkywardRealms = true
            }
        }
    }

    @SubscribeEvent
    fun onPlayerTick(event: PlayerTickEvent) {
        val strength = Skyward.client.playerData.attributeMap["qt_strength"]!!.toFloat()
        val agility = Skyward.client.playerData.attributeMap["qt_agility"]!!.toFloat()
        val vitality = Skyward.client.playerData.attributeMap["qt_vitality"]!!.toFloat()
        val luck = Skyward.client.playerData.attributeMap["qt_luck"]!!.toFloat()

        if(event.player is Player) {
            tickAbilities(event, agility, luck, strength, vitality)
        }

        if(!SkywardClient.isPlayerLoggedIn && !SkywardClient.isLoginScreenOpened) {
            if(Minecraft.getInstance().screen !is LoginScreen || Minecraft.getInstance().screen !is RegisterScreen) {
                LoginScreen.openGui()
            }
        }
    }
    @SubscribeEvent
    fun onCurioChange(event: CurioChangeEvent) {
        if (event.entity is Player) {
            (event.entity as Player).abilities.mayfly = event.to.item is Wing
            (event.entity as Player).onUpdateAbilities()
        }
    }

    private fun tickAbilities(
        event: PlayerTickEvent,
        agility: Float,
        luck: Float,
        strength: Float,
        vitality: Float
    ) {
        event.player.foodData.foodLevel = 20
        event.player.attributes.getInstance(Attributes.MOVEMENT_SPEED)!!.baseValue = 0.1 + (0.1 * (agility / 190.4))
        event.player.attributes.getInstance(Attributes.ATTACK_SPEED)!!.baseValue = 4 + (agility / 25.0)
        event.player.attributes.getInstance(Attributes.LUCK)!!.baseValue = 1 + (luck / 100.0)

        event.player.attributes.getInstance(Attributes.ATTACK_KNOCKBACK)!!.baseValue = 0 + (strength / 50.0)
        event.player.attributes.getInstance(Attributes.ATTACK_DAMAGE)!!.baseValue = 1 + (strength / 75.0)

        event.player.attributes.getInstance(Attributes.MAX_HEALTH)!!.baseValue = 20 + (vitality / 10.0)
        event.player.attributes.getInstance(Attributes.ARMOR)!!.baseValue = 0 + (vitality / 50.0)
        event.player.attributes.getInstance(Attributes.KNOCKBACK_RESISTANCE)!!.baseValue =
            0 + (vitality / 180.0) + (strength / 150)
    }

    @SubscribeEvent
    fun onCriticalHit(event: AttackEntityEvent) {
        val criticalTax = Skyward.client.playerData.attributeMap["qt_luck"]!!.toFloat() / 10.0
        val strength = Skyward.client.playerData.attributeMap["qt_strength"]!!.toFloat()

        val random = Random.nextDouble(95.0) + 5.0

        if(event.entity is Player) {
            if(random + criticalTax > 60) {
                event.target.hurt(DamageSource.GENERIC, strength / 8f)
                spawnParticle(event)
                playSound(event)
            }
        }
    }

    private fun playSound(event: AttackEntityEvent) {
        event.entity.level.playSound(
            event.entity, event.entity.x, event.entity.y, event.entity.z,
            SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 2f, 2f
        )
    }

    private fun spawnParticle(event: AttackEntityEvent) {
        val d0: Double = java.util.Random().nextGaussian() * 0.5
        val d1: Double = java.util.Random().nextGaussian() * 0.5
        val d2: Double = java.util.Random().nextGaussian() * 0.5
        event.entity.level.addParticle(
            ParticleTypes.CRIT,
            event.target.getRandomX(1.0), event.target.randomY, event.target.getRandomZ(1.0), d0, d1, d2
        )
    }

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.Key) {
        KeyHandler.handleKeyEvents(event)
    }

    @SubscribeEvent
    fun onRenderOverlay(event: RenderGuiEvent.Pre) {
        val maxMana = Skyward.client.playerData.attributeMap["qt_intelligence"]?.toInt() ?: 0
        val currentMana = Skyward.client.playerData.attributeMap["qt_intelligence"]?.toInt() ?: 0
        val maxHealth = Minecraft.getInstance().player?.maxHealth?.toInt()!!
        val currentHealth = Minecraft.getInstance().player?.health?.toInt()!!
        val currentXp: Int = (Minecraft.getInstance().player?.experienceProgress!! * 100.0).toInt()

        renderHudElement(event.poseStack, health, 6, 4)
        renderText(event.poseStack, "HP: $currentHealth / $maxHealth", 25, 10)

        renderHudElement(event.poseStack, mana, 6, 22)
        renderText(event.poseStack, "MP: $currentMana / $maxMana", 25, 28)

        renderHudElement(event.poseStack, experience, 6, 40)
        renderText(event.poseStack, "LV: ${Minecraft.getInstance().player!!.experienceLevel} - $currentXp / 100 (%)", 25, 46)
    }

    private fun renderText(poseStack: PoseStack, text: String, posX: Int, posY: Int) {
        Minecraft.getInstance().font.draw(poseStack, Component.literal(text), posX.toFloat(), posY.toFloat(), 0xf0f0f0)
    }

    private fun renderHudElement(poseStack: PoseStack, resourceLocation: ResourceLocation, posX: Int, posY: Int) {
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.enableBlend()
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        )
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, resourceLocation)

        GuiComponent.blit(poseStack, posX, posY, 0f, 0f, 16, 16, 16, 16)

        RenderSystem.depthMask(true)
        RenderSystem.defaultBlendFunc()
        RenderSystem.enableDepthTest()
        RenderSystem.disableBlend()
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
    }

    @SubscribeEvent
    fun renderEntities(event: RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>>) {
        val health = event.entity.health
        val maxHealth = event.entity.maxHealth
        event.entity.isCustomNameVisible = true
        event.entity.customName =  Component.literal("$health / $maxHealth §c❤")
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGuiOverlayEvent.Pre) {
        if(event.overlay.id.path == "player_health"
            || event.overlay.id.path == "food_level"
            || event.overlay.id.path == "armor_level"
            || event.overlay.id.path == "experience_bar"
        ) {
            event.isCanceled = true
        }
    }
}
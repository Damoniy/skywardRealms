package com.graceful.skyward.client.render

import com.graceful.skyward.client.model.WingModel
import com.graceful.skyward.common.item.SkywardItems
import com.graceful.skyward.common.item.Wing
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ElytraItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraftforge.fml.ModList
import top.theillusivec4.caelus.api.CaelusApi
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.client.ICurioRenderer


class WingRenderer(private val texture: ResourceLocation, private val model: WingModel) :
    ICurioRenderer {

    companion object {
        fun getInstance(wing: Wing, modelPart: ModelPart): WingRenderer {
            return WingRenderer(
                wing.getWingResourceLocation(),
                WingModel(modelPart, RenderType::entityCutoutNoCull)
            )
        }
    }

    override fun <T : LivingEntity?, M : EntityModel<T>?> render(
        stack: ItemStack,
        slotContext: SlotContext,
        poseStack: PoseStack,
        renderLayerParent: RenderLayerParent<T, M>?,
        multiBufferSource: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        val model = model

        if (ModList.get().isLoaded("caelus")) {
            if (!CaelusApi.getInstance().canFly(slotContext.entity())) {
                model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
                model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks)
                ICurioRenderer.followBodyRotations(slotContext.entity(), model)
                if (EnchantmentHelper.getEnchantments(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST))
                        .isEmpty()
                ) {
                    render(poseStack, multiBufferSource, light, stack.hasFoil())
                } else render(poseStack, multiBufferSource, light, true)
            }
        } else {
            if (slotContext.entity().getItemBySlot(EquipmentSlot.CHEST).item !is ElytraItem) {
                model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
                model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks)
                ICurioRenderer.followBodyRotations(slotContext.entity(), model)
                if (EnchantmentHelper.getEnchantments(slotContext.entity().getItemBySlot(EquipmentSlot.CHEST))
                        .isEmpty()
                ) {
                    render(poseStack, multiBufferSource, light, stack.hasFoil())
                } else render(poseStack, multiBufferSource, light, true)
            }
        }
    }

    private fun render(matrixStack: PoseStack, buffer: MultiBufferSource, light: Int, hasFoil: Boolean) {
        val renderType: RenderType = model.renderType(texture)
        val vertexBuilder = ItemRenderer.getFoilBuffer(buffer, renderType, false, hasFoil)
        model.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f)
    }
}
package com.graceful.skyward.client

import com.graceful.skyward.client.model.WingModel
import com.graceful.skyward.client.render.WingRenderer
import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.item.SkywardItems
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import top.theillusivec4.curios.api.client.CuriosRendererRegistry
import java.util.function.Supplier

object CuriosHandler {

    private val wingLayer = ModelLayerLocation(ResourceLocation(Skyward.modId, "wing"), "wing")


    fun register(event: FMLClientSetupEvent) {
        CuriosRendererRegistry.register(SkywardItems.wingBase.get()) { WingRenderer.getInstance(SkywardItems.wingBase.get(), bakeLayer(wingLayer)) }
        CuriosRendererRegistry.register(SkywardItems.wingJade.get()) { WingRenderer.getInstance(SkywardItems.wingJade.get(), bakeLayer(wingLayer)) }
        CuriosRendererRegistry.register(SkywardItems.wingTopaz.get()) { WingRenderer.getInstance(SkywardItems.wingTopaz.get(), bakeLayer(wingLayer)) }
        CuriosRendererRegistry.register(SkywardItems.wingRuby.get()) { WingRenderer.getInstance(SkywardItems.wingRuby.get(), bakeLayer(wingLayer)) }
        CuriosRendererRegistry.register(SkywardItems.wingSapphire.get()) { WingRenderer.getInstance(SkywardItems.wingSapphire.get(), bakeLayer(wingLayer)) }
        CuriosRendererRegistry.register(SkywardItems.wingTourmaline.get()) { WingRenderer.getInstance(SkywardItems.wingTourmaline.get(), bakeLayer(wingLayer)) }
    }

    private fun bakeLayer(layerLocation: ModelLayerLocation): ModelPart {
        return Minecraft.getInstance().entityModels.bakeLayer(layerLocation)
    }

    private fun registerLayer(
        event: EntityRenderersEvent.RegisterLayerDefinitions,
        layerLocation: ModelLayerLocation,
        layer: Supplier<LayerDefinition>
    ) {
        event.registerLayerDefinition(layerLocation, layer)
    }

    fun register(event: EntityRenderersEvent.RegisterLayerDefinitions) {
        registerLayer(event, wingLayer, getLayer(WingModel.createWing(), 64, 64))
    }

    private fun getLayer(mesh: MeshDefinition, textureWidth: Int, textureHeight: Int): Supplier<LayerDefinition> {
        return Supplier { LayerDefinition.create(mesh, textureWidth, textureHeight) }
    }
}
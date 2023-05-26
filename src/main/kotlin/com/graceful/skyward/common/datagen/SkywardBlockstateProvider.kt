package com.graceful.skyward.common.datagen

import com.graceful.skyward.common.Skyward
import com.graceful.skyward.common.block.BaseBlock
import com.graceful.skyward.common.block.SRRotatedPillarBlock
import com.graceful.skyward.common.block.SkywardBlocks
import net.minecraft.core.Direction
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

class SkywardBlockstateProvider(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockStateProvider(dataGenerator, Skyward.modId, existingFileHelper) {
    override fun registerStatesAndModels() {
        simpleBlock(SkywardBlocks.purpleGlowstoneBlock)
//        simpleBlock(SkywardBlocks.HUMUS_DIRT.block)
//        simpleBlock(SkywardBlocks.PURPLE_PLANKS.block)
//
//        grassBlock(SkywardBlocks.LIME_GRASS.getModdedBlock())
//
//        rawLogBlock(SkywardBlocks.BLACK_WOOD_LOG)
    }

    private fun grassBlock(block: BaseBlock) {
        val models = cubeBottomTop(block.descriptionId,
            resourceName(block, "side"),
            resourceName(block, "bottom"),
            resourceName(block, "top"))
        getVariantBuilder(block).partialState().setModels(ConfiguredModel(models))
    }

    private fun logBlockModels(block: SRRotatedPillarBlock, vertical: ModelFile, horizontal: ModelFile) {
        getVariantBuilder(block)
            .partialState().with(SRRotatedPillarBlock.AXIS, Direction.Axis.Y)
            .modelForState().modelFile(vertical).addModel()
            .partialState().with(SRRotatedPillarBlock.AXIS, Direction.Axis.Z)
            .modelForState().modelFile(horizontal).rotationX(90).addModel()
            .partialState().with(SRRotatedPillarBlock.AXIS, Direction.Axis.X)
            .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel()
    }

    private fun rotatedPillar(block: SRRotatedPillarBlock, blockName: String, side: ResourceLocation, end: ResourceLocation) {
        logBlockModels(
            block,
            models().cubeColumn(blockName, side, end),
            models().cubeColumnHorizontal(blockName + "_horizontal", side, end)
        )
    }

    private fun rawLogBlock(block: SRRotatedPillarBlock) {
        rotatedPillar(block, block.blockName, resourceName(block, "side"), resourceName(block,"end"))
    }

    private fun cubeBottomTop(block: String, side: ResourceLocation, bottom: ResourceLocation, top: ResourceLocation): ModelFile {
        return models().cubeBottomTop(block, side, bottom, top)
    }

    private fun resourceName(block: BaseBlock, suffix: String): ResourceLocation {
        return modLoc("block/${block.blockName}_$suffix")
    }
}
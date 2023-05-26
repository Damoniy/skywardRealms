package com.graceful.skyward.common.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property

abstract class SRRotatedPillarBlock(properties: Properties, blockName: String): BaseBlock(properties, blockName) {

    companion object {
        val AXIS: Property<Direction.Axis> = BlockStateProperties.AXIS
    }

    init {
        this.registerDefaultState(
            this.defaultBlockState().setValue(AXIS, Direction.Axis.Y)
        )
    }

    override fun rotate(blockState: BlockState, rotation: Rotation): BlockState {
        return when (rotation) {
            Rotation.COUNTERCLOCKWISE_90, Rotation.CLOCKWISE_90 -> when (blockState.getValue(AXIS) as Direction.Axis) {
                Direction.Axis.X -> blockState.setValue(AXIS, Direction.Axis.Z)
                Direction.Axis.Z -> blockState.setValue(AXIS, Direction.Axis.X)
                else -> blockState
            }
            else -> blockState
        }
    }

    override fun createBlockStateDefinition(stateDefinitions: StateDefinition.Builder<Block?, BlockState?>) {
        stateDefinitions.add(AXIS)
    }

    override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
        return this.defaultBlockState().setValue(AXIS, blockPlaceContext.clickedFace.axis)
    }
}
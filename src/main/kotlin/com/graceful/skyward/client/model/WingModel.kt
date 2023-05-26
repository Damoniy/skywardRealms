package com.graceful.skyward.client.model

import com.google.common.collect.ImmutableList
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import kotlin.math.cos


class WingModel(part: ModelPart, renderType: java.util.function.Function<ResourceLocation, RenderType>) :
    HumanoidModel<LivingEntity>(part, renderType) {
    private val rightWing = body.getChild("right_wing")
    private val leftWing = body.getChild("left_wing")

    override fun bodyParts(): Iterable<ModelPart> {
        return ImmutableList.of(rightWing, leftWing)
    }

    override fun setupAnim(
        entity: LivingEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        if(entity is Player) {
            if(entity.abilities.flying) {
                val wingAngle: Double = cos(ageInTicks * 0.2) * Math.PI.toFloat() * 0.25
                rightWing.zRot = wingAngle.toFloat()
                leftWing.zRot = (-wingAngle).toFloat()
                return
            }

            val idleRotation = 0.1f
            val wingAngle: Float = cos(ageInTicks * 0.1f) * Math.PI.toFloat() * 0.05f
            leftWing.zRot = -wingAngle + idleRotation
            rightWing.zRot = wingAngle + idleRotation
        }
    }

    companion object {
        fun createWing(): MeshDefinition {
            val meshDefinition = createMesh(CubeDeformation(-1f), 0f)

            meshDefinition.root.getChild("body").addOrReplaceChild(
                "right_wing",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-23.1328f, -23.8f, 0.2332f, 24.0f, 32.0f, 0.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-1.0f, 1.0f, 0.0f, 0.0f, 0.3927f, 0.0f)
            )

            meshDefinition.root.getChild("body").addOrReplaceChild(
                "left_wing",
                CubeListBuilder.create().texOffs(0, 0).mirror()
                    .addBox(-0.6732f, -23.8f, 0.0388f, 24.0f, 32.0f, 0.0f, CubeDeformation(0.0f))
                    .mirror(false),
                PartPose.offsetAndRotation(0.0f, 1.0f, 0.0f, 0.0f, -0.3927f, 0.0f)
            )

            return meshDefinition
        }
    }
}
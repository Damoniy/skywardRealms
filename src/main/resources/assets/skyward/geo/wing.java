// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class unknown<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "unknown"), "main");
	private final ModelPart right_side;
	private final ModelPart left_side;

	public unknown(ModelPart root) {
		this.right_side = root.getChild("right_side");
		this.left_side = root.getChild("left_side");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition right_side = partdefinition.addOrReplaceChild("right_side", CubeListBuilder.create(), PartPose.offset(0.5F, 2.8F, 4.95F));

		PartDefinition right_side_r1 = right_side.addOrReplaceChild("right_side_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-23.1328F, -23.8F, 0.2332F, 24.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition left_side = partdefinition.addOrReplaceChild("left_side", CubeListBuilder.create(), PartPose.offset(1.5F, 1.8F, 4.95F));

		PartDefinition left_side_r1 = left_side.addOrReplaceChild("left_side_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.6732F, -23.8F, 0.0388F, 24.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		right_side.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_side.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
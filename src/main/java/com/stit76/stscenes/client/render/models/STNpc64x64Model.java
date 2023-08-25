package com.stit76.stscenes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.entity.STNpc64x64;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class STNpc64x64Model<T extends STNpc64x64> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(STScenes.MODID, "breast_model"), "main");
	public final ModelPart breasts;
	private final ModelPart eyes_1_up;
	private final ModelPart eyes_1_down;
	private Double eyes_time = 0.0;
	public STNpc64x64Model(ModelPart root) {
		super(root);
		this.breasts = root.getChild("Breasts_type_1");
		this.eyes_1_up = this.head.getChild("Eyes");
		this.eyes_1_down = this.head.getChild("Eyes_2");
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.25f), 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition Head = partdefinition.getChild("head");
		PartDefinition Anime_eye_type_1_up = Head.addOrReplaceChild("Anime_eye_type_1_up", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Anime_eye_1_type_1_up = Anime_eye_type_1_up.addOrReplaceChild("Anime_eye_type_1_1_up", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.3F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_2_type_1_up = Anime_eye_type_1_up.addOrReplaceChild("Anime_eye_type_1_2_up", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.3F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_type_1_down = Head.addOrReplaceChild("Anime_eye_type_1_down", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Anime_eye_1_type_1_down = Anime_eye_type_1_down.addOrReplaceChild("Anime_eye_type_1_1_down", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.3F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_2_type_1_down = Anime_eye_type_1_down.addOrReplaceChild("Anime_eye_type_1_2_down", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.3F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Breasts_type_1 = partdefinition.addOrReplaceChild("Breasts_type_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Breast_type_1_1 = Breasts_type_1.addOrReplaceChild("Breast_type_1_1", CubeListBuilder.create().texOffs(21, 21).addBox(-1.0F, -2.0F, -3.0F, 3.5F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, 0.6127F, -0.0715F, -0.0501F));
		PartDefinition Breast_type_1_2 = Breasts_type_1.addOrReplaceChild("Breast_type_1_2", CubeListBuilder.create().texOffs(18, 21).addBox(-2.5F, -2.0F, -3.0F, 3.5F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -1.0F, 0.6127F, 0.0715F, 0.0501F));
		//partdefinition.addOrReplaceChild("slim_left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(5.0F, 2.0F + p_170683_, 0.0F));
		//partdefinition.addOrReplaceChild("slim_right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170682_), PartPose.offset(-5.0F, 2.0F + p_170683_, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e){
		this.eyes_time += 0.05;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
	}
	private void loadAnimeEyes1Anim(){
		double y = 0;
		if(this.eyes_time >= 7){
			if(this.eyes_time <= 8){
				double offset = 0;
				double PHASE_SHIFT = 0.0;
				double AMPLITUDE = 1.5;
				double PERIOD = 1;
				double time = this.eyes_time;
				y = AMPLITUDE * Math.sin((time / PERIOD - PHASE_SHIFT) * 2 * Math.PI) + offset;
			} else {
				this.eyes_time = 0.0D;
			}
		}
		if(y >= 0){
			eyes_1_up.y = (float) (-4.0f + (y / 3));
			eyes_1_up.yScale = (float) y / 3;
			eyes_1_down.y = (float) (-2.0f - (y / 3));
			eyes_1_down.yScale = (float) y / 3;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		breasts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		super.renderToBuffer(poseStack,vertexConsumer,packedLight,packedOverlay,red,green,blue,alpha);
	}
}
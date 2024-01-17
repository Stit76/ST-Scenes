package com.stit76.stscenes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.entity.STNpc64x64;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class STNpc64x64Model<T extends STNpc64x64> extends PlayerModel<T> {
	//demo version
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(STScenes.MODID, "breast_model"), "main");
	private final ModelPart anime_eye_type_1_up;
	private final ModelPart anime_eye_type_1_down;
	private final ModelPart steve_eye_type_1_up;
	private final ModelPart steve_eye_type_1_down;
	private final ModelPart breasts_type_1;
	private final ModelPart breasts_type_2;
	private final ModelPart left_arm_slim;
	private final ModelPart right_arm_slim;
	private final ModelPart left_sleeve_slim;
	private final ModelPart right_sleeve_slim;
	private double AET1_time = 0;

	public STNpc64x64Model(ModelPart root) {
		super(root,false);
		MinecraftForge.EVENT_BUS.register(this);
		this.anime_eye_type_1_up = this.head.getChild("anime_eye_type_1_up");
		this.anime_eye_type_1_down = this.head.getChild("anime_eye_type_1_down");
		this.steve_eye_type_1_up = this.head.getChild("steve_eye_type_1_up");
		this.steve_eye_type_1_down = this.head.getChild("steve_eye_type_1_down");
		this.breasts_type_1 = this.body.getChild("Breasts_type_1");
		this.breasts_type_2 = this.body.getChild("Breasts_type_2");
		this.left_arm_slim = this.body.getChild("left_arm_slim");
		this.right_arm_slim = this.body.getChild("right_arm_slim");
		this.left_sleeve_slim = this.body.getChild("left_sleeve_slim");
		this.right_sleeve_slim = this.body.getChild("right_sleeve_slim");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = PlayerModel.createMesh(CubeDeformation.NONE,false);
		PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.getChild("head");
		//Anime_eyes_type_1
		PartDefinition Anime_eye_type_1_up = Head.addOrReplaceChild("anime_eye_type_1_up", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Anime_eye_1_type_1_up = Anime_eye_type_1_up.addOrReplaceChild("Anime_eye_type_1_1_up", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_2_type_1_up = Anime_eye_type_1_up.addOrReplaceChild("Anime_eye_type_1_2_up", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_type_1_down = Head.addOrReplaceChild("anime_eye_type_1_down", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Anime_eye_1_type_1_down = Anime_eye_type_1_down.addOrReplaceChild("Anime_eye_type_1_1_down", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Anime_eye_2_type_1_down = Anime_eye_type_1_down.addOrReplaceChild("Anime_eye_type_1_2_down", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		//Steve_eyes_type_1
		PartDefinition Steve_eye_type_1_up = Head.addOrReplaceChild("steve_eye_type_1_up", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Steve_eye_1_type_1_up = Steve_eye_type_1_up.addOrReplaceChild("steve_eye_type_1_1_up", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Steve_eye_2_type_1_up = Steve_eye_type_1_up.addOrReplaceChild("steve_eye_type_1_2_up", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Steve_eye_type_1_down = Head.addOrReplaceChild("steve_eye_type_1_down", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -4.55F, 3.1416F, 0.0F, 0.0F));
		PartDefinition Steve_eye_1_type_1_down = Steve_eye_type_1_down.addOrReplaceChild("steve_eye_type_1_1_down", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));
		PartDefinition Steve_eye_2_type_1_down = Steve_eye_type_1_down.addOrReplaceChild("steve_eye_type_1_2_down", CubeListBuilder.create().texOffs(8, 11).addBox(-3.4F, -28.4F, -4.6F, 2.2F, 2.4F, 0.3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 27.0F, 4.1F));


		PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		//Breasts_type_1
		PartDefinition Breasts_type_1 = Body.addOrReplaceChild("Breasts_type_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Breast_type_1_1 = Breasts_type_1.addOrReplaceChild("Breast_type_1_1", CubeListBuilder.create().texOffs(21, 21).addBox(-1.0F, -2.0F, -3.0F, 3.5F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, 0.6127F, -0.0715F, -0.0501F));
		PartDefinition Breast_type_1_2 = Breasts_type_1.addOrReplaceChild("Breast_type_1_2", CubeListBuilder.create().texOffs(18, 21).addBox(-2.5F, -2.0F, -3.0F, 3.5F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, -1.0F, 0.6127F, 0.0715F, 0.0501F));
		//Breasts_type_2
		PartDefinition Breasts_type_2 = Body.addOrReplaceChild("Breasts_type_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Breast_type_2_2_r1 = Breasts_type_2.addOrReplaceChild("Breast_type_2_2_r1", CubeListBuilder.create().texOffs(20, 21).addBox(-1.0F, -2.9F, -1.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 21).addBox(-5.0F, -2.9F, -1.5F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 1.1345F, 0.0F, 0.0F));
		//Slim_arms
		Body.addOrReplaceChild("left_arm_slim", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0f)), PartPose.offset(5.0F, 2.5F, 0.0F));
		Body.addOrReplaceChild("right_arm_slim", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0f)), PartPose.offset(-5.0F, 2.5F, 0.0F));
		Body.addOrReplaceChild("left_sleeve_slim", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.5F, 0.0F));
		Body.addOrReplaceChild("right_sleeve_slim", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
		switch (entity.visualData.getHeadModel()){
			case "none":{
				this.anime_eye_type_1_up.visible = false;
				this.anime_eye_type_1_down.visible = false;
				this.steve_eye_type_1_up.visible = false;
				this.steve_eye_type_1_down.visible = false;
				this.AET1_time = 0.0D;
				break;
			}
			case "anime_eyes_type_1":{
				this.steve_eye_type_1_up.visible = false;
				this.steve_eye_type_1_down.visible = false;
				loadAnimeEyes1Anim();
				break;
			}
			case "steve_eyes_type_1":{
				this.anime_eye_type_1_up.visible = false;
				this.anime_eye_type_1_down.visible = false;
				loadSteveEyes1Anim();
				break;
			}
		}
		switch (entity.visualData.getBodyModel()){
			case "none":{
				this.breasts_type_1.visible = false;
				this.breasts_type_2.visible = false;
				break;
			}
			case "breasts_type_1":{
				this.breasts_type_1.visible = true;
				this.breasts_type_2.visible = false;
				break;
			}
			case  "breasts_type_2":{
				this.breasts_type_1.visible = false;
				this.breasts_type_2.visible = true;
				break;
			}
		}
		switch (entity.visualData.getArmsModel()){
			case "none":{
				this.left_arm_slim.visible = false;
				this.right_arm_slim.visible = false;
				this.leftArm.visible = true;
				this.rightArm.visible = true;
				this.left_sleeve_slim.visible = false;
				this.right_sleeve_slim.visible = false;
				this.leftSleeve.visible = true;
				this.rightSleeve.visible = true;
				break;
			}
			case "slim":{
				this.leftArm.visible = false;
				this.rightArm.visible = false;
				this.left_arm_slim.visible = true;
				this.right_arm_slim.visible = true;
				this.left_sleeve_slim.visible = true;
				this.right_sleeve_slim.visible = true;
				this.leftSleeve.visible = false;
				this.rightSleeve.visible = false;
				this.left_sleeve_slim.copyFrom(this.leftSleeve);
				this.right_sleeve_slim.copyFrom(this.rightSleeve);
				this.left_arm_slim.copyFrom(this.leftArm);
				this.right_arm_slim.copyFrom(this.rightArm);
				break;
			}
		}
		switch (entity.visualData.getLegsModel()){
			case "none":{
				break;
			}
		}


	}
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e){
		this.AET1_time += 0.05;
	}
	private void loadAnimeEyes1Anim(){
		double y = 0;
		if(this.AET1_time >= 7){
			if(this.AET1_time <= 8){
				this.anime_eye_type_1_up.visible = true;
				this.anime_eye_type_1_down.visible = true;
				double offset = 0;
				double PHASE_SHIFT = 0.0;
				double AMPLITUDE = 1.5;
				double PERIOD = 1;
				double time = this.AET1_time;
				y = AMPLITUDE * Math.sin((time / PERIOD - PHASE_SHIFT) * 2 * Math.PI) + offset;
			} else {
				this.AET1_time = 0.0D;
			}
		} else {
			this.anime_eye_type_1_up.visible = false;
			this.anime_eye_type_1_down.visible = false;
		}
		if(y >= 0){
			anime_eye_type_1_up.y = (float) (-4.0f + (y / 3));
			anime_eye_type_1_up.yScale = (float) y / 3;
			anime_eye_type_1_down.y = (float) (-2.0f - (y / 3));
			anime_eye_type_1_down.yScale = (float) y / 3;
		}
	}
	private void loadSteveEyes1Anim(){
		double y = 0;
		if(this.AET1_time >= 7){
			if(this.AET1_time <= 8){
				this.steve_eye_type_1_up.visible = true;
				this.steve_eye_type_1_down.visible = true;
				double offset = 0;
				double PHASE_SHIFT = 0.0;
				double AMPLITUDE = 0.9;
				double PERIOD = 1;
				double time = this.AET1_time;
				y = AMPLITUDE * Math.sin((time / PERIOD - PHASE_SHIFT) * 2 * Math.PI) + offset;
			} else {
				this.AET1_time = 0.0D;
			}
		} else {
			this.steve_eye_type_1_up.visible = false;
			this.steve_eye_type_1_down.visible = false;
		}
		if(y >= 0){
			steve_eye_type_1_up.y = (float) (-4.0f + (y / 3));
			steve_eye_type_1_up.yScale = (float) y / 3;
			steve_eye_type_1_down.y = (float) (-3.0f - (y / 3));
			steve_eye_type_1_down.yScale = (float) y / 3;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.renderToBuffer(poseStack,vertexConsumer,packedLight,packedOverlay,red,green,blue,alpha);
	}
}
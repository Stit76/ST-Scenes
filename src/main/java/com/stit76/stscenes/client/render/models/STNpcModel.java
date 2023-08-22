package com.stit76.stscenes.client.render.models;// Made with Blockbench 4.8.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.common.entity.STNpc;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class STNpcModel<T extends STNpc> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(STScenes.MODID, "stnpcmodel"), "main");

	public STNpcModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.25f), 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch);
		if(entity.isOnFire()){
			this.leftArm.xRot = 3.1f;
			this.rightArm.xRot = 3.1f;
		} else {
			this.leftArm.xRot = 0f;
			this.rightArm.xRot = 0f;
		}
	}

	//@Override
	//public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
	//	//Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//	//Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//	//RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//	//LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//	//RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//	//LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	//}
}
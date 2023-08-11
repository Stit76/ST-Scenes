package com.stit76.stscenes.common.item;

import com.stit76.stscenes.STScenes;
import com.stit76.stscenes.client.FileUtils;
import com.stit76.stscenes.client.gui.npcCustomizer.NpcCustomizerScreen;
import com.stit76.stscenes.common.entity.AbstractSTNPC;
import com.stit76.stscenes.core.init.EntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class NPCSpawner extends Item {
    public NPCSpawner(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        if(p_41399_.getLevel().isClientSide){
            if(p_41399_.isShiftKeyDown()){
                List<ResourceLocation> resourceLocations = FileUtils.getAllPNGFilesInFolder();
                for (int i = 0;i < resourceLocations.size();i++) {
                    p_41399_.sendSystemMessage(Component.nullToEmpty(resourceLocations.get(i).getPath()));
                }
            } else {
                if(p_41400_ instanceof AbstractSTNPC) {
                    NpcCustomizerScreen npcCustomizerScreen = new NpcCustomizerScreen(Component.translatable("npc_spawner.npc_customizer_screen.title"), (AbstractSTNPC) p_41400_);
                    Minecraft.getInstance().setScreen(npcCustomizerScreen);
                }
            }
        }
        return super.interactLivingEntity(p_41398_, p_41399_, p_41400_, p_41401_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if(!level.isClientSide){
            EntityInit.ST_NPC.get().spawn((ServerLevel) level,new BlockPos(
                    context.getClickedPos().getX(),context.getClickedPos().getY() + 1,context.getClickedPos().getZ())
                    ,MobSpawnType.SPAWN_EGG);
        }
        return super.useOn(context);
    }
}

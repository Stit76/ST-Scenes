package com.stit76.stscenes;

import com.stit76.stscenes.core.init.EntityInit;
import com.stit76.stscenes.core.init.ItemInit;
import com.stit76.stscenes.networking.SimpleNetworkWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(STScenes.MODID)
public class STScenes
{
    public static final String MODID = "stscenes";
    public STScenes()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //Scene scene = new Scene("FirstScene");
        //scene.updateDate();
        //Scenes.sceneList.add(scene);


        EntityInit.ENTITES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }
    private void commonSetup(final FMLClientSetupEvent clientSetupEvent){
        SimpleNetworkWrapper.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        ItemInit.addItemsCreativeTab(event);
    }
}
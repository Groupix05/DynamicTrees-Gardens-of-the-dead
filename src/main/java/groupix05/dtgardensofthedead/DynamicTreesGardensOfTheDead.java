package groupix05.dtgardensofthedead;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(DynamicTreesGardensOfTheDead.MOD_ID)
public final class DynamicTreesGardensOfTheDead {

    public static final String MOD_ID = "dtgardensofthedead";

    public DynamicTreesGardensOfTheDead() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);


        RegistryHandler.setup(MOD_ID);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DTGardensOfTheDeadRegistries.setup();
    }

    private void gatherData(final GatherDataEvent event) {
        GatherDataHelper.gatherTagData(MOD_ID, event);
        GatherDataHelper.gatherLootData(MOD_ID, event);
    }

}

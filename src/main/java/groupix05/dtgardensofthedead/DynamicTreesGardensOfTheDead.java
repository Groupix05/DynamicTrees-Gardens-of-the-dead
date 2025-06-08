package groupix05.dtgardensofthedead;

import com.ferreusveritas.dynamictrees.api.GatherDataHelper;
import com.ferreusveritas.dynamictrees.api.registry.RegistryHandler;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.block.rooty.SoilProperties;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.tree.species.Species;
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
        GatherDataHelper.gatherAllData(MOD_ID, event,
                SoilProperties.REGISTRY,
                Family.REGISTRY,
                Species.REGISTRY,
                LeavesProperties.REGISTRY
                //Fruit.REGISTRY,
                //Pod.REGISTRY,
                //CapProperties.REGISTRY //Requires DTPlus
        );
    }

    public static ResourceLocation location(final String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}

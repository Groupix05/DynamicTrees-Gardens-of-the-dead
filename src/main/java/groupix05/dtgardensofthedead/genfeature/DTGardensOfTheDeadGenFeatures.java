package groupix05.dtgardensofthedead.genfeature;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import net.minecraft.resources.ResourceLocation;
import groupix05.dtgardensofthedead.DynamicTreesGardensOfTheDead;

public class DTGardensOfTheDeadGenFeatures {

    public static final GenFeature SOULBLIGHT_VINES = new SoulblightVinesGenFeature(new ResourceLocation(DynamicTreesGardensOfTheDead.MOD_ID, "soulblight_vines"));
    public static final GenFeature FLOOR_VINES = new FloorVinesGenFeature(new ResourceLocation(DynamicTreesGardensOfTheDead.MOD_ID, "floor_vines"));

    public static void register(final Registry<GenFeature> registry) {
        registry.registerAll(SOULBLIGHT_VINES, FLOOR_VINES);
    }

}

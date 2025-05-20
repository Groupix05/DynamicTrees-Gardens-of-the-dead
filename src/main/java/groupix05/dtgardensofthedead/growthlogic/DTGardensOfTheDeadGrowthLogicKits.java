package groupix05.dtgardensofthedead.growthlogic;

import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import net.minecraft.resources.ResourceLocation;
import groupix05.dtgardensofthedead.DynamicTreesGardensOfTheDead;

public class DTGardensOfTheDeadGrowthLogicKits {

    public static final GrowthLogicKit SOULBLIGHT = new SoulblightLogic(new ResourceLocation(DynamicTreesGardensOfTheDead.MOD_ID, "soulblight"));

    public static void register(final Registry<GrowthLogicKit> registry) {
        registry.registerAll(SOULBLIGHT);
    }

}

package groupix05.dtgardensofthedead;

import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.registry.TypeRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.FeatureCanceller;
import com.ferreusveritas.dynamictrees.block.leaves.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.genfeature.BeeNestGenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.tree.family.Family;
import com.ferreusveritas.dynamictrees.util.CommonVoxelShapes;
import com.ferreusveritas.dynamictrees.worldgen.featurecancellation.MushroomFeatureCanceller;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.systems.BranchConnectables;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import groupix05.dtgardensofthedead.cell.DTGardensOfTheDeadCellKits;
import groupix05.dtgardensofthedead.growthlogic.DTGardensOfTheDeadGrowthLogicKits;
import groupix05.dtgardensofthedead.genfeature.DTGardensOfTheDeadGenFeatures;
import groupix05.dtgardensofthedead.trees.*;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTGardensOfTheDeadRegistries {

    private static VoxelShape box(double p_49797_, double p_49798_, double p_49799_, double p_49800_, double p_49801_, double p_49802_) {
        return Shapes.box(p_49797_ / (double)16.0F, p_49798_ / (double)16.0F, p_49799_ / (double)16.0F, p_49800_ / (double)16.0F, p_49801_ / (double)16.0F, p_49802_ / (double)16.0F);
    }

    public static final VoxelShape MUSHROOM_STEM = box(7D, 0D, 7D, 9D, 7D, 9D);
    public static final VoxelShape MUSHROOM_CAP_BOTTOM = box(5D, 7D, 5D, 11D, 9D, 11D);
    public static final VoxelShape MUSHROOM_CAP_TOP = box(6D, 9D, 6D, 10D, 10D, 10D);

    public static final VoxelShape SOULBLIGHT_MUSHROOM = Shapes.or(
        MUSHROOM_STEM,
        MUSHROOM_CAP_BOTTOM,
        MUSHROOM_CAP_TOP
    );

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(DynamicTreesGardensOfTheDead.location("soulblight_cap").toString(), SOULBLIGHT_MUSHROOM);
    }

    @SubscribeEvent
    public static void onGrowthLogicKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GrowthLogicKit> event) {
        DTGardensOfTheDeadGrowthLogicKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onCellKitRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<CellKit> event) {
        DTGardensOfTheDeadCellKits.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void onGenFeatureRegistry(final com.ferreusveritas.dynamictrees.api.registry.RegistryEvent<GenFeature> event) {
        DTGardensOfTheDeadGenFeatures.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerFamilyTypes (final TypeRegistryEvent<Family> event) {
        event.registerType(DynamicTreesGardensOfTheDead.location("soulblight"), SoulblightSpecies.TYPE);
    }
}

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
import net.jadenxgamer.netherexp.registry.block.JNEBlocks;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DTGardensOfTheDeadRegistries {

    public static final VoxelShape MUSHROOM_STEM = Block.box(7D, 0D, 7D, 9D, 7D, 9D);
    public static final VoxelShape MUSHROOM_CAP_BOTTOM = Block.box(5D, 7D, 5D, 11D, 9D, 11D);
    public static final VoxelShape MUSHROOM_CAP_TOP = Block.box(6D, 9D, 6D, 10D, 10D, 10D);

    public static final VoxelShape SOULBLIGHT_MUSHROOM = Shapes.or(
        MUSHROOM_STEM,
        MUSHROOM_CAP_BOTTOM,
        MUSHROOM_CAP_TOP
    );

    public static void setup() {
        if (ModList.get().isLoaded("netherexp")){
            setupConnectables();
        }
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

    private static void setupConnectables() {
        BranchConnectables.makeBlockConnectable(JNEBlocks.SHROOMBLIGHT.get(), (state, level, pos, side) -> {
            if (side == Direction.DOWN) {
                BlockState branchState = level.getBlockState(pos.relative(Direction.UP));
                BranchBlock branch = TreeHelper.getBranch(branchState);
                if (branch != null) {
                    return Mth.clamp(branch.getRadius(branchState) - 1, 1, 8);
                } else {
                    return 8;
                }
            }
            return 0;
        });
    }



}

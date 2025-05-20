package groupix05.dtgardensofthedead.genfeature;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.configuration.ConfigurationProperty;
import com.ferreusveritas.dynamictrees.api.network.MapSignal;
import com.ferreusveritas.dynamictrees.block.branch.BranchBlock;
import com.ferreusveritas.dynamictrees.block.leaves.DynamicLeavesBlock;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGenerationContext;
import com.ferreusveritas.dynamictrees.systems.genfeature.context.PostGrowContext;
import com.ferreusveritas.dynamictrees.systems.nodemapper.FindEndsNode;
import com.ferreusveritas.dynamictrees.tree.species.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeature;
import com.ferreusveritas.dynamictrees.systems.genfeature.GenFeatureConfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class FloorVinesGenFeature extends GenFeature {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Vine Type - this tells the generator which side the vines generate on - ceiling for vines that grow from the
     * ceiling downward like weeping vines, floor for vines that grow from the ground upward like twisting vines, and
     * side for vines that grow on the side of blocks likes regular vines.
     */
    protected final BooleanProperty[] sideVineStates = new BooleanProperty[]{null, null, VineBlock.NORTH, VineBlock.SOUTH, VineBlock.WEST, VineBlock.EAST};

    public static final ConfigurationProperty<Block> BLOCK = ConfigurationProperty.block("block");

    public FloorVinesGenFeature(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    protected void registerProperties() {
        this.register(QUANTITY, VERTICAL_SPREAD, RAY_DISTANCE, BLOCK, FRUITING_RADIUS);
    }

    @Override
    public GenFeatureConfiguration createDefaultConfiguration() {
        return super.createDefaultConfiguration()
                .with(QUANTITY, 4)
                .with(VERTICAL_SPREAD, 60f)
                .with(RAY_DISTANCE, 5f)
                .with(BLOCK, Blocks.VINE)
                .with(FRUITING_RADIUS, -1);
    }

    @Override
    protected boolean postGenerate(GenFeatureConfiguration configuration, PostGenerationContext context) {
        if (!context.isWorldGen() || context.endPoints().isEmpty()) {
            return false;
        }

        final int quantity = configuration.get(QUANTITY);

        for (int i = 0; i < quantity; i++) {
            final BlockPos endPoint = context.endPoints().get(context.random().nextInt(context.endPoints().size()));
            this.addFloorVines(configuration, context.level(), context.species(), context.pos(), endPoint, context.bounds());
        }

        return true;
    }

    @Override
    protected boolean postGrow(GenFeatureConfiguration configuration, PostGrowContext context) {
        final LevelAccessor level = context.level();
        final BlockPos rootPos = context.pos();
        final Species species = context.species();
        final int fruitingRadius = configuration.get(FRUITING_RADIUS);

        if (fruitingRadius < 0 || context.fertility() < 1) {
            return false;
        }

        final BlockState blockState = level.getBlockState(context.treePos());
        final BranchBlock branch = TreeHelper.getBranch(blockState);

        if (branch != null && branch.getRadius(blockState) >= fruitingRadius && context.natural()) {
            if (species.seasonalFruitProductionFactor(context.levelContext(), rootPos) > level.getRandom().nextFloat()) {
                final FindEndsNode endFinder = new FindEndsNode();
                TreeHelper.startAnalysisFromRoot(level, rootPos, new MapSignal(endFinder));
                final List<BlockPos> endPoints = endFinder.getEnds();
                final int qty = configuration.get(QUANTITY);

                if (!endPoints.isEmpty()) {
                    for (int i = 0; i < qty; i++) {
                        BlockPos endPoint = endPoints.get(level.getRandom().nextInt(endPoints.size()));
                        this.addFloorVines(configuration, level, species, rootPos, endPoint, SafeChunkBounds.ANY);
                    }
                    return true;
                }
            }
        }

        return true;
    }

    protected void addFloorVines(GenFeatureConfiguration configuration, LevelAccessor level, Species species, BlockPos rootPos, BlockPos branchPos, SafeChunkBounds safeBounds) {
        // Uses fruit ray trace method to grab a position under the tree's leaves.
        BlockPos vinePos = CoordUtils.getRayTraceFruitPos(level, species, rootPos, branchPos, safeBounds);

        if (!safeBounds.inBounds(vinePos, true)) {
            return;
        }

        vinePos = this.findAboveLeaves(level, vinePos, safeBounds);

        if (vinePos == BlockPos.ZERO) {
            return;
        }

        this.placeVines(level, vinePos, configuration.get(BLOCK).defaultBlockState());
    }

    private BlockPos findAboveLeaves(LevelAccessor level, BlockPos vinePos, SafeChunkBounds safeBounds) {
        BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos(vinePos.getX(), vinePos.getY(), vinePos.getZ());
        mPos.move(Direction.UP);
        if(!safeBounds.inBounds(mPos, true)) {
            return BlockPos.ZERO;
        }
        Block block = level.getBlockState(mPos).getBlock();
        if(!(level.getBlockState(mPos).getBlock() instanceof DynamicLeavesBlock)){
            return BlockPos.ZERO;
        }
        for (int i = 0; i < 5; i++) {
            mPos.move(Direction.UP);

            if(!safeBounds.inBounds(mPos, true)){
                break;
            }
            boolean isLeaf = level.getBlockState(mPos).getBlock() instanceof DynamicLeavesBlock;
            boolean isEmpty = level.isEmptyBlock(mPos);

            if (!isLeaf && !isEmpty) {
                break;
            }
            if (!isLeaf && isEmpty) {
                return mPos.immutable();
            }
        }
        return BlockPos.ZERO;
    }

    protected void placeVines(LevelAccessor level, BlockPos vinePos, BlockState vinesState) {
        final BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos(vinePos.getX(), vinePos.getY(), vinePos.getZ());
        level.setBlock(mPos,vinesState,3);
    }

}

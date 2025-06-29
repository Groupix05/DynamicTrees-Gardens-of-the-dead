package groupix05.dtgardensofthedead.growthlogic;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKitConfiguration;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionManipulationContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.DirectionSelectionContext;
import com.ferreusveritas.dynamictrees.growthlogic.context.PositionalSpeciesContext;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SoulblightLogic extends GrowthLogicKit {

    private static final int MIN_CAP_HEIGHT = 5; // max height is 3 with 5 idk why
    private static final int HEIGHT_VARIATION = 3;

    public SoulblightLogic(final ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public Direction selectNewDirection(GrowthLogicKitConfiguration configuration, DirectionSelectionContext context) {
        final Direction newDir = super.selectNewDirection(configuration, context);
        if (context.signal().isInTrunk() && newDir != Direction.UP) { // Turned out of trunk
            context.signal().energy = Math.min(context.signal().energy, context.species().isMegaSpecies() ? 3 : 2);
        }
        return newDir;
    }

    @Override
    public int[] populateDirectionProbabilityMap(GrowthLogicKitConfiguration configuration,
                                                DirectionManipulationContext context) {
        boolean tip = context.signal().energy < 2;
        return new int[]{0, tip?1:0, 1, 1, 1, 1};//{down,up,north,south,west,est}
    }

    private float getHashedVariation(Level level, BlockPos pos) {
        long day = level.getGameTime() / 24000L;
        int month = (int) day / 30;
        return CoordUtils.coordHashCode(pos.above(month), 2) % HEIGHT_VARIATION;
    }

    @Override
    public float getEnergy(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return Math.min(super.getLowestBranchHeight(configuration, context) +
                        MIN_CAP_HEIGHT +
                        getHashedVariation(context.level(), context.pos()) / 1.5f,
                super.getEnergy(configuration, context));
    }

    @Override
    public int getLowestBranchHeight(GrowthLogicKitConfiguration configuration, PositionalSpeciesContext context) {
        return (int) (super.getLowestBranchHeight(configuration, context) *
                context.species().biomeSuitability(context.level(), context.pos()) +
                getHashedVariation(context.level(), context.pos()));
    }
}

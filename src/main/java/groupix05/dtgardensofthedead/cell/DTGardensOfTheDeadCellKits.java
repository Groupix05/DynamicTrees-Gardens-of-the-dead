package groupix05.dtgardensofthedead.cell;

import com.ferreusveritas.dynamictrees.api.cell.Cell;
import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.cell.CellNull;
import com.ferreusveritas.dynamictrees.api.cell.CellSolver;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.cell.CellKits;
import com.ferreusveritas.dynamictrees.cell.MetadataCell;
import com.ferreusveritas.dynamictrees.cell.NormalCell;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import groupix05.dtgardensofthedead.DynamicTreesGardensOfTheDead;

public class DTGardensOfTheDeadCellKits {

    public static final CellKit SOULBLIGHT = new CellKit(new ResourceLocation(DynamicTreesGardensOfTheDead.MOD_ID, "soulblight")) {

        private final Cell[] soulblightCells = {
                CellNull.NULL_CELL,
                new SoulblightLeafCell(1),
                new SoulblightLeafCell(2),
                new SoulblightLeafCell(3),
                new SoulblightLeafCell(4),
                new SoulblightLeafCell(5),
                new SoulblightLeafCell(6),
                new SoulblightLeafCell(7)
        };

        private final Cell branchCell = new Cell() {
            @Override
            public int getValue() {
                return 5;
            }

            final int[] map = {0, 3, 5, 5, 5, 5};

            @Override
            public int getValueFromSide(Direction side) {
                return map[side.ordinal()];
            }

        };

        private final CellSolver SoulblightSolver = new CellKits.BasicSolver(new short[]{0x0514, 0x0423, 0x0412, 0x0312, 0x0211});

        @Override
        public Cell getCellForLeaves(int hydro) {
            return soulblightCells[hydro];
        }

        @Override
        public Cell getCellForBranch(int radius, int meta) {
            if (radius == 3) {
                return branchCell;
            }
            return CellNull.NULL_CELL;
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return DTGardensOfTheDeadLeafClusters.SOULBLIGHT;
        }

        @Override
        public CellSolver getCellSolver() {
            return SoulblightSolver;
        }

        @Override
        public int getDefaultHydration() {
            return 7;
        }

    };

    public static void register(final Registry<CellKit> registry) {
        registry.registerAll(SOULBLIGHT);
    }

}

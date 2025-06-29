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
                return 4;
            }

            final int[] map = {0, 5, 4, 4, 4, 4};

            @Override
            public int getValueFromSide(Direction side) {
                return map[side.ordinal()];
            }

        };

        private final CellSolver SoulblightSolver = new CellKits.BasicSolver(new short[]{0x512, 0x0413, 0x0322, 0x0311, 0x0211});
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
            return 4;
        }

    };

    public static void register(final Registry<CellKit> registry) {
        registry.registerAll(SOULBLIGHT);
    }

}

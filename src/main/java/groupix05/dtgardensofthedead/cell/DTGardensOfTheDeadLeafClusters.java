package groupix05.dtgardensofthedead.cell;

import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.core.BlockPos;

public class DTGardensOfTheDeadLeafClusters {

    public static final SimpleVoxmap SOULBLIGHT = new SimpleVoxmap(5, 2, 5, new byte[]{

            //Layer 1 (Cap Bottom)
            0, 1, 1, 1, 0,
            1, 2, 3, 2, 1,
            1, 3, 0, 3, 1,
            1, 2, 3, 2, 1,
            0, 1, 1, 1, 0,

            //Layer 2 (Cap Top)
            0, 0, 0, 0, 0,
            0, 1, 2, 1, 0,
            0, 2, 3, 2, 0,
            0, 1, 2, 1, 0,
            0, 0, 0, 0, 0

    }).setCenter(new BlockPos(2, 0, 2));

}

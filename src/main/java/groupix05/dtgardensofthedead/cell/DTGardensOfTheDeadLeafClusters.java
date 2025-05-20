package groupix05.dtgardensofthedead.cell;

import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.core.BlockPos;

public class DTGardensOfTheDeadLeafClusters {

    public static final SimpleVoxmap SOULBLIGHT = new SimpleVoxmap(3, 6, 3, new byte[]{

            //Layer 0(Droop Bottom)
            1, 0, 1,
            0, 0, 0,
            1, 0, 1,

            //Layer 1(Droop Middle)
            2, 0, 2,
            0, 0, 0,
            2, 0, 2,

            //Layer 2(Droop Top)
            3, 0, 3,
            0, 0, 0,
            3, 0, 3,

            //Layer 3(Cap Bottom)
            5, 6, 5,
            6, 7, 6,
            5, 6, 5,

            //Layer 4(Cap Middle)
            6, 7, 6,
            7, 0, 7,
            6, 7, 6,

            //Layer 5 (Cap Top)
            5, 6, 5,
            6, 7, 6,
            5, 6, 5

    }).setCenter(new BlockPos(1, 4, 1));

}

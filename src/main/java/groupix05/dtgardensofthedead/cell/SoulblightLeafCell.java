package groupix05.dtgardensofthedead.cell;

import com.ferreusveritas.dynamictrees.cell.MatrixCell;

public class SoulblightLeafCell extends MatrixCell {

    public SoulblightLeafCell(int value) {
        super(value, valMap);
    }

    /**
     * For hydro 6 and 7, the values stay the same. For hydro 5 and lower, only the bottom returns its value, all other
     * faces are set to 0. This is to cause the droopy wart effect
     */
    static final byte[] valMap = {
            0, 0, 0, 0, 0, 0, 0, 0, //D Maps * -> 0
            0, 0, 0, 0, 0, 0, 0, 0, //U Maps 3 -> 3, 4 -> 3, * -> 0
            0, 1, 2, 3, 4, 0, 0, 0, //N Maps * -> *
            0, 1, 2, 3, 4, 0, 0, 0, //S Maps * -> *
            0, 1, 2, 3, 4, 0, 0, 0, //W Maps * -> *
            0, 1, 2, 3, 4, 0, 0, 0  //E Maps * -> *
    };

}

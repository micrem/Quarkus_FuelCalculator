package org.dhbw.mosbach.ai.spritCalc18A;

import org.dhbw.mosbach.ai.spritCalc18A.externalAPIs.tankerKoenig.PetrolTyp;

import java.io.IOException;
import java.util.List;

/**
 * Zum manuellen Testen der der implimentieren Logik
 */
public class SpritCalcBLTest {

    public static void main(String[] args) throws IOException {
        SpritCalcBL spritCalcBL = new SpritCalcBL();
        List<SpritCalcBLData> spritCalcBLData = spritCalcBL.apiSearchStart("Lohrtalweg", 10, 78421, "Mosbach", PetrolTyp.e10, 20.5, 10);
        System.out.println(spritCalcBLData);
        System.out.println(spritCalcBLData.get(0).getTankerKoenigData().getName());
    }
}

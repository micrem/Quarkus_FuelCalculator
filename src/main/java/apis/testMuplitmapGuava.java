package apis;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.util.Map;

public class testMuplitmapGuava {
    // https://www.codeflow.site/de/article/guava-multimap
    public static void main(String[] args) {
        Multimap<Integer,String> multimap = TreeMultimap.create();
        multimap.put(155,"eins");
        multimap.put(50,"dsfsfd");
        multimap.put(155,"zwei");
        multimap.put(155,"sfsf");
        multimap.put(15,"sfsf");
        System.out.println(multimap.entries());

        int i = 0;
        for (Map.Entry<Integer, String> mapDatea: multimap.entries()) {
            if (i==3){
                break;
            }
            i++;
            System.out.println(mapDatea.getValue());
        }



    }









//    public static void main(String[] args) {
//        Map<Float, String> ascsortedMAP = new TreeMap<Float, String>();
//
//        ascsortedMAP.put(8f, "name8");
//        ascsortedMAP.put(5f, "name5");
//        ascsortedMAP.put(15f, "name15");
//        ascsortedMAP.put(35f, "name35");
//        ascsortedMAP.put(44f, "name44");
//        ascsortedMAP.put(7f, "name7");
//        ascsortedMAP.put(5f, "name6");
//
//        for (Map.Entry<Float, String> mapData : ascsortedMAP.entrySet()) {
//            System.out.println("Key : " + mapData.getKey() + "Value : " + mapData.getValue());
//        }
//    }
}

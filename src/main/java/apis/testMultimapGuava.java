package apis;

import apis.openCageAPI.Geocode;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.TreeMultimap;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class testMultimapGuava {
    // https://www.codeflow.site/de/article/guava-multimap
    public static void main(String[] args) {
        Multimap<Integer, Geocode> sortedMap = Multimaps.newListMultimap(new TreeMap<>(Collections.reverseOrder()), Lists::newArrayList);
        Multimap<Integer, Geocode> multimap9 = Multimaps.newMultimap(new TreeMap<>(Collections.reverseOrder()), Lists::newArrayList);


        //       Multimap<Integer,Geocode> multimap99 = TreeMultimap.create();
//        multimap99.put(7,new Geocode(15,55));
//        multimap99.put(1,new Geocode(88,55));
//        multimap99.put(1,new Geocode(55,55));
//        multimap99.put(2,new Geocode(1,55));
//        System.out.println(multimap99.entries());
//        for (Map.Entry<Integer, Geocode> mapData: multimap99.entries()) {
//            System.out.println(mapData.getValue().getLng());
//        }

        Multimap<Integer, UUID> multimap5 = TreeMultimap.create();
        multimap5.put(7, UUID.randomUUID());
        multimap5.put(1, UUID.randomUUID());
        multimap5.put(1, UUID.randomUUID());
        multimap5.put(2, UUID.randomUUID());

        System.out.println(multimap5.entries());
        for (Map.Entry<Integer, UUID> mapData : multimap5.entries()) {
            System.out.println(mapData.getValue().toString());
        }


//        for (Multimap<Integer,Geocode> multimap11: multimap.) {
//
//        }

//        multimap.put(155,"eins");
//        multimap.put(50,"dsfsfd");
//        multimap.put(155,"zwei");
//        multimap.put(155,"sfsf");
//        multimap.put(15,"sfsf");
//        System.out.println(multimap.entries());
//
//        int i = 0;
//        for (Map.Entry<Integer, String> mapDatea: multimap.entries()) {
//            if (i==3){
//                break;
//            }
//            i++;
//            System.out.println(mapDatea.getValue());
//        }


        Map<Float, String> ascsortedMAP = new TreeMap<Float, String>();
        Map<Float, Geocode> ascsortedMAP2 = new TreeMap<>();

        ascsortedMAP2.put(7f, new Geocode(15, 55));
        ascsortedMAP2.put(7f, new Geocode(88, 55));

        for (Map.Entry<Float, Geocode> mapData : ascsortedMAP2.entrySet()) {
            System.out.println("Key : " + mapData.getKey() + "Value : " + mapData.getValue().getLng());
        }


        ascsortedMAP.put(8f, "name8");
        ascsortedMAP.put(5f, "name5");
        ascsortedMAP.put(15f, "name15");
        ascsortedMAP.put(35f, "name35");
        ascsortedMAP.put(44f, "name44");
        ascsortedMAP.put(7f, "name7");
        ascsortedMAP.put(5f, "name6");

        for (Map.Entry<Float, String> mapData : ascsortedMAP.entrySet()) {
            System.out.println("Key : " + mapData.getKey() + "Value : " + mapData.getValue());
        }


    }


}

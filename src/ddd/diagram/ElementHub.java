package ddd.diagram;


import java.util.*;

public class ElementHub {
    private static Map<String, Set<MetaInfo>> elementMap = new HashMap();
    static String[] keys = {"AggregatePart", "AggregateRoot", "DomainService",
            "Entity", "Event", "Repository", "Specification", "ValueObject"};

    public static void put(MetaInfo metaInfo, String key) {
        if (Arrays.asList(keys).contains(key)) {
            if (elementMap.get(key) == null) {
                HashSet<MetaInfo> set = new HashSet<>();
                set.add(metaInfo);
                elementMap.put(key, set);
            } else {
                elementMap.get(key).add(metaInfo);
            }
        }

    }

    public static void remove(MetaInfo metaInfo, String key) {
        if (Arrays.asList(keys).contains(key)) {
            if (elementMap.get(key) != null) {
                elementMap.get(key).remove(metaInfo);
            }
        }

    }

    public static Map<String, Set<MetaInfo>> getElementMap() {
        return elementMap;
    }

    public static String[] getKeys() {
        return keys;
    }


}

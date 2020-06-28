package ddd.diagram;


import com.intellij.psi.PsiPackage;

import java.util.*;

public class ElementHub {
    private static Map<String, Set<MetaInfo>> elementMap=new HashMap<>() ;
    private  static  List<NameSpace> contextNameSpace=new ArrayList<>();
    static String[] keys = {"AggregatePart", "AggregateRoot", "DomainService",
            "Entity", "DomainEvent", "Repository", "Specification", "ValueObject","Factory"};

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
    public  static void  removeAll(){
        elementMap.clear();
        contextNameSpace.clear();
    }
    public static void setElementMap(Map<String, Set<MetaInfo>> elementMap) {
        ElementHub.elementMap = elementMap;
    }

    public static Map<String, Set<MetaInfo>> getElementMap() {
        return elementMap;
    }

    public static String[] getKeys() {
        return keys;
    }

    public static List<NameSpace> getContextNameSpace() {
        return contextNameSpace;
    }

    public static void setContextNameSpace(List<NameSpace> contextNameSpace) {
        ElementHub.contextNameSpace = contextNameSpace;
    }
}

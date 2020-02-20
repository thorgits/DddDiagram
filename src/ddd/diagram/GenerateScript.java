package ddd.diagram;

import ddd.diagram.script.*;

import java.util.Map;
import java.util.Set;

public class GenerateScript {
    public static Script projectView() {
        Map<String, Set<MetaInfo>> elementMap = ElementHub.getElementMap();
        Set<String> elementSet = elementMap.keySet();
        Script script = new Script();
        for (String c : elementSet) {
            Set<MetaInfo> metaInfoSet = elementMap.get(c);
            for (MetaInfo meta : metaInfoSet) {
                Item item = new Item();
                script.getItems().add(item.createItem(meta));
                Relation relation = new Relation();
                String rel = relation.createRelation(meta);
                if (!rel.isEmpty()) {
                    script.getRelations().add(rel);
                }
            }
        }
        setSkin(script);
        return script;
    }

    public static Script sketchView() {
        Map<String, Set<MetaInfo>> elementMap = ElementHub.getElementMap();
        Set<String> elementSet = elementMap.keySet();
        Script script = new Script();
        for (String c : elementSet) {
            Set<MetaInfo> metaInfoSet = elementMap.get(c);
            for (MetaInfo meta : metaInfoSet) {
                Item item = new Item();
                script.getItems().add(item.createItemSketch(meta));
                Relation relation = new Relation();
                String rel = relation.createSketchRelation(meta);
                if (!rel.isEmpty()) {
                    script.getRelations().add(rel);
                }
            }
        }
        setSkin(script);
        return script;
    }

    public static Script setSkin(Script script) {
        script.setHeader(new Header("@startuml\n"));
        script.setStyle(new Style(
                "!includeurl https://raw.githubusercontent.com/xuanye/plantuml-style-c4/master/core.puml\n"));
        script.setFooter(new Footer("\n@enduml"));
        return script;
    }
}

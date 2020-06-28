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
        setProjectSkin(script);
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
        setSketchSkin(script);
        return script;
    }

    public static Script setProjectSkin(Script script) {
        script.setHeader(new Header("@startuml\n"));
        script.setStyle(new Style(
                "skinparam linetype ortho\n" +
                        "skinparam Shadowing false\n" +
                        "skinparam package {\n" +
                        "backgroundColor #E3F2FD\n" +
                        "}\n" +
                        "skinparam Class {\n" +
                        "    ClassBorderThickness 1\n" +
                        "    ArrowColor Gray\n" +
                        "    ActorBorderColor Gray\n" +
                        "    BackgroundColor #E3F2FD\n" +
                        "    BorderColor #64B5F6\n" +
                        "    FontSize 14\n" +
                        "    AttributeFontSize 14\n" +
                        "    StereotypeFontColor #589DF6\n" +
                        "    StereotypeFontSize 14\n" +
                        "}\n" +
                        "left to right direction\n"));
        script.setFooter(new Footer("\n@enduml"));
        return script;
    }
    public static Script setSketchSkin(Script script) {
        script.setHeader(new Header("@startuml\n"));
        script.setStyle(new Style(
                "skinparam linetype ortho\n" +
                        "skinparam Shadowing false\n" +
                        "skinparam package {\n" +
                        "backgroundColor #E3F2FD\n" +
                        "}\n" +
                        "skinparam Class {\n" +
                        "    ClassBorderThickness 1\n" +
                        "    ArrowColor Gray\n" +
                        "    ActorBorderColor Gray\n" +
                        "    BackgroundColor #E3F2FD\n" +
                        "    BorderColor #64B5F6\n" +
                        "    FontSize 14\n" +
                        "    AttributeFontSize 14\n" +
                        "    StereotypeFontColor #589DF6\n" +
                        "    StereotypeFontSize 14\n" +
                        "}\n"));
        script.setFooter(new Footer("\n@enduml"));
        return script;
    }
    public static Script setDependencySkin(Script script) {
        script.setHeader(new Header("@startuml\n"));
        script.setStyle(new Style(
                "top to bottom direction \n" +
                        "skinparam Shadowing false\n" +
                        "skinparam wrapWidth 200\n" +
                        "skinparam usecase {\n" +
                        "\tBackgroundColor #64B5F6\n" +
                        "\tBorderColor #64B5F6\n" +
                        "\tArrowColor Gray\n" +
                        "}\n"));
        script.setFooter(new Footer("\n@enduml"));
        return script;
    }
}

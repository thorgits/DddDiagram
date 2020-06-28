package ddd.template;

import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.ClassEntry;
import ddd.template.tool.StereotypeFactory;


public class StereotypeGenerator {
    public  static void generateStereotype(String annotation, AnActionEvent anActionEvent, ClassEntry classEntry){
        StereotypeFactory.createStereotype(annotation,anActionEvent,classEntry);
    }
}

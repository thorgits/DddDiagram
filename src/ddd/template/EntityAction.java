package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.EntityClass;


public class EntityAction extends AnAction  {
    String identifier="@DefinesIdentity";
    String root="DefaultRoot.class";
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        EntityClass entityClass=new EntityClass();
        entityClass.setRoot(root);
        entityClass.setIdentifier(identifier);
        StereotypeGenerator.generateStereotype("Entity",anActionEvent,entityClass);}
}

package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.AggregateRootClass;

public class AggregateRootAction extends AnAction {
    String identifier="@DefinesIdentity";
    String root="DefaultRoot.class";
    @Override
    public void actionPerformed(AnActionEvent e) {
        AggregateRootClass aggregateRootClass=new AggregateRootClass();
        aggregateRootClass.setIdentifier(identifier);
        aggregateRootClass.setRoot(root);
        StereotypeGenerator.generateStereotype("AggregateRoot", e,aggregateRootClass);
    }
}

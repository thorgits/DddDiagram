package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.EntityClass;
import ddd.template.tool.ValueObjectClass;

public class ValueObjectAction extends AnAction {
    String root="DefaultRoot.class";
    @Override
    public void actionPerformed(AnActionEvent e) {
        ValueObjectClass valueObjectClass=new ValueObjectClass();
        valueObjectClass.setRoot(root);
        StereotypeGenerator.generateStereotype("ValueObject", e,valueObjectClass);
    }
}

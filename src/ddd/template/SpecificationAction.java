package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.SpecificationClass;

public class SpecificationAction extends AnAction {
    String validateSpec="@ValidateSpec";
    @Override
    public void actionPerformed(AnActionEvent e) {
        SpecificationClass specificationClass=new SpecificationClass();
        specificationClass.setValidateSpec(validateSpec);
        StereotypeGenerator.generateStereotype("Specification",e,specificationClass);
    }
}

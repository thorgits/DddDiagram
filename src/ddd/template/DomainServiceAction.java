package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.ClassEntry;

public class DomainServiceAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ClassEntry classEntry=new ClassEntry();
        StereotypeGenerator.generateStereotype("DomainService", e,classEntry);
    }
}

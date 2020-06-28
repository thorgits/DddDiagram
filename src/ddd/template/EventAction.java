package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.EventClass;


public class EventAction extends AnAction {
    String identifier="@DefinesIdentity";
    @Override
    public void actionPerformed(AnActionEvent e) {
        EventClass eventClass=new EventClass();
        eventClass.setIdentifier(identifier);
        StereotypeGenerator.generateStereotype("Event", e,eventClass);

    }
}

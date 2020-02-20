package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.until.ActionFactory;

public class EventAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ActionFactory.createAction("Event", e);
    }
}
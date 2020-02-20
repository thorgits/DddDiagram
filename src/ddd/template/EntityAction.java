package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.until.ActionFactory;



public class EntityAction extends AnAction  {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ActionFactory.createAction("Entity",anActionEvent);}
}

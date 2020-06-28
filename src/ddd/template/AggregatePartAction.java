package ddd.template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.template.tool.AggregatePartClass;

public class AggregatePartAction extends AnAction  {
    String root="DefaultRoot.class";
    @Override
    public void actionPerformed(AnActionEvent e) {
        AggregatePartClass aggregatePartClass=new AggregatePartClass();
        aggregatePartClass.setRoot(root);
        StereotypeGenerator.generateStereotype("AggregatePart", e,aggregatePartClass);
    }
}

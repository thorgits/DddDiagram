package ddd.validator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import ddd.diagram.*;
import ddd.diagram.script.DependencyTree;
import ddd.diagram.script.Script;

import javax.swing.text.Element;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependencyAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AnalyseProject.validateAnalyse(e);
        Set<MetaInfo> metaInfoSet = ElementHub.getElementMap().get("AggregateRoot");
        Script script = new Script();
        for (MetaInfo m : metaInfoSet) {
            DependencyTree.createInvoke(m, new HashSet<>(), new HashSet<>());
            script.getRelations().addAll(DependencyTree.getScript().getRelations());
        }
        script = GenerateScript.setDependencySkin(script);
        DrawGraph.writeDependencyView(script, e);

    }
}

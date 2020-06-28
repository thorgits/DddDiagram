package ddd.diagram;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class AbstractViewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AnalyseProject.diagramAnalyse(e);
        DrawGraph.writeSketchView(GenerateScript.sketchView(), e,false);
    }
}

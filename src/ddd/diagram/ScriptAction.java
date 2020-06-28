package ddd.diagram;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ScriptAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AnalyseProject.diagramAnalyse(e);
        DrawGraph.writeProjectView(GenerateScript.projectView(), e,true);
        DrawGraph.writeSketchView(GenerateScript.sketchView(), e,true);
    }
}

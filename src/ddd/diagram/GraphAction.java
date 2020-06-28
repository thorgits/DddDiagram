package ddd.diagram;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;



public class GraphAction extends AnAction {
    Project project;

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getProject();
        AnalyseProject.diagramAnalyse(e);
        DrawGraph.writeProjectView(GenerateScript.projectView(), e,false);
        DrawGraph.writeSketchView(GenerateScript.sketchView(), e,false);
    }
}

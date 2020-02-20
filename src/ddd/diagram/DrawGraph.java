package ddd.diagram;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import ddd.diagram.script.Script;
import ddd.template.until.CreateFileAction;

import java.util.Set;

public class DrawGraph {
    public static void writeProjectView(Script script, AnActionEvent anActionEvent) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("skinparam linetype ortho\n" +
                "left to right direction\n" +
                "GREY_ARROW\n");
        out.append("\'!@Item\n");
        Set<String> items = script.getItems();
        for (String item : items) {
            out.append(item + "\n");
        }
        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            out.append(relation + "\n");
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/project", out.toString(), "UTF-8");

    }

    public static void writeSketchView(Script script, AnActionEvent anActionEvent) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("skinparam linetype ortho\n" +
                "skinparam rectangle {\n" +
                " roundCorner 100\n" +
                "}\n");
        out.append("\'!@Sketch\n");
        out.append("package " + anActionEvent.getProject().getName() + " <<Rectangle>> #8B9BAC{\n");
        Set<String> items = script.getItems();
        for (String item : items) {
            out.append(item + "\n");
        }
        out.append("}\n");
        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            out.append(relation + "\n");
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/sketch", out.toString(), "UTF-8");

    }

    public static void writeDependencyView(Script script, AnActionEvent anActionEvent) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("top to bottom direction \n" +
                "GREY_ARROW\n");
        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            out.append(relation);
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/quality", out.toString(), "UTF-8");

    }

    private static void saveToFile(AnActionEvent anActionEvent, String fileExt, String source, String content, String encoding) {
        String targetPath = source + "/" + anActionEvent.getProject().getName() + "." + fileExt;
        ApplicationManager.getApplication().runWriteAction(
                new CreateFileAction(targetPath,
                        content,
                        encoding,
                        anActionEvent.getDataContext()));

    }


}

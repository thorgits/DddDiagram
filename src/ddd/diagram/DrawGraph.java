package ddd.diagram;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import ddd.diagram.script.Script;
import ddd.diagram.until.GenerateThread;
import ddd.template.tool.CreateFileAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class DrawGraph {
    public static void writeProjectView(Script script, AnActionEvent anActionEvent,boolean onlyScript) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("\'!@Item\n");
        Set<String> items = script.getItems();
        for (String item : items) {
            out.append(item + "\n");
        }

        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            if (out.indexOf(relation)==-1){
                out.append(relation + "\n");
            }
        }
        List<NameSpace> nameSpaces= ElementHub.getContextNameSpace();
        for (NameSpace pack:nameSpaces){
            if (out.indexOf(pack.getaPackage().getQualifiedName())!=-1){
                out.append("namespace " + pack.getaPackage().getQualifiedName()+"<<BoundedContext>>{}\n");
            }
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/project", out.toString(), "UTF-8");
        if (!onlyScript){
            generateImage(out.toString(),anActionEvent,anActionEvent.getProject().getBasePath()+ "/project","project");
        }
    }

    public static void writeSketchView(Script script, AnActionEvent anActionEvent,boolean onlyScript) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("\'!@Sketch\n");
        Set<String> items = script.getItems();
        for (String item : items) {
            out.append(item + "\n");
        }

        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            out.append(relation + "\n");
        }
        List<NameSpace> nameSpaces= ElementHub.getContextNameSpace();
        for (NameSpace pack:nameSpaces){
            if (out.indexOf(pack.getaPackage().getQualifiedName())!=-1){
                out.append("namespace " + pack.getaPackage().getQualifiedName()+"<<BoundedContext>>{}\n");
            }
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/sketch", out.toString(), "UTF-8");
        if (!onlyScript)
        generateImage(out.toString(),anActionEvent,anActionEvent.getProject().getBasePath()+ "/sketch","sketch");

    }

    public static void writeDependencyView(Script script, AnActionEvent anActionEvent) {
        StringBuilder out = new StringBuilder();
        out.append(script.getHeader().getHeader());
        out.append(script.getStyle().getStyleStr());
        out.append("\'!@Relation\n");
        Set<String> relations = script.getRelations();
        for (String relation : relations) {
            out.append(relation);
        }
        out.append(script.getFooter().getFooter());
        saveToFile(anActionEvent, "puml", anActionEvent.getProject().getBasePath() + "/quality", out.toString(), "UTF-8");
        generateImage(out.toString(),anActionEvent,anActionEvent.getProject().getBasePath()+ "/quality","dependency");

    }

    private static void saveToFile(AnActionEvent anActionEvent, String fileExt, String source, String content, String encoding) {
        String targetPath = source + "/" + anActionEvent.getProject().getName() + "." + fileExt;

        ApplicationManager.getApplication().runWriteAction(
                new CreateFileAction(targetPath,
                        content,
                        encoding,
                        anActionEvent.getDataContext()));

    }
    private static void generateImage(String content, AnActionEvent anActionEvent, String source,String view) {
        ProgressManager
                .getInstance().run(new Task.Backgroundable(anActionEvent.getProject(), "DDL Generation", false) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                // Generate DDLs
                GenerateThread generateThread=  new GenerateThread(content,anActionEvent,source,view);
                generateThread.run();
                generateThread.runXMI();
                // refresh
                VirtualFileManager.getInstance().asyncRefresh(null);
            }
        });

    }

}

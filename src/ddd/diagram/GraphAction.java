package ddd.diagram;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import ddd.diagram.script.*;

import java.util.HashSet;
import java.util.Set;


public class GraphAction extends AnAction {
    Project project;

    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getProject();
        if (project == null) {
            return;
        }
        PsiElement psiElement = e.getData(LangDataKeys.PSI_ELEMENT);
        if (!(psiElement instanceof PsiDirectory)) {
            Messages.showMessageDialog(project, "Please focus on a package", "Generate Failed", AllIcons.Toolwindows.ErrorEvents);
            return;
        } else {
            scanPackage(psiElement);
        }
        Set<MetaInfo> metaInfoSet = ElementHub.getElementMap().get("AggregateRoot");
        Script script = new Script();
        for (MetaInfo m : metaInfoSet) {
            DependencyTree.createInvoke(m, new HashSet<>(), new HashSet<>());
            script.getRelations().addAll(DependencyTree.getScript().getRelations());
        }
        script = GenerateScript.setSkin(script);
        DrawGraph.writeDependencyView(script, e);
        DrawGraph.writeProjectView(GenerateScript.projectView(), e);
        DrawGraph.writeSketchView(GenerateScript.sketchView(), e);
    }

    public void scanPackage(PsiElement psiElement) {
        if (psiElement instanceof PsiJavaFile) {
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiElement;
            createMeta(psiJavaFile);
        } else if (psiElement instanceof PsiDirectory){
            PsiDirectory psiDirectory = (PsiDirectory) psiElement;
            PsiElement[] psiElements = psiDirectory.getChildren();
            for (int i = 0; i < psiElements.length; i++) {
                scanPackage(psiElements[i]);
            }
        }else {
            Messages.showMessageDialog(project, "Please focus on a package", "Generate Failed", AllIcons.Toolwindows.ErrorEvents);
            return;
        }
    }

    public void createMeta(PsiJavaFile psiJavaFile) {
        // 获取包名
        String packageName = psiJavaFile.getPackageName();
        //获取类名 psiJavaFile 转化成类
        PsiClass[] classes = psiJavaFile.getClasses();
        // 获取所有属性
        PsiField[] psiFields = classes[0].getAllFields();
        // 获取注解
        PsiAnnotation[] psiAnnotations = classes[0].getAnnotations();
        // 获取方法
        PsiMethod[] psiMethods = classes[0].getMethods();
        MetaInfo metaInfo = MetaFactory.createMeta(project, classes[0], packageName, psiFields, psiAnnotations, psiMethods);
        for (int i = 0; i < psiAnnotations.length; i++) {
            if (MetaFactory.includeInDDD(psiAnnotations[i])) {
                ElementHub.put(metaInfo, MetaFactory.getKey(psiAnnotations[i]));
            }
        }

    }
}

package ddd.diagram;

import com.intellij.icons.AllIcons;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.lang.annotation.Annotation;
import java.util.List;

public class AnalyseProject {
    static Project project;
    public static void diagramAnalyse(AnActionEvent e) {
        project = e.getProject();
        ElementHub.removeAll();
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
    }

    public static void validateAnalyse(AnActionEvent e) {
        ElementHub.removeAll();
        project = e.getProject();
        PsiManager manager = PsiManager.getInstance(project);
        VirtualFile[] directory = e.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY).getChildren();
        for (int i = 0; i < directory.length; i++) {
            if (directory[i].getName().contains("src")){
                PsiDirectory psiDirectory = manager.findDirectory(directory[i]);
                scanPackage(psiDirectory);
                break;
            }
        }
//        final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(psiDirectory)
    }

    public static void scanPackage(PsiElement psiElement) {
        if (psiElement instanceof PsiJavaFile) {
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiElement;
            createMeta(psiJavaFile);
        } else if (psiElement instanceof PsiDirectory) {
            PsiDirectory psiDirectory = (PsiDirectory) psiElement;
            PsiElement[] psiElements = psiDirectory.getChildren();
            for (int i = 0; i < psiElements.length; i++) {
                scanPackage(psiElements[i]);
            }
        } else {
            Messages.showMessageDialog(project, "Please focus on a package", "Generate Failed", AllIcons.Toolwindows.ErrorEvents);
            return;
        }
    }


    public static void createMeta(PsiJavaFile psiJavaFile) {
        if (psiJavaFile.getName().contains("package-info")){
            PsiPackage pkg = JavaPsiFacade.getInstance(project).findPackage(psiJavaFile.getPackageName());
            String boundedContext = PsiShortNamesCache.getInstance(project).getClassesByName("BoundedContext", GlobalSearchScope.allScope(project))[0].getQualifiedName();
            PsiAnnotation psiAnnotation=pkg.getAnnotation(boundedContext);
            String context=psiAnnotation.findAttributeValue("context").getText();
            ElementHub.getContextNameSpace().add(new NameSpace(pkg,context));

        }else {
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
            MetaInfo metaInfo=null;
            for (int i = 0; i < psiAnnotations.length; i++) {
                if (MetaFactory.includeInDDD(psiAnnotations[i])) {
                    metaInfo = MetaFactory.createMeta(project, classes[0], packageName, psiFields, psiAnnotations, psiMethods);
                    break;
                }
            }
            if (metaInfo!=null){
                for (int i = 0; i < psiAnnotations.length; i++) {
                    if (MetaFactory.includeInDDD(psiAnnotations[i])) {
                        ElementHub.put(metaInfo, MetaFactory.getKey(psiAnnotations[i]));
                    }
                }
            }
        }

    }
}

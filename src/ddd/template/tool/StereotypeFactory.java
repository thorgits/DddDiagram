package ddd.template.tool;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.PsiDirectoryImpl;

public class StereotypeFactory {
    public static void createStereotype(String annotation, AnActionEvent anActionEvent,ClassEntry classEntry) {
        Project project = anActionEvent.getProject();
        if (project == null) {
            return;
        }
        PsiElement psiElement = anActionEvent.getData(LangDataKeys.PSI_ELEMENT);
        if (!(psiElement instanceof PsiDirectoryImpl)) {
            Messages.showMessageDialog(project, "Please focus on a package", "Generate Failed", null);
            return;
        }
        PsiDirectoryImpl psiDirectory = (PsiDirectoryImpl) psiElement;

        String sourcePath = psiDirectory.getVirtualFile().getPath();
        String packageName;
        if (!sourcePath.endsWith("src")) {
            String packagePath = sourcePath.substring(sourcePath.indexOf("src/") + 4);
            packageName = packagePath.replaceAll("/", ".");
        } else {
            packageName = "";
        }
        DumbService dumbService = DumbService.getInstance(project);
        if (dumbService.isDumb()) {
            dumbService.showDumbModeNotification("DDDTool plugin is not available during indexing");
            return;
        }
        String clazz = Messages.showInputDialog(
                project,
                "New a "+annotation,
                "Input "+annotation+ " name", null);
        if (clazz == null || clazz.isEmpty()) {
            return;
        }
        classEntry.setPackageName(packageName);
        classEntry.setClassName(clazz);
        classEntry.setAnnotation(annotation);
        String content=classEntry.getInstance();
        try {
            saveToFile(anActionEvent, clazz, sourcePath, content, "UTF-8");
        } catch (Exception e) {
            Messages.showMessageDialog(project, e.getMessage(), "Generate Failed", null);
        }
    }


    private static void saveToFile(AnActionEvent anActionEvent, String className, String source, String content, String encoding) {
        final String targetPath = source + "/" + className + ".java";
        VirtualFileManager manager = VirtualFileManager.getInstance();
        VirtualFile virtualFile = manager
                .refreshAndFindFileByUrl(VfsUtil.pathToUrl(targetPath));

        if (virtualFile == null || !virtualFile.exists()) {
            ApplicationManager.getApplication().runWriteAction(
                    new CreateFileAction(targetPath,
                            content,
                            encoding,
                            anActionEvent.getDataContext()));
        }

    }
}

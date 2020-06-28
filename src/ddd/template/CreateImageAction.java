package ddd.template;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.apache.commons.io.FileUtils;

import java.io.File;


public class CreateImageAction implements Runnable {


    private String outputFile;

    private byte[] data;

    private DataContext dataContext;

    public CreateImageAction(String outputFile, byte[] data, DataContext dataContext) {
        this.outputFile = outputFile;
        this.data = data;
        this.dataContext = dataContext;
    }

    @Override
    public void run() {
        try {
            VirtualFileManager manager = VirtualFileManager.getInstance();
            VirtualFile virtualFile = manager.refreshAndFindFileByUrl(VfsUtil.pathToUrl(outputFile));
            File file = new File(outputFile);
            FileUtils.writeByteArrayToFile(file,data);
            virtualFile = manager.refreshAndFindFileByUrl(VfsUtil.pathToUrl(outputFile));
            VirtualFile finalVirtualFile = virtualFile;
            Project project = DataKeys.PROJECT.getData(dataContext);

            ApplicationManager.getApplication()
                    .invokeLater(
                            () -> FileEditorManager.getInstance(project).openFile(finalVirtualFile, true,
                                    true));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

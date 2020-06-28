package ddd.template.tool;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.UnsupportedCharsetException;


public class CreateFileAction implements Runnable {


    private String outputFile;

    private String content;

    private String fileEncoding;

    private DataContext dataContext;

    public CreateFileAction(String outputFile, String content, String fileEncoding, DataContext dataContext) {
        this.outputFile = outputFile;
        this.content = content;
        this.fileEncoding = fileEncoding;
        this.dataContext = dataContext;
    }

    @Override
    public void run() {
        try {
            VirtualFileManager manager = VirtualFileManager.getInstance();
            VirtualFile virtualFile = manager.refreshAndFindFileByUrl(VfsUtil.pathToUrl(outputFile));
            File file = new File(outputFile);
            FileUtils.writeStringToFile(file, content, fileEncoding);
            virtualFile = manager.refreshAndFindFileByUrl(VfsUtil.pathToUrl(outputFile));
            VirtualFile finalVirtualFile = virtualFile;
            Project project = DataKeys.PROJECT.getData(dataContext);

            ApplicationManager.getApplication()
                    .invokeLater(
                            () -> FileEditorManager.getInstance(project).openFile(finalVirtualFile, true,
                                    true));

        } catch (UnsupportedCharsetException ex) {
            ApplicationManager.getApplication().invokeLater(() -> Messages.showMessageDialog("Unknown Charset: " + fileEncoding + ", please use the correct charset", "Generate Failed", null));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

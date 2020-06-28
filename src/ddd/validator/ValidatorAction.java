package ddd.validator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import ddd.diagram.AnalyseProject;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ValidatorAction extends AnAction {
    private JTextArea jTextArea;

    /**
     * @param e 事件
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        AnalyseProject.validateAnalyse(e);
        if (project != null) {
            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("DDD validator");
            if (toolWindow != null) {
                // 无论当前状态为关闭/打开，进行强制打开ToolWindow 2017/3/21 16:21
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                // ToolWindow未初始化时，可能为空 2017/4/4 18:20
                try {
                    jTextArea = (JTextArea) ((JScrollPane) toolWindow.getContentManager().getContent(0)
                            .getComponent().getComponent(0)).getViewport().getComponent(0);
                    if (jTextArea != null) {
                        jTextArea.append(project.getProjectFilePath() + "\n");
                        getErrorInfo(e);
                    }
                } catch (Exception e1) {
                     e1.printStackTrace();
                }
            }
        }

    }


    private void getErrorInfo(AnActionEvent anActionEvent) {
        try {
            List<ConstraintException> constraintExceptions = VerifyConstraint.verify(anActionEvent);
            for (ConstraintException e : constraintExceptions) {
                String data= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                 data+=String.format("[ %6d ] Error - %-35s - %-50s \n",e.getConstraint().getCode(),e.getMessage(),e.getConstraint().getRule());
//                System.out.println(data);
                 jTextArea.append(data);
//                jTextArea.append("Error code [" + e.getConstraint().getCode() + "] - " + e.getMessage() + "- violate" + e.getConstraint().getRule() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


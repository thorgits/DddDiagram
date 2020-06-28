package ddd.diagram.until;

import com.intellij.openapi.actionSystem.AnActionEvent;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.Run;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.classdiagram.ClassDiagram;
import net.sourceforge.plantuml.core.ImageData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GenerateThread  {
    private String content;
    private   AnActionEvent anActionEvent;
    private String source;
    private  String view;

    public GenerateThread(String content, AnActionEvent anActionEvent, String source, String view) {
        this.content = content;
        this.anActionEvent = anActionEvent;
        this.source = source;
        this.view = view;
    }



    private  void saveToImage( byte[] bytes) {
        String targetPath = source + "/" + anActionEvent.getProject().getName()+"_"+view + "." + "svg";
//        ApplicationManager.getApplication().runWriteAction(
//////                new CreateImageAction(targetPath,
//////                        bytes,
//////                        anActionEvent.getDataContext()));
        FileUtil.writeToFile(bytes,targetPath);
    }
    private  void saveToImageXMI( byte[] bytes) {
        String targetPath = source + "/" + anActionEvent.getProject().getName()+"_"+view + "." + "xmi";
        FileUtil.writeToFile(bytes,targetPath);
    }

    public void run() {
        SourceStringReader reader = new SourceStringReader(content);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes=new byte[1024*1024];
        try {
            reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
            bytes= os.toByteArray();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bytes.length!=0){
            saveToImage(bytes);
        }
    }
    public void runXMI() {
       String arg[]=new String[]{source + "/" + anActionEvent.getProject().getName()+".puml","-xmi:argo"," -duration","-o",source};
        try {
            Run.main(arg);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

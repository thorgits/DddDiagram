package ddd.diagram.until;

import java.io.Closeable;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;


public class FileUtil {


    public static void writeToFile(byte[] content, String outFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFileName);
            fos.write(content);
            fos.flush();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Generate Failed",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            safeClose(fos);
        }
    }

    public static void safeClose(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}

package ddd.diagram.script;


import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import ddd.diagram.MetaFactory;
import ddd.diagram.MetaInfo;
import ddd.diagram.util.ModifierUtil;


/**
 * @author liwenjun
 * @ClassName Item
 * @Date 2019-12-17 17:28
 */
public class Item {
    String name;
    PsiField[] fields;
    PsiMethod[] methods;
    PsiAnnotation[] annotations;
    StringBuilder itemStr;

    public String createItem(MetaInfo meta) {
        itemStr = new StringBuilder();
        if (meta.getClazz().isInterface()) {
            itemStr.append(" interface ");
        } else {
            itemStr.append(" class ");
        }
        name = meta.getClazz().getQualifiedName();
        itemStr.append(name);
        annotations = meta.getAnnotations();
        itemStr.append("<<");
        for (int i = 0; i < annotations.length - 1; i++) {
            itemStr.append(MetaFactory.getKey(annotations[i]) + ", ");
        }
        itemStr.append(MetaFactory.getKey(annotations[annotations.length - 1]));
        itemStr.append(">> { \n");
        fields = meta.getPsiFields();
        for (PsiField f : fields) {
            String fs = f.getText();
            fs = ModifierUtil.getModifier(fs);
            if (fs.contains(";")) {
                fs.replace(";", "");
            }
            itemStr.append(fs + "\n");
        }
        methods = meta.getPsiMethods();
        for (PsiMethod m : methods) {
            String ms = m.getText();
            String s = "";
            ms = ModifierUtil.getModifier(ms);
            if (ms.indexOf("{") != -1 && ms.indexOf("}") != -1) {
                s = ms.substring(ms.indexOf("{"), ms.indexOf("}") + 1);
                ms = ms.replace(s, "");
            }
            if (ms.contains(";")) {
                ms = ms.replace(";", "");
            }
            itemStr.append(ms + "\n");
        }
        itemStr.append("}");
        return itemStr.toString();

    }

    public String createItemSketch(MetaInfo meta) {
        itemStr = new StringBuilder();
        itemStr.append("class ");
        name = meta.getClazz().getName();
        itemStr.append(name);
        itemStr.append(ModifierUtil.getStereotype(MetaFactory.getKey(meta.getAnnotations()[0])));
        itemStr.append("\n");
        itemStr.append("hide " + name + " members\n");
        return itemStr.toString();

    }


}

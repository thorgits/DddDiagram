package ddd.diagram.script;


import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import ddd.diagram.MetaFactory;
import ddd.diagram.MetaInfo;
import ddd.diagram.until.ModifierUtil;


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
            String mox=f.getModifierList().getText();
            String mo=ModifierUtil.getModifierField(mox);
             if (mox.contains("@DefinesIdentity")){
                mo+="<<DefinesIdentity>>";
            }
            String fs = "";
            fs=mo+f.getName()+":"+f.getType().getPresentableText();
            itemStr.append(fs + "\n");
        }
        methods = meta.getPsiMethods();
        for (PsiMethod m : methods) {
           String sx= m.getModifierList().getText();
           String s = ModifierUtil.getModifierField(sx);
            if (sx.contains("@DefinesIdentity")){
                s+="<<DefinesIdentity>>";
            }else if (sx.contains("@ValidatesSpec")){
                s+="<<ValidatesSpec>>";
            }else {

            }
            String ms ="";
            if (m.getReturnType()!=null){
                ms=s+" "+m.getName()+m.getParameterList().getText()+":"+m.getReturnType().getPresentableText();
                itemStr.append(ms + "\n");
            }
        }
        itemStr.append("}"+"\n");
        String tmp="";
        String  note="";
        for (int i = 0; i < annotations.length - 1; i++) {
            tmp=annotations[i].getText();
            if (tmp.contains("root")){
                note=tmp.substring(tmp.indexOf("(")+1,tmp.indexOf(")"));
                break;
            }
        }
        if (!note.isEmpty()){
            itemStr.append("note right:"+note+"\n");
        }
        return itemStr.toString();
    }

    public String createItemSketch(MetaInfo meta) {
        itemStr = new StringBuilder();
        itemStr.append("class ");
        name = meta.getClazz().getQualifiedName();
        itemStr.append(name);
        itemStr.append(ModifierUtil.getStereotype(MetaFactory.getKey(meta.getAnnotations()[0])));
        itemStr.append("{\n");
        fields = meta.getPsiFields();
        for (PsiField f : fields) {
            String mox=f.getModifierList().getText();
            String mo=ModifierUtil.getModifierField(mox);
            if (mox.contains("@DefinesIdentity")){
                mo+="<<DefinesIdentity>>";
            }
            String fs = "";
            fs=mo+f.getName()+":"+f.getType().getPresentableText();
            itemStr.append(fs + "\n");
        }
        itemStr.append("}\n");
        itemStr.append("hide " + name + " methods\n");
        return itemStr.toString();

    }


}

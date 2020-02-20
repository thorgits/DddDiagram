package ddd.diagram.script;


import com.intellij.psi.PsiClass;
import ddd.diagram.MetaInfo;

import java.util.Set;

/**
 * @author liwenjun
 * @ClassName Relation
 * @Date 2019-12-17 17:35
 */
public class Relation {
    StringBuilder relationStr;

    public String createRelation(MetaInfo meta) {
        relationStr = new StringBuilder();
        Set<PsiClass> psiClasses = meta.getAssociationClazz();
        for (PsiClass m : psiClasses) {
            StringBuilder tmpStr = new StringBuilder();
            tmpStr.append(meta.getClazz().getQualifiedName() + " --> " + m.getQualifiedName() + '\n');
            if (relationStr.indexOf(tmpStr.toString()) == -1) {
                relationStr.append(tmpStr);
            }
        }
        return relationStr.toString();
    }

    public String createSketchRelation(MetaInfo meta) {
        relationStr = new StringBuilder();
        Set<PsiClass> psiClasses = meta.getAssociationClazz();
        for (PsiClass m : psiClasses) {
            StringBuilder tmpStr = new StringBuilder();
            tmpStr.append(meta.getClazz().getName() + " --> " + m.getName() + '\n');
            if (relationStr.indexOf(tmpStr.toString()) == -1) {
                relationStr.append(tmpStr);
            }
        }
        return relationStr.toString();
    }
}

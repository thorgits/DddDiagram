package ddd.diagram.script;


import com.intellij.psi.PsiClass;
import ddd.diagram.MetaInfo;
import ddd.diagram.until.RelationClazz;

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
        Set<RelationClazz> psiClasses = meta.getAssociationClazz();
        for (RelationClazz m : psiClasses) {
            StringBuilder tmpStr = new StringBuilder();
            if (m.getType()==0){
                if (m.isOdd()){
                    tmpStr.append(meta.getClazz().getQualifiedName() + " --> " +"\"0..n\""+ m.getPsiClass().getQualifiedName() + '\n');
                }else {
                    tmpStr.append(meta.getClazz().getQualifiedName() + " --> "+"\"0..1\""+ m.getPsiClass().getQualifiedName() + '\n');
                }

            }else {
                if (m.isOdd()){
                    tmpStr.append(meta.getClazz().getQualifiedName() + " ..> " +"\"0..n\""+ m.getPsiClass().getQualifiedName() + '\n');
                }else {
                    tmpStr.append(meta.getClazz().getQualifiedName() + " ..> "+ "\"0..1\""+m.getPsiClass().getQualifiedName() + '\n');
                }

            }

            if (relationStr.indexOf(tmpStr.toString()) == -1) {
                relationStr.append(tmpStr);
            }
        }
        return relationStr.toString();
    }

    public String createSketchRelation(MetaInfo meta) {
        relationStr = new StringBuilder();
        Set<RelationClazz> psiClasses = meta.getAssociationClazz();
        for (RelationClazz m : psiClasses) {
            StringBuilder tmpStr = new StringBuilder();
            tmpStr.append(meta.getClazz().getQualifiedName() + " --> " + m.getPsiClass().getQualifiedName() + '\n');
            if (relationStr.indexOf(tmpStr.toString()) == -1) {
                relationStr.append(tmpStr);
            }
        }
        return relationStr.toString();
    }
}

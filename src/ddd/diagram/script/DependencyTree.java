package ddd.diagram.script;


import com.intellij.psi.PsiClass;
import ddd.diagram.MetaInfo;
import ddd.diagram.util.MetaClazzUtil;

import java.util.*;

/**
 * @author liwenjun
 * @ClassName Sequence
 * @Date 2019-12-19 20:44
 */
public class DependencyTree {
    static Script script = new Script();

    public static void createInvoke(MetaInfo metaInfo, Set<PsiClass> setMark, Set<PsiClass> setBack) {
        Set<PsiClass> psiClassSet = metaInfo.getAssociationClazz();
        if (psiClassSet.size() == 0) {
            return;
        }
        setMark.add(metaInfo.getClazz());
        setBack.add(metaInfo.getClazz());
        for (PsiClass cl : psiClassSet) {
            if (setMark.contains(cl)) {
                script.getRelations().add("(" + metaInfo.getClazz().getName() + ") .[#red].> (" + cl.getName() + "):circular\n");
            } else if (setBack.contains(cl)) {
                script.getRelations().add("(" + metaInfo.getClazz().getName() + ") -->(" + cl.getName() + ")\n");
            } else {
                script.getRelations().add("(" + metaInfo.getClazz().getName() + ") --> (" + cl.getName() + ")\n");
                createInvoke(MetaClazzUtil.getMetaInfoFromClazzUtil(cl), setMark, setBack);
                setMark.remove(cl);
            }
        }
    }

    public static Script getScript() {
        return script;
    }
}

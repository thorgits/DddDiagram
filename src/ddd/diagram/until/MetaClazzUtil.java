package ddd.diagram.until;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import ddd.diagram.ElementHub;
import ddd.diagram.MetaFactory;
import ddd.diagram.MetaInfo;


import java.util.Set;
import java.util.stream.Collectors;

public class MetaClazzUtil {
    public  static MetaInfo getMetaInfoFromClazzUtil(PsiClass psiClass){
        PsiAnnotation[] psiAnnotations= psiClass.getAnnotations();
        String key="";
        MetaInfo meta = null;
        for (int i = 0; i <psiAnnotations.length ; i++) {
            if (MetaFactory.includeInDDD(psiAnnotations[i])){
                 key=MetaFactory.getKey(psiAnnotations[i]);
            }
        }
        Set<MetaInfo> map= ElementHub.getElementMap().get(key);
        Set<MetaInfo>metaInfo =map.stream().filter((k)->k.getClazz().getName().equals(psiClass.getName())).collect(Collectors.toSet());
        for (MetaInfo m:metaInfo){
            meta=m;
        }
        return meta;

    }
}

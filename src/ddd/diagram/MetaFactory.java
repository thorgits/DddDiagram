package ddd.diagram;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.*;

public class MetaFactory {
    public static PsiClass getPsiClassFromName(PsiType psiType, Project project) {
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        String typeStr = psiType.getDeepComponentType().getCanonicalText();
        PsiClass psiClazz = null;
        if (typeStr.contains("<")) {
            typeStr = typeStr.substring(typeStr.indexOf("<") + 1, typeStr.indexOf(">"));
        }
        psiClazz = javaPsiFacade.findClass(typeStr, GlobalSearchScope.allScope(project));
        return psiClazz;
    }

    public static boolean includeInDDD(PsiAnnotation psiAnnotation) {
        boolean isInclude = false;
        if (Arrays.asList(ElementHub.getKeys()).contains(getKey(psiAnnotation))) {
            isInclude = true;
        }
        return isInclude;
    }

    public static String getKey(PsiAnnotation psiAnnotation) {
        String key = psiAnnotation.getText();
        if (key.contains("(")) {
            key = key.substring(key.indexOf("@") + 1, key.indexOf("("));
        } else {
            key = key.replace("@", "");
        }
        return key;
    }

    public static MetaInfo createMeta(Project project, PsiClass aClass, String packageName,
                                      PsiField[] psiFields, PsiAnnotation[] psiAnnotations, PsiMethod[] psiMethods) {
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setClazz(aClass);
        metaInfo.setPackageName(packageName);
        metaInfo.setPsiFields(psiFields);
        metaInfo.setPsiMethods(psiMethods);
        Set<PsiClass> associationClazz = new HashSet();
        for (int j = 0; j < psiFields.length; j++) {
            PsiClass psiClass = getPsiClassFromName(psiFields[j].getType(), project);
            if (psiClass != null) associationClazz.add(psiClass);
        }
        for (int i = 0; i < psiMethods.length; i++) {
            PsiClass psiClass = getPsiClassFromName(psiMethods[i].getReturnType(), project);
            if (psiClass != null) associationClazz.add(psiClass);
            PsiParameter[] psiParameters = psiMethods[i].getParameterList().getParameters();
            for (int j = 0; j < psiParameters.length; j++) {
                PsiClass psiClassParameter = getPsiClassFromName(psiParameters[j].getType(), project);
                if (psiClassParameter != null) associationClazz.add(psiClassParameter);
            }
        }
        Iterator<PsiClass> iterator = associationClazz.iterator();
        while (iterator.hasNext()) {
            PsiClass p = iterator.next();
            PsiAnnotation[] psiAs = p.getAnnotations();
            Boolean isIn = false;
            for (int i = 0; i < psiAs.length; i++) {
                if (includeInDDD(psiAs[i])) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                iterator.remove();
            }
        }
        metaInfo.setAssociationClazz(associationClazz);
        List<PsiAnnotation> psiAn = new ArrayList<>();
        for (int i = 0; i < psiAnnotations.length; i++) {
            if (includeInDDD(psiAnnotations[i])) {
                psiAn.add(psiAnnotations[i]);
            }

        }
        metaInfo.setAnnotations(psiAn.toArray(PsiAnnotation[]::new));
        return metaInfo;
    }
}

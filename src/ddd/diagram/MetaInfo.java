package ddd.diagram;

import com.intellij.psi.*;
import ddd.diagram.until.RelationClazz;

import java.util.Set;

public class MetaInfo {
    private PsiClass clazz;
    private String packageName;
    private Set<RelationClazz> associationClazz;
    private PsiAnnotation[] annotations;
    private PsiMethod[] psiMethods;
    private PsiField[] psiFields;
    private  Integer annotationCode;

    public Integer getAnnotationCode() {
        return annotationCode;
    }

    public void setAnnotationCode(Integer annotationCode) {
        this.annotationCode = annotationCode;
    }

    public PsiField[] getPsiFields() {
        return psiFields;
    }

    public void setPsiFields(PsiField[] psiFields) {
        this.psiFields = psiFields;
    }

    public PsiClass getClazz() {
        return clazz;
    }

    public void setClazz(PsiClass clazz) {
        this.clazz = clazz;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Set<RelationClazz> getAssociationClazz() {
        return associationClazz;
    }

    public void setAssociationClazz(Set<RelationClazz> associationClazz) {
        this.associationClazz = associationClazz;
    }

    public PsiAnnotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(PsiAnnotation[] annotations) {
        this.annotations = annotations;
    }

    public PsiMethod[] getPsiMethods() {
        return psiMethods;
    }

    public void setPsiMethods(PsiMethod[] psiMethods) {
        this.psiMethods = psiMethods;
    }
}

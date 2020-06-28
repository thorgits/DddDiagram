package ddd.diagram.until;

import com.intellij.psi.PsiClass;

import java.util.Objects;

public class RelationClazz {
    private PsiClass psiClass;
    private boolean odd;
    private int type;

    public RelationClazz(PsiClass psiClass, boolean odd, int type) {
        this.psiClass = psiClass;
        this.odd = odd;
        this.type = type;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public boolean isOdd() {
        return odd;
    }

    public void setOdd(boolean odd) {
        this.odd = odd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationClazz that = (RelationClazz) o;
        return Objects.equals(psiClass.getQualifiedName(), that.psiClass.getQualifiedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(psiClass.getQualifiedName());
    }
}

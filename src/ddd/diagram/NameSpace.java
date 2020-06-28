package ddd.diagram;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiPackage;

public class NameSpace {
    private PsiPackage aPackage;
    private String context;

    public NameSpace(PsiPackage aPackage, String context) {
        this.aPackage = aPackage;
        this.context = context;
    }

    public PsiPackage getaPackage() {
        return aPackage;
    }

    public void setaPackage(PsiPackage aPackage) {
        this.aPackage = aPackage;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

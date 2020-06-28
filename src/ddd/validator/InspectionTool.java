package ddd.validator;

import com.intellij.codeInspection.*;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


public class InspectionTool extends AbstractBaseJavaLocalInspectionTool {

    /**
     * This method is called to get the panel describing the inspection.
     * It is called every time the user selects the inspection in preferences.
     * The user has the option to edit the list of CHECKED_CLASSES.
     * Adds a document listener to see if
     *
     * @return panel to display inspection information.
     */
    @Override
    public JComponent createOptionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        return panel;
    }
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {


            /**
             *  This string defines the short message shown to a user signaling the inspection
             *  found a problem. It reuses a string from the inspections bundle.
             */
            @NonNls
            private final String DESCRIPTION_TEMPLATE = "Violation of DDD constraints [#] - $";

            /**
             * Avoid defining visitors for both Reference and Binary expressions.
             *
             * @param psiReferenceExpression  The expression to be evaluated.
             */
            @Override
            public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
            }

            @Override
            public void visitField(PsiField field) {
                super.visitField(field);
            }

            @Override
            public void visitClass(PsiClass aClass) {
                super.visitClass(aClass);
                PsiClass[] p0= PsiShortNamesCache.getInstance(aClass.getProject()).getClassesByName("AggregateRoot", GlobalSearchScope.allScope(aClass.getProject()));
                PsiClass p=null;
                if (p0!=null){
                    for (int i = 0; i < p0.length; i++) {
                        if (p0[i].isAnnotationType()){
                            p=p0[i];
                            break;
                        }
                    }
                }
                PsiClass[] p1= PsiShortNamesCache.getInstance(aClass.getProject()).getClassesByName("Entity", GlobalSearchScope.allScope(aClass.getProject()));
                PsiClass p2=null;
                if(p1!=null){
                    for (int i = 0; i < p1.length; i++) {
                        if (p1[i].isAnnotationType()){
                            p2=p1[i];
                            break;
                        }
                    }
                }

                if (p!=null && aClass.getAnnotation(p.getQualifiedName())!=null){
                    if (p2!=null &&aClass.getAnnotation(p2.getQualifiedName())==null){
                        String ErrorMessage=DESCRIPTION_TEMPLATE.replace("#",(Constraint.CONSTRAINT_03.getCode()+""));
                        ErrorMessage=ErrorMessage.replace("$",Constraint.CONSTRAINT_03.getRule());
                        holder.registerProblem(aClass.getAnnotation(p.getQualifiedName()), ErrorMessage);
                    }
                }
            }

        };
    }
}

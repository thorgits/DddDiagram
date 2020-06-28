package ddd.validator;

import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import ddd.diagram.ElementHub;
import ddd.diagram.MetaFactory;
import ddd.diagram.MetaInfo;

import java.util.*;

public class VerifyConstraint {
    static String AggregateRoot, Entity, ValueObject, DomainEvent, Specification, Repository, AggregatePart, DomainService, DefinesIdentity, ValidatesSpec,Factory;

    public static List<ConstraintException> verify(AnActionEvent e) {
        AggregateRoot = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("AggregateRoot", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        Entity = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("Entity", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        ValueObject = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("ValueObject", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        DomainEvent = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("DomainEvent", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        Specification = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("Specification", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        Repository = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("Repository", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        AggregatePart = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("AggregatePart", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        DomainService = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("DomainService", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        DefinesIdentity = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("DefinesIdentity", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        ValidatesSpec = PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("ValidatesSpec", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        Factory=PsiShortNamesCache.getInstance(e.getProject()).getClassesByName("Factory", GlobalSearchScope.projectScope(e.getProject()))[0].getQualifiedName();
        List<ConstraintException> constraintExceptions = new LinkedList<>();
        Map<String, Set<MetaInfo>> map = ElementHub.getElementMap();
        Set<String> keySet = map.keySet();
        for (String c : keySet) {
            map.get(c).forEach(m -> check(m, constraintExceptions,e));
        }
        return constraintExceptions;
    }


    public static void check(MetaInfo e, List<ConstraintException> constraintExceptions,AnActionEvent an) {
        PsiAnnotation[] annotations = e.getAnnotations();
        try {
            for (PsiAnnotation a : annotations) {
                switch (MetaFactory.getKey(a)) {
                    case "ValueObject":
                        valueObjectCheck(e);
                        break;
                    case "Entity":
                        entityCheck(e);
                        break;
                    case "DomainEvent":
                        eventCheck(e);
                        break;
                    case "Specification":
                        specCheck(e);
                        break;
                    case "Repository":
                        repositoryCheck(e,an);
                        break;
                    case "AggregatePart":
                        aggregatePartCheck(e);
                        break;
                    case "AggregateRoot":
                        aggregateRootCheck(e);
                        break;
                    case "DomainService":
                        serviceCheck(e);
                        break;
                    case "Factory":
                        factoryCheck(e);
                        break;
                    default:
                        break;

                }
                definesIdentityCheck(e);
//                validateSpecCheck(e);
            }
        } catch (ConstraintException constraintE) {
            boolean alreadyExist = false;
            for (ConstraintException c : constraintExceptions) {
                if (constraintE.getConstraint().getCode() == c.getConstraint().getCode()) {
                    alreadyExist = true;
                    break;
                }
            }
            if (!alreadyExist) {
                constraintExceptions.add(constraintE);
            }

        }

    }

    public static void aggregatePartCheck(MetaInfo e) throws ConstraintException {

        if ((e.getAnnotationCode() & MetaEnum.getvalueOf("Entity").getCode()) != 0 ||
                (e.getAnnotationCode() & MetaEnum.getvalueOf("ValueObject").getCode()) != 0) {
        } else {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_04);
        }
        List<JvmAnnotationAttribute> attributes = e.getClazz().getAnnotation(AggregatePart).getAttributes();
        boolean hasRoot = false;
        for (JvmAnnotationAttribute j : attributes) {
            if (j.getAttributeName().equals("root") && j.getAttributeValue() != null) ;
            hasRoot = true;
            break;
        }
        if (!hasRoot) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_05);
        }
    }

    public static void aggregateRootCheck(MetaInfo e) throws ConstraintException {
        if ((e.getAnnotationCode() & MetaEnum.ENTITY.getCode()) != 0) {
        } else {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_03);
        }
    }

    public static void entityCheck(MetaInfo e) throws ConstraintException {
        PsiMethod[] methods = e.getPsiMethods();
        PsiField[] fields = e.getPsiFields();
        boolean check = false;
        for (PsiMethod m : methods) {
            if (m.getAnnotation(DefinesIdentity) != null) {
                check = true;
            }
        }
        for (PsiField f : fields) {
            if (f.getAnnotation(DefinesIdentity) != null) {
                check = true;
            }
        }
        if (!check) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_01);
        }

    }

    public static void valueObjectCheck(MetaInfo e) {

    }

    public static void eventCheck(MetaInfo e) {

    }

    public static void repositoryCheck(MetaInfo e,AnActionEvent an) throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.REPOSITORY.getCode())) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_11);
        }
        if (e.getClazz().getMethods().length >= 1 && e.getClazz().getFields().length <= 0) {
        } else {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_09);
        }
        PsiMethod[] pms=e.getClazz().getMethods();
        for (int i = 0; i <pms.length ; i++) {
            boolean hasR=false;
            String c="";
            if (pms[i].getReturnType().getPresentableText().contains("<") &&pms[i].getReturnType().getPresentableText().contains(">") ){
                c=pms[i].getReturnType().getPresentableText().substring(pms[i].getReturnType().getPresentableText().indexOf("<")+1,pms[i].getReturnType().getPresentableText().indexOf(">"));
            }else {
                c=pms[i].getReturnType().getDeepComponentType().getPresentableText();
            }
            PsiClass[] ps=PsiShortNamesCache.getInstance(an.getProject()).getClassesByName(c, GlobalSearchScope.projectScope(an.getProject()));
            if (ps.length!=0){
                List<PsiAnnotation> pay=Arrays.asList(ps[0].getAnnotations());
                for (int j = 0; j < pay.size(); j++) {
                    if (!pay.get(j).getText().contains("AggregateRoot")){
                        hasR=true;
                        break;
                    }
                }
            }

            if (!hasR){
                throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_10);
            }
        }


    }

    public static void factoryCheck(MetaInfo e)throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.FACTORY.getCode())) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_12);
        }
    }

    public static void serviceCheck(MetaInfo e) throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.DOMAINSERVICE.getCode())) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_07);
        }
        if (e.getClazz().getMethods().length >= 1 && e.getClazz().getFields().length <= 0) {
        } else {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_06);
        }

    }

    public static void specCheck(MetaInfo e) throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.SPECIFICATION.getCode())) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_16);
        }
        PsiMethod[] methods = e.getPsiMethods();
        boolean check = false;
        for (PsiMethod m : methods) {
            if (m.getAnnotation(ValidatesSpec) != null) {
                if (m.getParameterList().isEmpty()) {
                    throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_14);
                }
                if (!(m.getReturnType().isValid())){
                    throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_15);
                }
                check = true;
            }
        }
        if (!check) {
            throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_13);
        }

    }


    public static void definesIdentityCheck(MetaInfo e) throws ConstraintException {
        Integer a=e.getAnnotationCode() & MetaEnum.ENTITY.getCode();
        Integer b=e.getAnnotationCode() & MetaEnum.DOMAINEVENT.getCode();
      if (a==0 && b==0){
          PsiMethod[] methods = e.getPsiMethods();
          PsiField[] fields = e.getPsiFields();
          boolean check = false;
          for (PsiMethod m : methods) {
              if (m.getAnnotation(DefinesIdentity) != null) {
                  check = true;
              }
          }
          for (PsiField f : fields) {
              if (f.getAnnotation(DefinesIdentity) != null) {
                  check = true;
              }
          }
          if (check) {
              throw new ConstraintException(e.getClazz().getQualifiedName(), Constraint.CONSTRAINT_02);
          }
      }


    }




}

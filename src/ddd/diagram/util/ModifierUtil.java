package ddd.diagram.util;

/**
 * @author liwenjun
 * @ClassName ModifierUtil
 * @Date 2019-12-19 17:51
 */
public class ModifierUtil {
    // -String area /'-表示权限private'/
    //    #int rivers  /'#表示权限protected'/
    //    +long person /'+表示权限public'/
    //    ~String getArea() /'~表示权限package private'/

    public static String getModifier(String modify) {
        if (modify.contains("private")) {
            if (modify.contains("@")) {
                String s = modify.substring(modify.indexOf("@"), modify.indexOf("private"));
                modify = modify.replace(s+"private", "-");
            } else {
                modify = modify.replace("private", "-");
            }
        } else if (modify.contains("protected")) {
            if (modify.contains("@")) {
                String s = modify.substring(modify.indexOf("@"), modify.indexOf("protected"));
                modify = modify.replace(s+"protected", "#");
            } else {
                modify = modify.replace("protected", "#");
            }
        } else if (modify.contains("public")) {
            if (modify.contains("@")) {
                String s = modify.substring(modify.indexOf("@"), modify.indexOf("public"));
                modify = modify.replace(s+"public", "+");
            } else {
                modify = modify.replace("public", "+");
            }
        } else {
            if (modify.contains("@")) {
                String s = modify.substring(modify.indexOf("@"), modify.indexOf("\n")+1);
                modify=modify.replace(s, "~");
            }
        }
        return modify.strip();
    }


    public static String getStereotype(String annotation) {
        switch (annotation) {
            case "AggregateRoot":
                return " <<(R,#5C95D1) AggregateRoot>> #D1A349 {}";
            case "AggregatePart":
                return " <<(P,#7720B0) AggregatePart>> #476EB0 {}";
            case "Entity":
                return " <<(E,#FF7700)Entity>> #8ED4D1 {}";
            case "ValueObject":
                return " <<(V,#148610) ValueObject>> #861F1B {}";
            case "DomainService":
                return " <<(D,#AF1603) DomainService>> #544E15 {}";
            case "Specification":
                return " <<(S,#C49AC3) Specification>> #2FD92A{}";
            case "Repository":
                return " <<(R,#e2b8b8)Repository>> #FFFFFF {}";
            case "Event":
                return " <<(T,#207EC4) Event>> #C45E24 {}";
            default:
                return "{}";
        }
    }
}

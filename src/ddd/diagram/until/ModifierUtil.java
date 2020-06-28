package ddd.diagram.until;

import org.junit.Assert;

import java.lang.reflect.Modifier;

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

    public static String getModifierField(String modify){
        String out="";
        if (modify.contains("private")) {
            out="-";
        }else if (modify.contains("protected")){
            out="#";
        }else if (modify.contains("public")){
            out="+";
        }else {
          out="~";
        }
        return  out;
    }

    public static String getModifier(String modify) {
        if (modify.contains("private")) {
            if (modify.contains("@")) {
                String s="";
                if (modify.indexOf("@")<modify.indexOf("private")){
                    if (modify.contains("@DefinesIdentity")){
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("private"));
                        modify = modify.replace(s+"private", "<<DefinesIdentity>> -");
                    }else {
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("private"));
                        modify = modify.replace(s+"private", "-");
                    }

                }else {
                    modify = modify.replace("private", "-");
                }
            } else {
                modify = modify.replace("private", "-");
            }
        } else if (modify.contains("protected")) {
            if (modify.contains("@")) {
                String s="";
                if (modify.indexOf("@")<modify.indexOf("protected")){
                    if (modify.contains("@DefinesIdentity")){
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("protected"));
                        modify = modify.replace(s+"protected", "<<DefinesIdentity>> #");
                    }else {
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("protected"));
                        modify = modify.replace(s+"protected", "#");
                    }

                }else {
                    modify = modify.replace("protected", "#");
                }

            } else {
                modify = modify.replace("protected", "#");
            }
        } else if (modify.contains("public")) {
            if (modify.contains("@")) {
                String s="";
                if (modify.indexOf("@")<modify.indexOf("public")){
                    if (modify.contains("@DefinesIdentity")){
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("public"));
                        modify = modify.replace(s+"public", "<<DefinesIdentity>> +");
                    }else {
                        s= modify.substring(modify.indexOf("@"), modify.indexOf("public"));
                        modify = modify.replace(s+"public", "+");
                    }
                }else {
                    modify = modify.replace("public", "+");
                }
            } else {
                modify = modify.replace("public", "+");
            }
        } else {
            if (modify.contains("@")) {
                if (modify.indexOf("@")> modify.indexOf("\n")+1){
                    String s = modify.substring(modify.indexOf("@"), modify.indexOf("\n")+1);
                    modify=modify.replace(s, "~");
                }
            }
        }
        return modify.strip();
    }


    public static String getStereotype(String annotation) {
        switch (annotation) {
            case "AggregateRoot":
                return " <<(R,#FF7700) AggregateRoot>>";
            case "AggregatePart":
                return " <<(P,#FF7700) AggregatePart>>";
            case "Entity":
                return " <<(E,#FF7700)Entity>>";
            case "ValueObject":
                return " <<(V,#FF7700) ValueObject>> ";
            case "DomainService":
                return " <<(D,#FF7700) DomainService>> ";
            case "Specification":
                return " <<(S,#FF7700) Specification>>";
            case "Repository":
                return " <<(R,#FF7700)Repository>> ";
            case "DomainEvent":
                return " <<(E,#FF7700) DomainEvent>> ";
            default:
                return "";
        }
    }
}

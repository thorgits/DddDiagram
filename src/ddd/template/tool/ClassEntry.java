package ddd.template.tool;


public class ClassEntry {

    String className;
    String annotation;
    String packageName;
    StringBuilder stringBuilder = new StringBuilder();

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInstance() {
        if (!packageName.isEmpty()) {
            stringBuilder.append("package  " + packageName + ";");
        }
        stringBuilder.append("\n");
        stringBuilder.append("@" + annotation + "\n");
        stringBuilder.append("public class " + className + " {\n");
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

package ddd.template.tool;

public class ValueObjectClass extends  ClassEntry {
    public String root;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public String getInstance() {
        if (!packageName.isEmpty()) {
            stringBuilder.append("package  " + packageName + ";");
        }
        stringBuilder.append("\n");
        stringBuilder.append("@" + annotation + "(root="+root+")\n");
        stringBuilder.append("public class " + className + " {\n");
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

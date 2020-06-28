package ddd.template.tool;

public class AggregateRootClass extends ClassEntry {
    String identifier;
    String root;
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

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
        stringBuilder.append("@" + annotation + "\n");
        stringBuilder.append("@Entity(root="+root+")\n");
        stringBuilder.append("public class " + className + " {\n");
        stringBuilder.append(identifier);
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

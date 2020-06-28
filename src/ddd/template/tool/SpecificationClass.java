package ddd.template.tool;

public class SpecificationClass  extends ClassEntry{
    String validateSpec;

    public String getValidateSpec() {
        return validateSpec;
    }

    public void setValidateSpec(String validateSpec) {
        this.validateSpec = validateSpec;
    }

    @Override
    public String getInstance() {
        if (!packageName.isEmpty()) {
            stringBuilder.append("package  " + packageName + ";");
        }
        stringBuilder.append("\n");
        stringBuilder.append("@" + annotation + "\n");
        stringBuilder.append("public class " + className + " {\n");
        stringBuilder.append(validateSpec);
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

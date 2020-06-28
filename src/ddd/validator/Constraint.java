package ddd.validator;

public enum Constraint {
     //Entity
    CONSTRAINT_01(1, "Entity有且只有一个标识符"),
    //DefinesIdentity
    CONSTRAINT_02(2, "DefinesIdentity必须在Entity或DomainEvent中使用"),
    //AggregateRoot
    CONSTRAINT_03(3, "AggregateRoot必须是Entity类型"),
    //AggregatePart
    CONSTRAINT_04(4, "AggregatePart必须是Entity或ValueObject类型"),
    CONSTRAINT_05(5, "AggregatePart必须指定其根,且根为AggregateRoot类型"),
    //DomainService
    CONSTRAINT_06(6, "DomainService只定义操作"),
    CONSTRAINT_07(7, "被建模为DomainService的领域对象不能被建模为其它类型"),
    //DomainEvent
    CONSTRAINT_08(8, "DomainEvent有且只有一个标识符"),
    //Repository
    CONSTRAINT_09(9, "Repository只定义操作"),
    CONSTRAINT_10(10, "Repository操作的返回值类型必须是AggregateRoot对象"),
    CONSTRAINT_11(11, "被建模为Repository的领域对象不能被建模为其它类型"),
    //Factory
    CONSTRAINT_12(12, "被建模为Factory的领域对象不能被建模为其它类型"),
    //Specification
    CONSTRAINT_13(13, "规格至少有一个ValidateSpec方法"),
    //ValdateSpec
    CONSTRAINT_14(14, "ValidateSpec方法必须以一个具体领域对象作为参数"),
    CONSTRAINT_15(15, "ValidateSpec方法必须返回布尔值类型"),
    CONSTRAINT_16(16, "只能在Specification中使用");



    private  Integer code;
    private  String rule;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    Constraint(Integer code, String rule) {
        this.code = code;
        this.rule = rule;
    }


}


package ddd.validator;


public enum  MetaEnum {
    VALUEOBJECT("ValueObject", 2),
    ENTITY("Entity", 4),
    DOMAINEVENT("DomainEvent", 8),
    SPECIFICATION("Specification", 16),
    REPOSITORY("Repository", 32),
    AGGREGATEPART("AggregatePart", 64),
    AGGREGATEROOT("AggregateRoot", 128),
    DOMAINSERVICE("DomainService", 256),
    FACTORY("Factory", 512);
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private String type;
    private Integer code;


    MetaEnum(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    static MetaEnum result;

    public static MetaEnum getvalueOf(String shortName) {

        switch (shortName) {
            case "ValueObject":
                result = MetaEnum.VALUEOBJECT;
                break;
            case "Entity":
                result = MetaEnum.ENTITY;
                break;
            case "DomainEvent":
                result = MetaEnum.DOMAINEVENT;
                break;
            case "Specification":
                result = MetaEnum.SPECIFICATION;
                break;
            case "Repository":
                result = MetaEnum.REPOSITORY;
                break;
            case "AggregatePart":
                result = MetaEnum.AGGREGATEPART;
                break;
            case "AggregateRoot":
                result = MetaEnum.AGGREGATEROOT;
                break;
            case "DomainService" :
                result =MetaEnum.DOMAINSERVICE;
                break;
            case "Factory" :
                result =MetaEnum.FACTORY;
                break;

        }
        return result;


    }


}

public enum FunctionsEnum {
    ACOS("acos"),
    ASIN("asin"),
    ATAN("atan"),
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    CTG("ctg"),
    SQRT("sqrt"),
    LN("ln"),
    LOG("log"),
    MOD("mod");

    private final String NAME;

    FunctionsEnum(String name) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }


}

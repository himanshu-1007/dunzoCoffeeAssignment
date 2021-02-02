package dunzo.enums;

public enum ExceptionEnum {
    INVALID_DATA("ERR001","Data is invalid"),
    INSUFFICIENT_INGREDIENT("ERR002","Ingredient is insufficient"),
    MAX_CAPACITY_BREACHED_EXCEPTION("ERR003","Maximum capacity is breached"),
    NO_SLOTS_AVAILABLE("ERR004","No slots are available"),
    NOT_FOUND("ERR005","Data not found");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
    }
}

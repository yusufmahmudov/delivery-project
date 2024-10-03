package food.delivery.telegram.constants;

public enum ButtonCallbackEnum {

    UZBEK_LANGUAGE("uzbek language"),
    ENGLISH_LANGUAGE("english language"),
    RUSSIAN_LANGUAGE("russian language");


    private final String callback;

    ButtonCallbackEnum(String callback) {
        this.callback = callback;
    }

    public String getCallback() {
        return callback;
    }

}

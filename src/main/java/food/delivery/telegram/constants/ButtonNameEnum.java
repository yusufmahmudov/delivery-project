package food.delivery.telegram.constants;

public enum ButtonNameEnum {

    CONTACT_BUTTON_NAME_UZ("\uD83D\uDCF1 Telefon raqamni yuborish"),
    CONTACT_BUTTON_NAME_EN("\uD83D\uDCF1 Send phone number"),
    CONTACT_BUTTON_NAME_RU("\uD83D\uDCF1 Отправить номер телефона"),

    LANGUAGE_BUTTON_NAME_UZ("\uD83C\uDDFA\uD83C\uDDFF O'zbekcha"),
    LANGUAGE_BUTTON_NAME_EN("\uD83C\uDDFA\uD83C\uDDF8 English"),
    LANGUAGE_BUTTON_NAME_RU("\uD83C\uDDF7\uD83C\uDDFA Русский"),

    WEBAPP_BUTTON_NAME_UZ("Buyurtma berish"),
    WEBAPP_BUTTON_NAME_EN("Order Food"),
    WEBAPP_BUTTON_NAME_RU("Pазмещение заказа");


    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }

}

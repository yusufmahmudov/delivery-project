package food.delivery.telegram.constants;

public enum BotMessageEnum {

    FIRST_MESSAGE("Bu xabar eng birinchidir"),


    WEBAPP_MESSAGE_UZ("*Keling, boshlaymiz* \uD83C\uDF5F\n\n" +
            "_Ajoyib tushlikka buyurtma berish uchun quyidagi tugmani bosing!_"),
    WEBAPP_MESSAGE_EN("*Let's get started* \uD83C\uDF5F\n\n" +
            "_Please tap the button below to order your perfect lunch!_"),
    WEBAPP_MESSAGE_RU("Приступаем \uD83C\uDF5F\n\n" +
            "_Нажмите на кнопку ниже, чтобы заказать идеальный обед!_");


    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

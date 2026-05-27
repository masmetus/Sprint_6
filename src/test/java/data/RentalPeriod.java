package data;

public enum RentalPeriod {
    ONE_DAY("сутки"),
    TWO_DAYS("двое суток"),
    THREE_DAYS("трое суток"),
    FOUR_DAYS("четверо суток"),
    FIVE_DAYS("пятеро суток"),
    SIX_DAYS("шестеро суток"),
    SEVEN_DAYS("семеро суток");

    private final String text;

    RentalPeriod(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

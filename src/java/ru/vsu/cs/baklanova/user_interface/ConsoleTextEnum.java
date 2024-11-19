package ru.vsu.cs.baklanova.user_interface;

public enum ConsoleTextEnum {
    RESET("\u001B[0m"),
    YELLOW("\u001B[93m"),
    CYAN("\u001B[36m"),
    RED("\u001B[31m"),
    GREEN("\u001B[38;5;10m"),
    GREY("\u001B[37m"),
    BLACK("\u001b[30m"),
    WHITE("\u001b[38;5;255m"),
    BOLD("\u001B[1m"),
    UNDERLINE("\u001B[4m"),
    WHITE_BG("\u001b[48;5;250m");

    final String code;

    ConsoleTextEnum(String s) {
        code = s;
    }

    public String getCode() {
        return code;
    }
}

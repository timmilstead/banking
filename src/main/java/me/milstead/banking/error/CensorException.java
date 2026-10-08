package me.milstead.banking.error;

/**
 * Thrown when a string contains an offensive word.
 */
public class CensorException extends RuntimeException {

    public CensorException() {
        super("Offensive word detected");
    }

}

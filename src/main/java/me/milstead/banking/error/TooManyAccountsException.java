package me.milstead.banking.error;

public class TooManyAccountsException extends RuntimeException {

    public TooManyAccountsException() {
        super("Max 5 accounts per customer");
    }
}

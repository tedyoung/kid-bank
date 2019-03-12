package com.learnwithted.kidbank.adapter.sms;

public class InvalidCommandException extends RuntimeException {
  public InvalidCommandException(Throwable cause) {
    super(cause);
  }

  public InvalidCommandException() {
    super();
  }

  public InvalidCommandException(String message) {
    super(message);
  }

  public InvalidCommandException(String message, Throwable cause) {
    super(message, cause);
  }
}

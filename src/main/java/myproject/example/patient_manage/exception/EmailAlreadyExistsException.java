package myproject.example.patient_manage.exception;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}

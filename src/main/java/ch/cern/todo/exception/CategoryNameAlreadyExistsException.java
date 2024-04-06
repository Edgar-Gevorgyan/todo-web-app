package ch.cern.todo.exception;

public class CategoryNameAlreadyExistsException extends Exception {
    public CategoryNameAlreadyExistsException(String msg) {
        super(msg);
    }
}


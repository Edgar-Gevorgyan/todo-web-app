package ch.cern.todo.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String msg) {
        super(msg);
    }
}

package pl.ros1yn.personalbudgetapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpensesNotFoundException extends ResponseStatusException {

    public ExpensesNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Expanses not found");
    }
}

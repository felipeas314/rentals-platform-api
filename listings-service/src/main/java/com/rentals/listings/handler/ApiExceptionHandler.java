package com.rentals.listings.handler;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.joining;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
    var msg = ex.getBindingResult().getFieldErrors()
      .stream().map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
      .collect(joining("; "));
    var pd = ProblemDetail.forStatus(400);
    pd.setDetail(msg);
    return pd;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleBadRequest(IllegalArgumentException ex) {
    var pd = ProblemDetail.forStatus(400);
    pd.setDetail(ex.getMessage());
    return pd;
  }
}

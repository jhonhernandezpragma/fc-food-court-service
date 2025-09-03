package com.pragma.fc.food_curt.domain.exception;

public class RestaurantInvalidUserRoleException extends RuntimeException {
  public RestaurantInvalidUserRoleException(Long documentNumber) {
    super("Invalid user role for user with document number " + documentNumber + ": expected role OWNER");

  }
}

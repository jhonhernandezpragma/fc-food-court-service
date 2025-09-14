package com.pragma.fc.food_curt.domain.spi;

public interface IUserClientPort {
    Boolean isOwner(Long documentNumber);

    String getPhoneNumberByDocumentNumber(Long documentNumber);
}

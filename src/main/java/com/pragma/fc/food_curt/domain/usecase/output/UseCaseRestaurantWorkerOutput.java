package com.pragma.fc.food_curt.domain.usecase.output;

public class UseCaseRestaurantWorkerOutput {
    private Long workerDocumentNumber;
    private Long restaurantNit;

    public UseCaseRestaurantWorkerOutput() {
    }

    public UseCaseRestaurantWorkerOutput(Long workerDocumentNumber, Long restaurantNit) {
        this.workerDocumentNumber = workerDocumentNumber;
        this.restaurantNit = restaurantNit;
    }

    public Long getWorkerDocumentNumber() {
        return workerDocumentNumber;
    }

    public void setWorkerDocumentNumber(Long workerDocumentNumber) {
        this.workerDocumentNumber = workerDocumentNumber;
    }

    public Long getRestaurantNit() {
        return restaurantNit;
    }

    public void setRestaurantNit(Long restaurantNit) {
        this.restaurantNit = restaurantNit;
    }
}

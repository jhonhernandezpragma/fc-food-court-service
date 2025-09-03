package com.pragma.fc.food_curt.domain.model;

public class Restaurant {
    private Long nit;
    private Long ownerDocumentNumber;
    private String name;
    private String address;
    private String phone;
    private String logoUrl;

    public Restaurant() {
    }

    public Restaurant(Long nit, Long ownerDocumentNumber, String name, String address, String phone, String logoUrl) {
        this.nit = nit;
        this.ownerDocumentNumber = ownerDocumentNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.logoUrl = logoUrl;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public Long getOwnerDocumentNumber() {
        return ownerDocumentNumber;
    }

    public void setOwnerDocumentNumber(Long ownerDocumentNumber) {
        this.ownerDocumentNumber = ownerDocumentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

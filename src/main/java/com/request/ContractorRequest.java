package com.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ContractorRequest implements Serializable {

    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private String role;

    public ContractorRequest(String name, String address, String email, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContractorRequest that = (ContractorRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstName, lastName, address, email, password, role);
    }
}
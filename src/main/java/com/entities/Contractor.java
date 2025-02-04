package com.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity(name="contractor")
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int  id;

    @Column
    private String companyName;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;


    @JoinColumn
    @OneToMany
    private List<SubContractor> subContractorList;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contractor that = (Contractor) o;
        return id == that.id && Objects.equals(companyName, that.companyName) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(role, that.role) && Objects.equals(subContractorList, that.subContractorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, lastName, firstName, address, email, password, role, subContractorList);
    }
}

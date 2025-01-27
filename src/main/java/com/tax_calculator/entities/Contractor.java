package com.tax_calculator.entities;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity(name="contractor")
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int  id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String password;


    @JoinColumn
    @OneToMany
    private List<SubContractor> subContractorList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SubContractor> getSubContractorList() {
        return subContractorList;
    }

    public void setSubContractorList(List<SubContractor> subContractorList) {
        this.subContractorList = subContractorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contractor that = (Contractor) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(subContractorList, that.subContractorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, email, password, subContractorList);
    }
}

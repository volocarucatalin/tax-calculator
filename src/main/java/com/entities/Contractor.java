package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity(name = "contractors")
@AllArgsConstructor
@NoArgsConstructor
public class Contractor {

    @Id
    @Column(name = "user_id")
    private int userId;
    @Column(name = "company_name")
    private String companyName;
    @Column
    private String address;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contractor that = (Contractor) o;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}

package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "subcontractors")
@AllArgsConstructor
@NoArgsConstructor
public class SubContractor {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column
    private Integer contractorId;
    @Column
    private String utr;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubContractor that = (SubContractor) o;
        return userId == that.userId && contractorId == that.contractorId;
    }
}

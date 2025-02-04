package com.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This request object contains the details provided on the form for a subcontractor registration
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegSubContractReq {
    private Integer contractorId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String utr;
}

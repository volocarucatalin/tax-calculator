package com.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubContractorRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String utr;
}

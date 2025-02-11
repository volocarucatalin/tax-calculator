package com.response;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
public class SubContractorResponse {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String utr;
}

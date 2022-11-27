package net.ontopsolutions.lambda.s3sns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientCheckOutEvent {

    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;

}

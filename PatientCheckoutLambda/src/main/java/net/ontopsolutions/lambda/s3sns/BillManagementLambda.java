package net.ontopsolutions.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import static net.ontopsolutions.lambda.utils.UtilityJackson.parser;

public class BillManagementLambda {

    public void handle(SNSEvent snsEvent) {
        snsEvent.getRecords().forEach(record -> {
          PatientCheckOutEvent patientCheckOutEvent = parser(record.getSNS().getMessage(), PatientCheckOutEvent.class);
            System.out.println(record.getSNS().getMessage());
        });
    }

}

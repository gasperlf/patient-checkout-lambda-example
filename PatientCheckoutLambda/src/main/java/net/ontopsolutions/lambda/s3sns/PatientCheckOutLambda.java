package net.ontopsolutions.lambda.s3sns;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import net.ontopsolutions.lambda.utils.UtilityJackson;

import java.util.List;

public class PatientCheckOutLambda {

    public static final String PATIENT_CHECKOUT_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");
    private AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
    private AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();

    @SneakyThrows
    public void handle(S3Event event) {
        event.getRecords().forEach(record -> {
            S3ObjectInputStream s3ObjectInputStream = getS3Object(record);
            List<PatientCheckOutEvent> patientCheckOutEventList = getListDto(s3ObjectInputStream);
            //s3ObjectInputStream.close();
            publishEvent(patientCheckOutEventList);
        });
    }

    private void publishEvent(List<PatientCheckOutEvent> list) {
        list.forEach(patient -> {
            amazonSNS.publish(PATIENT_CHECKOUT_TOPIC, UtilityJackson.parserString(patient));
        });
    }

    private List<PatientCheckOutEvent> getListDto(S3ObjectInputStream s3ObjectInputStream) {
        return UtilityJackson.parser(s3ObjectInputStream, new TypeReference<List<PatientCheckOutEvent>>() {
        });
    }

    private S3ObjectInputStream getS3Object(final S3EventNotification.S3EventNotificationRecord record) {
        return amazonS3.getObject(record.getS3().getBucket().getName(),
                record.getS3().getObject().getKey()).getObjectContent();
    }


}

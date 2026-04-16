package kz.diploma.rprettser.attendance_analyser.kafka.consumer;

import kz.diploma.rprettser.attendance_analyser.business.dto.kafka.FaceRecognitionEventDto;
import kz.diploma.rprettser.attendance_analyser.business.facade.FaceRecognitionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaceRecognitionConsumer {

//    private final FaceRecognitionFacade faceRecognitionFacade;
//
//    @KafkaListener(
//            topics = "${kafka.topics.face-recognition}",
//            groupId = "${spring.kafka.consumer.group-id}"
//    )
    public void consume(FaceRecognitionEventDto dto) {
        // TODO
    }
}

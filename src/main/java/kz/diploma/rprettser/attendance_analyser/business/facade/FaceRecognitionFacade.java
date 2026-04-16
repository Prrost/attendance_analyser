package kz.diploma.rprettser.attendance_analyser.business.facade;

import kz.diploma.rprettser.attendance_analyser.business.dto.kafka.FaceRecognitionEventDto;

public interface FaceRecognitionFacade {

    void processEvent(FaceRecognitionEventDto dto);

    void retryFailedEvents();
}

package kz.diploma.rprettser.attendance_analyser.business.facade.impl;

import kz.diploma.rprettser.attendance_analyser.business.dto.kafka.FaceRecognitionEventDto;
import kz.diploma.rprettser.attendance_analyser.business.facade.FaceRecognitionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceRecognitionFacadeImpl implements FaceRecognitionFacade {

    @Override
    public void processEvent(FaceRecognitionEventDto dto) {
        // TODO
    }

    @Override
    public void retryFailedEvents() {
        // TODO
    }
}

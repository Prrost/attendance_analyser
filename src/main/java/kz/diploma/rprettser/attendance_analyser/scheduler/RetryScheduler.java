package kz.diploma.rprettser.attendance_analyser.scheduler;

import kz.diploma.rprettser.attendance_analyser.business.facade.FaceRecognitionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryScheduler {

    private final FaceRecognitionFacade faceRecognitionFacade;

    @Scheduled(fixedRateString = "${scheduler.retry.rate-ms:120000}")
    public void retry() {
        // TODO
    }
}

package kz.diploma.rprettser.attendance_analyser.business.client;

import kz.diploma.rprettser.attendance_analyser.business.dto.lms.*;
import kz.diploma.rprettser.attendance_analyser.common.model.LmsPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LmsClientImpl implements LmsClient {

    private final RestTemplate restTemplate;

    @Value("${lms.base-url}")
    private String lmsBaseUrl;

    @Override
    public List<LmsClassroomDto> getAllClassrooms() {
        List<LmsClassroomDto> resultList = new ArrayList<>();

        int totalElements = 100;
        int processedElements = 0;
        for (int page = 0; processedElements < totalElements; page++) {

            String url = lmsBaseUrl + "/api/classroom?page=" + page + "&size=100&sort=id,asc";
            log.debug("Fetching classrooms page {} from LMS: {}", page, url);

            try {
                ResponseEntity<LmsPageResponse<LmsClassroomDto>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("Failed to fetch classrooms from LMS");
                if (response.getBody() == null) break;

                totalElements = Math.toIntExact(response.getBody().getTotalElements());
                processedElements += response.getBody().getNumberOfElements();

                resultList.addAll(response.getBody().getContent());
            } catch (Exception e) {
                log.error("Failed to fetch classrooms from LMS", e);
                throw e;
            }
        }

        log.info("Fetched {} classrooms from LMS", resultList.size());
        return resultList;
    }

    @Override
    public List<LmsStudentGroupDto> getAllStudentGroups() {
        List<LmsStudentGroupDto> resultList = new ArrayList<>();

        int totalElements = 100;
        int processedElements = 0;
        for (int page = 0; processedElements < totalElements; page++) {

            String url = lmsBaseUrl + "/api/student_group?page=" + page + "&size=100&sort=id,asc";
            log.debug("Fetching student groups page {} from LMS: {}", page, url);

            try {
                ResponseEntity<LmsPageResponse<LmsStudentGroupDto>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("Failed to fetch student groups from LMS");
                if (response.getBody() == null) break;

                totalElements = response.getBody().getTotalElements();
                processedElements += response.getBody().getNumberOfElements();

                resultList.addAll(response.getBody().getContent());
            } catch (Exception e) {
                log.error("Failed to fetch student groups from LMS", e);
                throw e;
            }
        }

        log.info("Fetched {} student groups from LMS", resultList.size());
        return resultList;
    }

    @Override
    public List<LmsStudentDto> getAllStudents() {
        List<LmsStudentDto> resultList = new ArrayList<>();

        int totalElements = 100;
        int processedElements = 0;
        for (int page = 0; processedElements < totalElements; page++) {

            String url = lmsBaseUrl + "/api/student?page=" + page + "&size=100&sort=id,asc";
            log.debug("Fetching students page {} from LMS: {}", page, url);

            try {
                ResponseEntity<LmsPageResponse<LmsStudentDto>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("Failed to fetch students from LMS");
                if (response.getBody() == null) break;

                totalElements = response.getBody().getTotalElements();
                processedElements += response.getBody().getNumberOfElements();

                resultList.addAll(response.getBody().getContent());
            } catch (Exception e) {
                log.error("Failed to fetch students from LMS", e);
                throw e;
            }
        }

        log.info("Fetched {} students from LMS", resultList.size());
        return resultList;
    }

    @Override
    public List<LmsLessonDto> getAllLessons() {
        List<LmsLessonDto> resultList = new ArrayList<>();

        int totalElements = 100;
        int processedElements = 0;
        for (int page = 0; processedElements < totalElements; page++) {

            String url = lmsBaseUrl + "/api/lesson?page=" + page + "&size=100&sort=id,asc";
            log.debug("Fetching lessons page {} from LMS: {}", page, url);

            try {
                ResponseEntity<LmsPageResponse<LmsLessonDto>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

                if (response.getStatusCode() != HttpStatus.OK) throw new RuntimeException("Failed to fetch lessons from LMS");
                if (response.getBody() == null) break;

                totalElements = response.getBody().getTotalElements();
                processedElements += response.getBody().getNumberOfElements();

                resultList.addAll(response.getBody().getContent());
            } catch (Exception e) {
                log.error("Failed to fetch lessons from LMS", e);
                throw e;
            }
        }

        log.info("Fetched {} lessons from LMS", resultList.size());
        return resultList;
    }

    @Override
    public void setAttendance(LmsSetAttendanceDto dto) {
        String url = lmsBaseUrl + "/api/attendance/set_attendance";
        log.debug("Pushing attendance to LMS: {}", dto);
        try {
            restTemplate.postForObject(url, dto, Object.class);
        } catch (Exception e) {
            log.error("Failed to push attendance to LMS, dto: {}", dto, e);
            throw e;
        }
    }
}

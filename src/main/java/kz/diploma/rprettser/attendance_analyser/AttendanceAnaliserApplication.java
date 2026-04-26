package kz.diploma.rprettser.attendance_analyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AttendanceAnaliserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceAnaliserApplication.class, args);
	}

}

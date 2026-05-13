package kz.diploma.rprettser.attendance_analyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class AttendanceAnaliserApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(System.getenv().getOrDefault("APP_TIMEZONE", "Europe/Almaty")));
		SpringApplication.run(AttendanceAnaliserApplication.class, args);
	}

}

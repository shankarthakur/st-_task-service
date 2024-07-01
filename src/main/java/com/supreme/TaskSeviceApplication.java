package com.supreme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskSeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSeviceApplication.class, args);
	}

}

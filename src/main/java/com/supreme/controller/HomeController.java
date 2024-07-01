package com.supreme.controller;

import com.supreme.model.Task;
import com.supreme.model.TaskStatus;
import com.supreme.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> getAssignedUsersTask() {

        return new ResponseEntity<>("Welcome to task service", HttpStatus.OK);
    }
}

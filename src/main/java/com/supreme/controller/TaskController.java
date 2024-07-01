package com.supreme.controller;

import com.supreme.model.Task;
import com.supreme.model.TaskStatus;
import com.supreme.model.UserDto;
import com.supreme.service.TaskService;
import com.supreme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    private TaskService taskService;

    private UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization")
                                           String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);
        Task createdTask=taskService.createTask(task, userDto.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }


    @GetMapping ("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @RequestHeader("Authorization")
    String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);
        Task task=taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @GetMapping ("/user")
    public ResponseEntity<List<Task>> getAssignedUserTask(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization")
    String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);

        List<Task> tasks =taskService.assignedUserTask(userDto.getId(),status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping ()
    public ResponseEntity<List<Task>> getAllTask(
            @RequestParam(required = false)TaskStatus status,
            @RequestHeader("Authorization")
            String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);

        List<Task> tasks =taskService.getAllTask(status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping ("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization")
            String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);

        Task tasks =taskService.assignToUser(userId,id);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task req,
            @RequestHeader("Authorization")
            String jwt) throws Exception {

        UserDto userDto = userService.getUserProfile(jwt);

        Task tasks =taskService.updateTask(id,req,userDto.getId());

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping ("/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable Long id) throws Exception {

        Task tasks =taskService.completeTask(id);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deletTask(
            @PathVariable Long id) throws Exception {

        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

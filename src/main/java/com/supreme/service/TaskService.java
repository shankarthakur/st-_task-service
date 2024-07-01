package com.supreme.service;

import com.supreme.model.Task;
import com.supreme.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    Task createTask(Task task, String requesterRole)throws Exception;

    Task getTaskById(Long id)throws Exception;

    List<Task> getAllTask(TaskStatus status);

    Task updateTask(Long id, Task updateTask,Long userId)throws Exception;

    void deleteTask(Long id) throws Exception;

    Task assignToUser(Long userId, Long taskId)throws Exception;

    List<Task> assignedUserTask(Long userId, TaskStatus status);

    Task completeTask(Long taskId)throws Exception;
}

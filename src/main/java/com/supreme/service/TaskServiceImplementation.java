package com.supreme.service;

import com.supreme.model.Task;
import com.supreme.model.TaskStatus;
import com.supreme.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService{

    private TaskRepository repository;
    @Autowired
    public TaskServiceImplementation(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (!requesterRole.equals(("ROLE_ADMIN"))){
            throw new Exception("Only admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return repository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("task not found with id"+id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTask=repository.findAll();

        List<Task> filteredTasks = allTask.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())

        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updateTask, Long userId) throws Exception {

        Task existingTask = getTaskById(id);

        if (updateTask.getTitle()!=null){
            existingTask.setTitle(updateTask.getTitle());
        }
        if (updateTask.getImage()!=null){
            existingTask.setImage(updateTask.getImage());
        }
        if (updateTask.getDescription()!=null){
            existingTask.setDescription(updateTask.getDescription());
        }
        if (updateTask.getStatus()!=null){
            existingTask.setStatus(updateTask.getStatus());
        }
        return repository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {

        getTaskById(id);

        repository.deleteById(id);
    }

    @Override
    public Task assignToUser(Long userId, Long taskId) throws Exception {

        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);

        return repository.save(task);
    }

    @Override
    public List<Task> assignedUserTask(Long userId, TaskStatus status) {

        List<Task> allTask=repository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTask.stream().filter(
                task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())

        ).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task =getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return repository.save(task);
    }
}

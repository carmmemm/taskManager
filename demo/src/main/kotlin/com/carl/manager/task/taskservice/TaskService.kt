package com.carl.manager.task.taskservice

import com.carl.manager.db.SqlRepository
import com.carl.manager.task.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService : ITaskService {

    @Autowired
    val sqlRepository: SqlRepository? = null

    override fun insert(task: Task): Task {
        val id: Long? = sqlRepository?.taskInserter(
            "INSERT INTO tasks (`name`, `status`, `description`) VALUES (?, ?, ?);",
            task.name,
            task.status.name,
            task.description
        )?.longValueExact()
        return Task(task.name, task.description, task.status, id)
    }

    override fun getById(id: Int): Task? {
        return sqlRepository?.findTaskById("SELECT * FROM tasks WHERE task_id = ?;", id)
    }

    override fun getAllTasks(): List<Task>? {
        return sqlRepository?.getTasks("SELECT * FROM tasks")
    }

    override fun removeTask(id: Int) {
        sqlRepository?.removeTaskById("DELETE FROM tasks WHERE task_id = ?;", id)
    }

    override fun updateTask(id: Int, task: Task): Task? {
        sqlRepository?.updateTaskById("UPDATE tasks SET name = ?, status = ?, description = ? WHERE task_id = ?;", id, task)
        return getById(id)
    }
}
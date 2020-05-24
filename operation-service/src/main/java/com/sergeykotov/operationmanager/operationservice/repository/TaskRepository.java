package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TaskRepository {
    private static final String CREATE_CMD = "insert into task (name, note) values (?, ?)";
    private static final String UPDATE_CMD = "update task set name = ?, note = ? where id = ?";
    private static final String DELETE_CMD = "delete from task where id = ?";

    public boolean create(Task task) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getNote());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean update(Task task) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getNote());
            preparedStatement.setLong(3, task.getId());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean delete(long id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CMD)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }
}
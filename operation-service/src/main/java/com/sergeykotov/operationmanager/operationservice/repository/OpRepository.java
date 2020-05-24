package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Op;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class OpRepository {
    private static final String CREATE_CMD = "insert into op (name, note) values (?, ?);";
    private static final String UPDATE_CMD = "update op set name = ?, note = ? where id = ?";
    private static final String DELETE_CMD = "delete from op where id = ?";

    public boolean create(Op op) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, op.getName());
            preparedStatement.setString(2, op.getNote());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean update(Op op) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, op.getName());
            preparedStatement.setString(2, op.getNote());
            preparedStatement.setLong(3, op.getId());
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
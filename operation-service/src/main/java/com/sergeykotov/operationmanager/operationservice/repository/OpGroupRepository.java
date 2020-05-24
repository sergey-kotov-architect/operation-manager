package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.OpGroup;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class OpGroupRepository {
    private static final String CREATE_CMD = "insert into op_group (name, note) values (?, ?);";
    private static final String UPDATE_CMD = "update op_group set name = ?, note = ? where id = ?";
    private static final String DELETE_CMD = "delete from op_group where id = ?";

    public boolean create(OpGroup opGroup) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, opGroup.getName());
            preparedStatement.setString(2, opGroup.getNote());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean update(OpGroup opGroup) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, opGroup.getName());
            preparedStatement.setString(2, opGroup.getNote());
            preparedStatement.setLong(3, opGroup.getId());
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
package com.sergeykotov.operationmanager.operationservice.repository;

import com.sergeykotov.operationmanager.operationservice.model.Op;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OpRepository {
    private static final String CREATE_CMD = "insert into op (name, note, status, profit, cost, op_group_id, task_id, executor_id, period_id) values (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_CMD = "update op set name = ?, note = ?, status = ?, profit = ?, cost = ?, op_group_id = ?, task_id = ?, executor_id = ?, period_id = ? where id = ?";
    private static final String DELETE_CMD = "delete from op where id = ?";
    private static final String UPDATE_STATUS_CMD = "update op set status = ? where id = ?";

    public boolean create(Op op) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, op.getName());
            preparedStatement.setString(2, op.getNote());
            preparedStatement.setString(3, op.getStatus());
            preparedStatement.setDouble(4, op.getProfit());
            preparedStatement.setDouble(5, op.getCost());
            preparedStatement.setLong(6, op.getOpGroup().getId());
            preparedStatement.setLong(7, op.getTask().getId());
            preparedStatement.setLong(8, op.getExecutor().getId());
            preparedStatement.setLong(9, op.getPeriod().getId());
            return preparedStatement.executeUpdate() == 1;
        }
    }

    public boolean update(Op op) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, op.getName());
            preparedStatement.setString(2, op.getNote());
            preparedStatement.setString(3, op.getStatus());
            preparedStatement.setDouble(4, op.getProfit());
            preparedStatement.setDouble(5, op.getCost());
            preparedStatement.setLong(6, op.getOpGroup().getId());
            preparedStatement.setLong(7, op.getTask().getId());
            preparedStatement.setLong(8, op.getExecutor().getId());
            preparedStatement.setLong(9, op.getPeriod().getId());
            preparedStatement.setLong(10, op.getId());
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

    public int[] update(List<Op> ops) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            for (Op op : ops) {
                preparedStatement.setString(1, op.getName());
                preparedStatement.setString(2, op.getNote());
                preparedStatement.setString(3, op.getStatus());
                preparedStatement.setDouble(4, op.getProfit());
                preparedStatement.setDouble(5, op.getCost());
                preparedStatement.setLong(6, op.getOpGroup().getId());
                preparedStatement.setLong(7, op.getTask().getId());
                preparedStatement.setLong(8, op.getExecutor().getId());
                preparedStatement.setLong(9, op.getPeriod().getId());
                preparedStatement.setLong(10, op.getId());
                preparedStatement.addBatch();
            }
            return preparedStatement.executeBatch();
        }
    }

    public int[] updateStatus(List<Op> ops) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_CMD)) {
            for (Op op : ops) {
                preparedStatement.setString(1, op.getStatus());
                preparedStatement.setLong(2, op.getId());
                preparedStatement.addBatch();
            }
            return preparedStatement.executeBatch();
        }
    }
}
package com.blog.app.repository;

import com.blog.app.domain.User;
import com.blog.app.domain.UserRepository;
import com.blog.app.exceptions.UserNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;


public class UserRepo implements UserRepository
{
    private DataSource dataSource;
    public UserRepo(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    // createUser - на вход принимает объект пользователя, записывает SQL запрос в переменную, внутри блока try,
    // выбираем свободное соединение и готовим запрос к выполнению. Подставляет к плейсхоледрам ? - значения из
    // объекта User, отправляет запрос в БД, получает таблицу с ответом, и кладет ее в объект
    public User createUser( User user)
    {
        String query = "INSERT INTO users(name, email, password_hash) VALUES(?, ?, ?) RETURNING id";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                user.setId(rs.getLong("id"));
            }
        } catch (SQLException e)
        {
            throw new RuntimeException("failed to create user", e);
        }
        return user;
    }

    public User getUserById(Long id)
    {
        String query = "SELECT id, name, email, password_hash, created_at FROM users WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                User user = new User();
                user.setId( rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail( rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setCreatedAt( rs.getObject("created_at", OffsetDateTime.class));
                return user;
            } else
            {
                throw new UserNotFoundException("user not found");
            }
        } catch (SQLException e)
        {
            throw new RuntimeException("failed to get user by id", e);
        }
    }

    public User getUserByEmail(String email)
    {
        String query = "SELECT id, name, email, password_hash, created_at FROM users WHERE email = ?";
        if (email == null || email.isBlank())
        {
            throw new IllegalArgumentException("email is required");
        }

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail( rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setCreatedAt( rs.getObject("created_at", OffsetDateTime.class));
                return user;
            } else
            {
                throw new UserNotFoundException("user not found");
            }
        } catch (SQLException e)
        {
            throw new RuntimeException("failed to get user by email", e);
        }
    }

    public void updateUser(User user)
    {
        String query = "UPDATE users SET name = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setString(1, user.getName());
            stmt.setLong(2, user.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0)
            {
                throw new UserNotFoundException("failed to update user fields");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("failed to update user", e);
        }

    }

    public void deleteUser(Long id)
    {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0)
            {
                throw new UserNotFoundException("failed to found user");
            }
        } catch (SQLException e)
        {
            throw new RuntimeException("failed to delete user", e);
        }
    }
}

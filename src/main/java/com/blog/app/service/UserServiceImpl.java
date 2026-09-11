package com.blog.app.service;

import com.blog.app.domain.User;
import com.blog.app.domain.UserService;
import com.blog.app.exceptions.InvalidCredentialsException;
import com.blog.app.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;


public class UserServiceImpl implements UserService
{
    private UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public User register(User user, String rawPassword)
    {
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        user.setPasswordHash(hashed);
        return userRepo.createUser(user);
    }

    public User login(String email, String password)
    {
        User user = userRepo.getUserByEmail(email);
        if (BCrypt.checkpw(password, user.getPasswordHash()))
            return user;
        else
            throw new InvalidCredentialsException("the credentials as incorrect");
    }

    public User getUserById(Long id)
    {
        return userRepo.getUserById(id);
    }

    public void updateUser(User user)
    {
        userRepo.updateUser(user);
    }

    public void deleteUser(Long id)
    {
        userRepo.deleteUser(id);
    }
}

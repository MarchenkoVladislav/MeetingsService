package ru.marchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.repository.UsersRepo;

/**
 * @author Vladislav Marchenko
 */
@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;

    public User getUserByID(String userID) {
        return usersRepo.getOne(userID);
    }
}

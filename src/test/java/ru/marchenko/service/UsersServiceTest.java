package ru.marchenko.service;

import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.repository.UsersRepo;

import static org.junit.Assert.*;

/**
 * @author Vladislav Marchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private UsersService usersService;

    @Test(expected = LazyInitializationException.class)
    public void getUserByID() {
        User user = usersRepo.save(new User("1323233223","Name", "vlad125091999@mail.ru"));
        Assert.assertEquals(user, usersService.getUserByID("1323233223"));
    }

    @Test(expected = LazyInitializationException.class)
    public void getUserbyEmail() {
        User user = usersRepo.save(new User("1333223","Name", "vlad1250919999@mail.ru"));
        Assert.assertEquals(user, usersService.getUserbyEmail("vlad1250919999@mail.ru"));
    }
}
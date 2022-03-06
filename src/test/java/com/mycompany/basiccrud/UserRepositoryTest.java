package com.mycompany.basiccrud;

import com.mycompany.basiccrud.user.User;
import com.mycompany.basiccrud.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    public void testAddNewUser(){
    User user = new User();
    user.setEmail("danielextra@gmail.com");
    user.setFirstName("Daniel");
    user.setLastName("Owolabi");
    user.setPassword("12345678");

    User savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }


    @Test
    public void testListAllUsers(){
       Iterable<User> userList = userRepository.findAll();
        Assertions.assertThat(userList).hasSizeGreaterThan(0);
        for(User users : userList){
            System.out.println(users);
        }

    }

    @Test
    public void testUpdateUser(){
        Integer userId = 1;
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        user.setPassword("111111111");
        User savedUser = userRepository.save(user);
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("111111111");
    }

    @Test
    public void testGetUser(){
        Integer userId = 2;
        Optional<User> userOptional = userRepository.findById(userId);
        Assertions.assertThat(userOptional).isPresent();
    }


    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        userRepository.deleteById(userId);
        Optional<User> userOptional = userRepository.findById(userId);
        Assertions.assertThat(userOptional).isNotPresent();

    }
}

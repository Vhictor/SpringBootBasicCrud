package com.mycompany.basiccrud.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> getAllUsers(){
       List<User> userList = (List<User>) userRepository.findAll();
        return userList;
    }

    public void saveNewUser(User user){
        userRepository.save(user);
    }

    public User findUserById(Integer id) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }throw new UserNotFoundException("Could not found user " +id);
    }

    public void deleteUser(Integer id) throws UserNotFoundException{
        Long count = userRepository.countById(id);
        if (count==null|| count==0){
            throw new UserNotFoundException("Could not found user");
        }
        userRepository.deleteById(id);
    }


}

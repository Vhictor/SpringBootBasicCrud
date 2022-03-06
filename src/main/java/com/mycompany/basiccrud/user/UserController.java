package com.mycompany.basiccrud.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/users")
    private String showAllUsers(Model model){
        List<User> userList = userService.getAllUsers();
        model.addAttribute("usersList", userList);
        return "users";
    }

    @GetMapping("/users/new")
    private String showFormNewUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("pageTitle","Add New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    private String saveUser(User user, RedirectAttributes attributes){
        userService.saveNewUser(user);
        attributes.addFlashAttribute("message", "The user data has been saved successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    private String editUserForm(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes){
        try {
            User user = userService.findUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User With ID " + id);
            return "user_form";
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }

    }


    @GetMapping("/users/delete/{id}")
    private String deleteUser(@PathVariable("id") Integer id, RedirectAttributes attributes){
        try {
            userService.deleteUser(id);
            attributes.addFlashAttribute("message", "The user with ID " + id + " has been deleted successfully");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";

    }

}

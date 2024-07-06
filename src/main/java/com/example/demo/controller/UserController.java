package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    //get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable (value = "id") long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourseNotFoundException("User not found with id: "+ userId));

    }

    //create a user
    @PostMapping
    public User createUser(@RequestBody User user){
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping ("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable ("id") long userId){
        User existingUser= this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourseNotFoundException("User not found with id: "+ userId));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        return this.userRepository.save(existingUser);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
        User existingUser= this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourseNotFoundException("User not found with id: "+ userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();

    }
}

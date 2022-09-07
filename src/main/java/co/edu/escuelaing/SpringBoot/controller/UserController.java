package co.edu.escuelaing.SpringBoot.controller;

import co.edu.escuelaing.SpringBoot.dto.UserDTO;
import co.edu.escuelaing.SpringBoot.entities.User;
import co.edu.escuelaing.SpringBoot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserController(@Autowired UserService userService) {

        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        users.forEach(el -> modelMapper.map(el,UserDTO.class));
        List<UserDTO> dtos = modelMapper.map(users, List.class);
        return new ResponseEntity<>(dtos,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);
        UserDTO dto = modelMapper.map(user,UserDTO.class);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }


    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDto) {
        return new ResponseEntity<>(userDto,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO user, @PathVariable String id) {
        User newU = userService.update(modelMapper.map(user,User.class),id);
        return new ResponseEntity<>(modelMapper.map(newU,UserDTO.class),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        userService.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}

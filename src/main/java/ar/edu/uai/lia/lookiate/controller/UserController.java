package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<Void> updateProfile(@PathVariable String userId, @RequestBody String userName) {
        userService.updateProfile(userId, userName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable String userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getDetails(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserDetails(userId));
    }

    @PatchMapping("/{userId}/subscription")
    public ResponseEntity<Void> updateSubscription(@PathVariable String userId, @RequestParam String newType) {
        userService.updateSubscription(userId, newType);
        return ResponseEntity.noContent().build();
    }
}

package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.repository.UserRepository;
import ar.edu.uai.lia.lookiate.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        user.setCreatedAt(new Date());
        user.setIsActive(true);
        return userRepository.save(user);
    }

    @Override
    public void updateProfile(String userId, String userName) {
        User user = getUserOrThrow(userId);
        user.updateProfile(userName);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(String userId) {
        User user = getUserOrThrow(userId);
        user.deactivate();
        userRepository.save(user);
    }

    @Override
    public User getUserDetails(String userId) {
        return getUserOrThrow(userId);
    }

    @Override
    public void updateSubscription(String userId, String newType) {
        User user = getUserOrThrow(userId);
        user.changeSubscription(newType);
        userRepository.save(user);
    }

    private User getUserOrThrow(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }
}

package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.User;

public interface UserService {
    User registerUser(User user);
    void updateProfile(String userId, String userName);
    void deactivateUser(String userId);
    User getUserDetails(String userId);
    void updateSubscription(String userId, String newType);
}

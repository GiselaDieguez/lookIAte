package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerReturnsSavedUser() {
        User user = new User();
        user.setUserId("u1");
        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.register(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    void updateProfileReturnsNoContent() {
        ResponseEntity<Void> response = userController.updateProfile("u1", "nuevo");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).updateProfile("u1", "nuevo");
    }

    @Test
    void deactivateReturnsNoContent() {
        ResponseEntity<Void> response = userController.deactivate("u1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).deactivateUser("u1");
    }

    @Test
    void getDetailsReturnsUser() {
        User user = new User();
        user.setUserId("u1");
        when(userService.getUserDetails("u1")).thenReturn(user);

        ResponseEntity<User> response = userController.getDetails("u1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);
    }

    @Test
    void updateSubscriptionReturnsNoContent() {
        ResponseEntity<Void> response = userController.updateSubscription("u1", "PREMIUM");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).updateSubscription("u1", "PREMIUM");
    }
}

package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUserSetsCreatedAtAndActivatesUser() {
        User user = new User();
        user.setUserId("u1");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User registered = userService.registerUser(user);

        assertThat(registered.getCreatedAt()).isNotNull();
        assertThat(registered.getIsActive()).isTrue();
        verify(userRepository).save(user);
    }

    @Test
    void updateProfileChangesUserName() {
        User user = new User();
        user.setUserId("u1");
        user.setUserName("viejo");
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.updateProfile("u1", "nuevo");

        assertThat(user.getUserName()).isEqualTo("nuevo");
        verify(userRepository).save(user);
    }

    @Test
    void updateProfileThrowsWhenUserNotFound() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateProfile("missing", "nuevo"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void deactivateUserSetsInactive() {
        User user = new User();
        user.setUserId("u1");
        user.setIsActive(true);
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.deactivateUser("u1");

        assertThat(user.getIsActive()).isFalse();
        assertThat(user.isActive()).isFalse();
        verify(userRepository).save(user);
    }

    @Test
    void getUserDetailsReturnsUser() {
        User user = new User();
        user.setUserId("u1");
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));

        assertThat(userService.getUserDetails("u1")).isEqualTo(user);
    }

    @Test
    void getUserDetailsThrowsWhenUserNotFound() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserDetails("missing"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSubscriptionChangesType() {
        User user = new User();
        user.setUserId("u1");
        user.setSubscriptionType("FREE");
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.updateSubscription("u1", "PREMIUM");

        assertThat(user.getSubscriptionType()).isEqualTo("PREMIUM");
        verify(userRepository).save(user);
    }
}

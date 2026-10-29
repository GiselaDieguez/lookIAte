package ar.edu.uai.lia.lookiate.repository.jpa;

import ar.edu.uai.lia.lookiate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}

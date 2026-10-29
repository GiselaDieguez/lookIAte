package ar.edu.uai.lia.lookiate.repository.impl;

import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.repository.UserRepository;
import ar.edu.uai.lia.lookiate.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository repository;

    public UserRepositoryImpl(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

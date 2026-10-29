package ar.edu.uai.lia.lookiate.repository;

import ar.edu.uai.lia.lookiate.model.User;

public interface UserRepository extends Repository<User, String> {
    User findByEmail(String email);
}

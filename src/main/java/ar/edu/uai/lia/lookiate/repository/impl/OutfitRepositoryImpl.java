package ar.edu.uai.lia.lookiate.repository.impl;

import ar.edu.uai.lia.lookiate.model.Outfit;
import ar.edu.uai.lia.lookiate.repository.OutfitRepository;
import ar.edu.uai.lia.lookiate.repository.jpa.OutfitJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OutfitRepositoryImpl implements OutfitRepository {
    private final OutfitJpaRepository repository;

    public OutfitRepositoryImpl(OutfitJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Outfit save(Outfit entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Outfit> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Outfit> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Outfit> listFavorites() {
        return repository.findByIsFavoriteTrue();
    }

    @Override
    public List<Outfit> findByUser(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Outfit> findByOccasion(String occasion) {
        return repository.findByOccasion(occasion);
    }

    @Override
    public List<Outfit> findByVisibility(Boolean visibility) {
        return repository.findByVisibility(visibility);
    }

    @Override
    public void markFavorite(String id) {
        repository.findById(id).ifPresent(outfit -> {
            outfit.markAsFavorite();
            repository.save(outfit);
        });
    }
}

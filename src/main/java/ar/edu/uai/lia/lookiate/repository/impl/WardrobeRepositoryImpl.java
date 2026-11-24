package ar.edu.uai.lia.lookiate.repository.impl;

import ar.edu.uai.lia.lookiate.model.Wardrobe;
import ar.edu.uai.lia.lookiate.repository.WardrobeRepository;
import ar.edu.uai.lia.lookiate.repository.jpa.WardrobeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WardrobeRepositoryImpl implements WardrobeRepository {
    private final WardrobeJpaRepository repository;

    public WardrobeRepositoryImpl(WardrobeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wardrobe save(Wardrobe entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Wardrobe> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Wardrobe> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

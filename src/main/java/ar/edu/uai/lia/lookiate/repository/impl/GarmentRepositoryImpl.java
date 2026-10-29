package ar.edu.uai.lia.lookiate.repository.impl;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.jpa.GarmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GarmentRepositoryImpl implements GarmentRepository {
    private final GarmentJpaRepository repository;

    public GarmentRepositoryImpl(GarmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Garment save(Garment garment) {
        return repository.save(garment);
    }

    @Override
    public Optional<Garment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Garment> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Garment> findByWardrobe(String wardrobeId) {
        return repository.findByWardrobeId(wardrobeId);
    }

    @Override
    public List<Garment> findByTag(String tag) {
        return repository.findByTagsTagValue(tag);
    }

    @Override
    public List<Garment> findByType(String type) {
        return repository.findByTagsTagType(type);
    }

}

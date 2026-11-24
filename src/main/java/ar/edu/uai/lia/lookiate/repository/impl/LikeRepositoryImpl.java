package ar.edu.uai.lia.lookiate.repository.impl;

import ar.edu.uai.lia.lookiate.model.Like;
import ar.edu.uai.lia.lookiate.repository.LikeRepository;
import ar.edu.uai.lia.lookiate.repository.jpa.LikeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LikeRepositoryImpl implements LikeRepository {
    private final LikeJpaRepository repository;

    public LikeRepositoryImpl(LikeJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Like save(Like entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Like> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Like> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Like> findByOutfitId(String outfitId) {
        return repository.findByOutfit_OutfitId(outfitId);
    }

    @Override
    public Like findByUserIdAndOutfitId(String userId, String outfitId) {
        return repository.findByUser_UserIdAndOutfit_OutfitId(userId, outfitId);
    }
}

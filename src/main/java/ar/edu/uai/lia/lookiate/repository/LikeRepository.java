package ar.edu.uai.lia.lookiate.repository;

import ar.edu.uai.lia.lookiate.model.Like;

import java.util.List;

public interface LikeRepository extends Repository<Like, String> {
    List<Like> findByOutfitId(String outfitId);
    Like findByUserIdAndOutfitId(String userId, String outfitId);
}

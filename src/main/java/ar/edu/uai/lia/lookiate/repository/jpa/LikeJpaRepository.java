package ar.edu.uai.lia.lookiate.repository.jpa;

import ar.edu.uai.lia.lookiate.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeJpaRepository extends JpaRepository<Like, String> {
    List<Like> findByOutfit_OutfitId(String outfitId);
    Like findByUser_UserIdAndOutfit_OutfitId(String userId, String outfitId);
}

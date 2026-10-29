package ar.edu.uai.lia.lookiate.repository;

import ar.edu.uai.lia.lookiate.model.Garment;

import java.util.List;

public interface GarmentRepository extends Repository<Garment, String>{
    //persiste y recupera datos.
    List<Garment> findByWardrobe(String wardrobeId);
    List<Garment> findByTag(String tag);
    List<Garment> findByType(String type);
}

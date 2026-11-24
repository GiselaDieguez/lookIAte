-- =============================================================================
-- REFERENCIA: este archivo NO se ejecuta automáticamente contra la BD.
-- Derivado de las entidades JPA del proyecto (fuente de verdad: clases @Entity).
-- Para recrear la BD manualmente: psql -h <host> -U <user> -d lookiate_db -f schema.sql
-- Nota: Outfit.user_id no tiene FK porque la entidad no mapea la asociación User↔Outfit.
-- =============================================================================

CREATE TABLE IF NOT EXISTS users (
    user_id           VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    user_location     VARCHAR(255),
    subscription_type VARCHAR(255),
    created_at        TIMESTAMP,
    is_active         BOOLEAN
);

CREATE TABLE IF NOT EXISTS wardrobes (
    wardrobe_id VARCHAR(255) PRIMARY KEY,
    name        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS garments (
    garment_id       VARCHAR(255) PRIMARY KEY,
    garment_img      VARCHAR(255),
    created_at       TIMESTAMP,
    confidence_level FLOAT,
    wardrobe_id      VARCHAR(255),
    CONSTRAINT fk_garments_wardrobe FOREIGN KEY (wardrobe_id) REFERENCES wardrobes (wardrobe_id)
);

CREATE TABLE IF NOT EXISTS tags (
    tag_id    VARCHAR(255) PRIMARY KEY,
    tag_type  VARCHAR(255),
    tag_value VARCHAR(255),
    garment_id VARCHAR(255),
    CONSTRAINT fk_tags_garment FOREIGN KEY (garment_id) REFERENCES garments (garment_id)
);

CREATE TABLE IF NOT EXISTS outfits (
    outfit_id    VARCHAR(255) PRIMARY KEY,
    occasion     VARCHAR(255),
    outfit_style VARCHAR(255),
    weather      VARCHAR(255),
    created_at   TIMESTAMP,
    is_favorite  BOOLEAN,
    likes_count  INTEGER,
    visibility   BOOLEAN,
    user_id      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS likes (
    like_id    VARCHAR(255) PRIMARY KEY,
    created_at TIMESTAMP,
    user_id    VARCHAR(255),
    outfit_id  VARCHAR(255),
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_likes_outfit FOREIGN KEY (outfit_id) REFERENCES outfits (outfit_id)
);

CREATE TABLE IF NOT EXISTS outfit_garments (
    outfit_id  VARCHAR(255) NOT NULL,
    garment_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (outfit_id, garment_id),
    CONSTRAINT fk_og_outfit FOREIGN KEY (outfit_id) REFERENCES outfits (outfit_id),
    CONSTRAINT fk_og_garment FOREIGN KEY (garment_id) REFERENCES garments (garment_id)
);

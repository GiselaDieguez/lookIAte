-- =============================================================================
-- REFERENCIA: este archivo NO se ejecuta automáticamente contra la BD.
-- Datos de ejemplo para desarrollo. Idempotente (ON CONFLICT DO NOTHING).
-- Para cargar manualmente: psql -h <host> -U <user> -d lookiate_db -f data.sql
-- =============================================================================

INSERT INTO users (user_id, user_name, user_location, subscription_type, created_at, is_active)
VALUES ('user-0001', 'Ana Pérez', 'Buenos Aires', 'free', NOW(), TRUE)
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO wardrobes (wardrobe_id, name)
VALUES ('wardrobe-0001', 'Guardarropa principal')
ON CONFLICT (wardrobe_id) DO NOTHING;

INSERT INTO garments (garment_id, garment_img, created_at, confidence_level, wardrobe_id)
VALUES ('garment-0001', 'https://example.com/img/remera-blanca.jpg', NOW(), 0.95, 'wardrobe-0001')
ON CONFLICT (garment_id) DO NOTHING;

INSERT INTO tags (tag_id, tag_type, tag_value, garment_id)
VALUES ('tag-0001', 'type', 'remera', 'garment-0001'),
       ('tag-0002', 'color', 'blanco', 'garment-0001')
ON CONFLICT (tag_id) DO NOTHING;

INSERT INTO outfits (outfit_id, occasion, outfit_style, weather, created_at, is_favorite, likes_count, visibility, user_id)
VALUES ('outfit-0001', 'casual', 'streetwear', 'soleado', NOW(), FALSE, 0, TRUE, 'user-0001')
ON CONFLICT (outfit_id) DO NOTHING;

INSERT INTO outfit_garments (outfit_id, garment_id)
VALUES ('outfit-0001', 'garment-0001')
ON CONFLICT DO NOTHING;

INSERT INTO likes (like_id, created_at, user_id, outfit_id)
VALUES ('like-0001', NOW(), 'user-0001', 'outfit-0001')
ON CONFLICT (like_id) DO NOTHING;

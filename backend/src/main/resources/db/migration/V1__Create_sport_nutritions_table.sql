CREATE TABLE sport_nutritions (
    id BIGSERIAL PRIMARY KEY,  
    price BIGINT NOT NULL,
    name VARCHAR(255),
    category_id BIGINT NOT NULL
);
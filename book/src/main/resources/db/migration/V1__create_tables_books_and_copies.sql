CREATE TABLE tb_books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(60) NOT NULL UNIQUE,
    author VARCHAR(60) NOT NULL,
    isbn VARCHAR(45) NOT NULL UNIQUE,
    category VARCHAR(255) NOT NULL
);

CREATE TABLE tb_copies (
    id BIGSERIAL PRIMARY KEY,
    copyCode VARCHAR(45) NOT NULL UNIQUE,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    book_id BIGINT,
    CONSTRAINT fk_book
        FOREIGN KEY (book_id)
        REFERENCES tb_books(id)
        ON DELETE CASCADE
);

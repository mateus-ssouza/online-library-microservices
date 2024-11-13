
CREATE TABLE tb_loans (
    id BIGSERIAL PRIMARY KEY,
    loan_date DATE NOT NULL,
    return_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    fines NUMERIC(10, 2),
    user_id BIGINT NOT NULL,
    books_id BIGINT[] NOT NULL
);


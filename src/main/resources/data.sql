INSERT INTO customer (name) VALUES ('Srilatha');

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 120, '2026-01-10');

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 80, '2026-02-05');

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 30, '2026-01-02');   -- No points (<50)

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 60, '2026-01-10');   -- 10 points

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 110, '2026-01-15');  -- 70 points

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 180, '2026-02-05');  -- 210 points

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 250, '2026-03-10');  -- High reward


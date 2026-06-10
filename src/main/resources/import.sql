-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
INSERT INTO tb01_simulacao (coSimulacao, valor_inicial, taxa_juros_mensal, prazo_meses, valor_total_final, valor_total_juros)
VALUES ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 1000.00, 0.015, 12, 1195.62, 195.62);

INSERT INTO tb01_simulacao (coSimulacao, valor_inicial, taxa_juros_mensal, prazo_meses, valor_total_final, valor_total_juros)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf6', 5000.00, 0.020, 24, 8042.19, 3042.19);

INSERT INTO tb01_simulacao (coSimulacao, valor_inicial, taxa_juros_mensal, prazo_meses, valor_total_final, valor_total_juros)
VALUES ('6ec0bd7f-11c0-43da-975e-2a8ad9ebae0b', 15000.00, 0.012, 6, 16113.92, 1113.92);

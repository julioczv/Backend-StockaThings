/*CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE products (
                          id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          cost_price DECIMAL(10,2),
                          selling_price DECIMAL(10,2),
 stock_quantity INT,
type VARCHAR(100)
);*/
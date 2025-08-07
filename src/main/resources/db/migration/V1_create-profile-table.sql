/*CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE profile (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(40) NOT NULL
);*/
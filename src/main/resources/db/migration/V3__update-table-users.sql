ALTER TABLE usuario
    ADD COLUMN login varchar(120),
    ADD COLUMN senha varchar(120);

ALTER TABLE public.usuario
    DROP COLUMN IF EXISTS usuario_email;

ALTER TABLE usuario
    ALTER COLUMN login SET NOT NULL,
    ALTER COLUMN senha SET NOT NULL;

ALTER TABLE usuario
    ADD CONSTRAINT uq_usuario_login UNIQUE (login);

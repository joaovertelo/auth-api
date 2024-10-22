-- DDL COMMANDS

CREATE TABLE "users" (
  "id" SERIAL PRIMARY KEY,
  "username" varchar(30) UNIQUE NOT NULL,
  "email" varchar(50) NOT NULL,
  "password" varchar(255),
  "user_role" varchar(20) NOT NULL,
  "is_account_non_expired" bool NOT NULL,
  "is_account_non_locked" bool NOT NULL,
  "is_credentials_non_expired" bool NOT NULL,
  "is_enabled" bool NOT NULL,
  "created_at" timestamp NOT NULL,
  "updated_at" timestamp
);
-- DML COMMANDS

INSERT INTO public.users
(id, username, email, "password", user_role, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, created_at, updated_at)
VALUES(1, 'joao', 'joao@gmail.com', '$2a$10$JeIej0Fi6RaAyFJyl7v0H.s5QpS2uWlZQjDYFaI/3OBjFGyra89lO', 'USER', true, true, true, true, now(), now());



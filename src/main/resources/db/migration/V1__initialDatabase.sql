SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.library
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT library_pkey PRIMARY KEY (id)
);

CREATE TABLE public.book
(
    id bigint NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    author character varying(255) COLLATE pg_catalog."default" NOT NULL,
    total_amount integer NOT NULL,
    available_amount integer NOT NULL,
    library_id bigint NOT NULL,
    CONSTRAINT book_pkey PRIMARY KEY (id),
        FOREIGN KEY(library_id)
            REFERENCES public.library(id)
);

CREATE TABLE IF NOT EXISTS public.customer
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    book_id bigint,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
        FOREIGN KEY(book_id)
            REFERENCES public.book(id)
);

CREATE TABLE IF NOT EXISTS public.grade
(
    id bigint NOT NULL,
    value smallint NOT NULL,
    customer_id bigint NOT NULL,
    book_id bigint NOT NULL,
    CONSTRAINT grade_pkey PRIMARY KEY (id),
        FOREIGN KEY(book_id)
            REFERENCES public.book(id),
        FOREIGN KEY (customer_id)
            REFERENCES public.customer(id)
);
-- Table Definition
CREATE TABLE "public"."restaurants" (
    "nit" int8 NOT NULL,
    "owner_document_number" int8 NOT NULL,
    "phone" varchar(20) NOT NULL,
    "name" varchar(100) NOT NULL,
    "address" varchar(250) NOT NULL,
    "logo_url" varchar(400) NOT NULL,
    PRIMARY KEY ("nit")
);


-- Indices
CREATE UNIQUE INDEX restaurants_name_key ON public.restaurants USING btree (name);
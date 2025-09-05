-- Tabla dish_categories
CREATE TABLE "public"."dish_categories" (
    "id" int4 GENERATED ALWAYS AS IDENTITY,
    "name" varchar(100) NOT NULL,
    PRIMARY KEY ("id")
);

-- Tabla dishes
CREATE TABLE "public"."dishes" (
    "id" int4 GENERATED ALWAYS AS IDENTITY,
    "category_id" int4 NOT NULL,
    "restaurant_nit" int8 NOT NULL,
    "is_active" bool NOT NULL,
    "price" float8 NOT NULL,
    "name" varchar(100) NOT NULL,
    "image_url" varchar(400) NOT NULL,
    "description" varchar(500) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fkhjecv877f7mugty2rqdb97xox" FOREIGN KEY ("category_id") REFERENCES "public"."dish_categories"("id"),
    CONSTRAINT "fkpslsa9mci7gsfhwukb3mx7s6n" FOREIGN KEY ("restaurant_nit") REFERENCES "public"."restaurants"("nit")
);

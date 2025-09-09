INSERT INTO public.dish_categories ( name)
VALUES
  ('Entradas'),
  ('Sopas'),
  ('Ensaladas'),
  ('Platos principales'),
  ('Pastas'),
  ('Mariscos'),
  ('Parrilla'),
  ('Vegetariano'),
  ('Postres'),
  ('Bebidas');


INSERT INTO public.order_status (name)
VALUES
    ('PENDING'),
    ('IN_PREPARATION'),
    ('CANCELED'),
    ('READY'),
    ('DELIVERED');
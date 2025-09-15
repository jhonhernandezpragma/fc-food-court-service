ALTER TABLE public.order_otps
ALTER COLUMN order_id TYPE int4
USING order_id::int4;

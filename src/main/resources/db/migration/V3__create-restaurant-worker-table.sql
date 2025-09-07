CREATE TABLE public.restaurant_workers (
	restaurant_nit int8 NOT NULL,
	worker_document_number int8 NOT NULL,
	CONSTRAINT restaurant_workers_pkey PRIMARY KEY (worker_document_number)
);


-- public.restaurant_workers foreign keys

ALTER TABLE public.restaurant_workers ADD CONSTRAINT fk5gug4ex4noe8da3wabxcm50rp FOREIGN KEY (restaurant_nit) REFERENCES public.restaurants(nit);

/*==============================================PRODUCT====================================================*/

CREATE TABLE product (
	id                            serial PRIMARY KEY,
	name                          text NOT NULL UNIQUE,
	description                   text,
	price                         double precision NOT NULL CHECK(price >= 0) DEFAULT 0,
	stock                         integer NOT NULL default 0
);


CREATE OR REPLACE FUNCTION product (
	IN p_name                     text,
	IN p_description              text,
	IN p_price                    double precision
) RETURNS product AS
$$
	INSERT INTO product(name, description, price)
		VALUES (p_name, p_description, p_price)
	RETURNING *;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_search (
	IN p_name                     text DEFAULT '%'
)
RETURNS SETOF product AS 
$$
	SELECT * FROM PRODUCT WHERE name ILIKE '%' || p_name || '%';
$$ LANGUAGE sql STABLE STRICT;


CREATE OR REPLACE FUNCTION product_destroy (
	IN p_id                       integer
) RETURNS void AS 
$$
	DELETE FROM product WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_get_name (
	IN p_id                       integer
) RETURNS text AS
$$
	SELECT name FROM product WHERE id = p_id;
$$ LANGUAGE sql STABLE STRICT;


CREATE OR REPLACE FUNCTION product_set_name (
	IN p_id                       integer,
	IN p_name                     text
) RETURNS void AS
$$
	UPDATE product SET name = p_name WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_get_description (
	IN p_id                       integer
) RETURNS text AS
$$
	SELECT description FROM product WHERE id = p_id;
$$ LANGUAGE sql STABLE STRICT;


CREATE OR REPLACE FUNCTION product_set_description (
	IN p_id                       integer,
	IN p_description              text
) RETURNS void AS
$$
	UPDATE product SET description = p_description WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;



CREATE OR REPLACE FUNCTION product_get_price (
	IN p_id                       integer
) RETURNS double precision AS
$$
	SELECT price FROM product WHERE id = p_id;
$$ LANGUAGE sql STABLE STRICT;


CREATE OR REPLACE FUNCTION product_set_price (
	IN p_id                       integer,
	IN p_price                    double precision
) RETURNS void AS
$$
	UPDATE product SET price = p_price WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_get_stock (
	IN p_id                       integer
) RETURNS integer AS
$$
	SELECT stock FROM product WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_add_items_to_stock (
	IN p_id                       integer,
	IN p_quantity                 integer
) RETURNS void AS $$
BEGIN
	IF p_quantity > 0
	THEN
		UPDATE product SET stock = stock + p_quantity WHERE id = p_id;
	END IF;
END;
$$ LANGUAGE plpgsql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION product_remove_items_from_stock (
	IN p_id                       integer,
	IN p_quantity                 integer
) RETURNS void AS $$
DECLARE
	v_quantity                    integer;

BEGIN
	SELECT stock FROM product WHERE id = p_id INTO v_quantity;

	IF p_quantity > 0 AND p_quantity <= v_quantity
	THEN
		UPDATE product SET stock = stock - p_quantity WHERE id = p_id;
	END IF;
END;
$$ LANGUAGE plpgsql VOLATILE STRICT;


/*================================================PURCHASE====================================================*/

CREATE TABLE purchase (
	id                            serial PRIMARY KEY,
	timestamp                     timestamp with time zone DEFAULT now(),
	product                       integer NOT NULL REFERENCES product(id),
	quantity                      integer NOT NULL CHECK(quantity > 0)
);


CREATE OR REPLACE FUNCTION purchase_product_trg()
RETURNS trigger AS $$
BEGIN
	UPDATE product 
		SET stock = stock + NEW.quantity
	WHERE id = NEW.product;

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER purchase_product_trg AFTER INSERT ON purchase
	FOR EACH ROW EXECUTE PROCEDURE purchase_product_trg();


CREATE OR REPLACE FUNCTION destroy_purchase_trg()
RETURNS trigger AS $$
BEGIN
	UPDATE product 
		SET stock = stock - OLD.quantity
	WHERE id = OLD.product;

	RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER destroy_purchase_trg AFTER DELETE ON purchase
	FOR EACH ROW EXECUTE PROCEDURE destroy_purchase_trg();


CREATE VIEW vw_purchase AS
	SELECT 
		p.id,
		DATE(p.timestamp) AS date,
		p.timestamp::time as time,
		p.product,
		pr.name,
		pr.description,
		p.quantity
	FROM
		purchase p
		INNER JOIN product pr ON p.product = pr.id;


CREATE OR REPLACE FUNCTION purchase (
	IN p_product                  integer,
	IN p_quantity                 integer
) RETURNS integer AS 
$$
	INSERT INTO purchase(product, quantity)
		VALUES(p_product, p_quantity)
	RETURNING id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION purchase_destroy (
	IN p_id                       integer
) RETURNS void AS
$$
	DELETE FROM purchase WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;


CREATE OR REPLACE FUNCTION purchase_search (
	IN p_date_from                timestamp without time zone DEFAULT date_trunc('month', now()),
	IN p_date_to                  timestamp without time zone DEFAULT now(),
	IN p_product_name             text DEFAULT '%'
) RETURNS SETOF vw_purchase AS
$$
	SELECT * FROM vw_purchase 
		WHERE name ilike '%' || p_product_name || '%' AND date BETWEEN p_date_from AND p_date_to
	ORDER BY date;
$$ LANGUAGE sql STABLE;


CREATE OR REPLACE FUNCTION purchase_identify_by_id (
	IN p_id                       integer
) RETURNS vw_purchase AS 
$$
	SELECT * FROM vw_purchase WHERE id = p_id;
$$ LANGUAGE sql STABLE STRICT;

/*========================================SALE============================================*/

CREATE TABLE sale(
	id 							  serial PRIMARY KEY,
	timestamp 					  timestamp with time zone DEFAULT now(),
	product						  integer NOT NULL REFERENCES product(id),
	quantity					  integer NOT NULL CHECK(quantity > 0)	
);

CREATE OR REPLACE FUNCTION sale_product_trg()
RETURNS trigger AS $$
BEGIN
		UPDATE product
				SET stock = stock + NEW.quantity
		WHERE id = NEW.product;

		RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER sale_product_trg AFTER INSERT ON sale
		FOR EACH ROW EXECUTE PROCEDURE sale_product_trg();


CREATE OR REPLACE FUNCTION destroy_sale_trg()
	RETURNS trigger AS $$
BEGIN
			UPDATE product
					SET stock = stock - OLD.quantity
			WHERE id = OLD.product;

			RETURN OLD;
END
$$LANGUAGE plpgsql;

CREATE TRIGGER destroy_sale_trg AFTER DELETE ON sale
		FOR EACH ROW EXECUTE PROCEDURE destroy_sale_trg();

CREATE VIEW vw_sale AS
		SELECT 
				p.id,
				DATE(p.timestamp) AS date,
				p.timestamp::time as time,
				p.product,
				pr.name,
				pr.description,
				p.quantity
		FROM
				sale p
				INNER JOIN product pr ON p.product = pr.id;

CREATE OR REPLACE FUNCTION sale (
		IN p_product				integer,
		IN p_quantity				integer
)RETURNS integer AS 
$$
		INSERT INTO sale(product, quantity)
				VALUES(p_product, p_quantity)
		RETURNING id;
$$ LANGUAGE sql VOLATILE STRICT;

CREATE OR REPLACE FUNCTION sale_destroy(
		IN p_id					integer
) RETURNS void AS
$$
		DELETE FROM sale WHERE id = p_id;
$$ LANGUAGE sql VOLATILE STRICT;

CREATE OR REPLACE FUNCTION sale_search (
		IN p_date_from			  timestamp without time zone DEFAULT date_trunc('month', now()),
		IN p_date_to			  timestamp without time zone DEFAULT now(),
		IN p_product_name		  text DEFAULT '%'
)RETURNS SETOF vw_sale AS
$$
		SELECT * FROM vw_sale
				WHERE name ilike '%' || p_product_name || '%' AND date BETWEEN p_date_from AND p_date_to
		ORDER BY date;
$$ LANGUAGE sql STABLE;

CREATE OR REPLACE FUNCTION sale_identify_by_id (
		IN p_id					  integer
)RETURNS vw_sale AS
$$
		SELECT * FROM vw_sale WHERE id = p_id;
$$ LANGUAGE sql STABLE STRICT;


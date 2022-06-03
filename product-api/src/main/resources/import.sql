INSERT INTO supplier(id, name) VALUES(1, 'Panini Comics');
INSERT INTO supplier(id, name) VALUES(2, 'Amazon');

INSERT INTO category(id, description) VALUES(1, 'Comic Books');
INSERT INTO category(id, description) VALUES(2, 'Movies');
INSERT INTO category(id, description) VALUES(3, 'Books');

INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available) VALUES(1, 'Crise nas infinitas Terras', 1, 1, 10);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available) VALUES(2, 'Interestelar', 2, 2, 5);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available) VALUES(3, 'Matrix', 2, 2, 2);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available) VALUES(4, 'Até que nada mais importe', 2, 3, 15);



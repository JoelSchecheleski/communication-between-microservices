INSERT INTO supplier(id, name) VALUES(1001, 'Panini Comics');
INSERT INTO supplier(id, name) VALUES(1002, 'Amazon');

INSERT INTO category(id, description) VALUES(1001, 'Comic Books');
INSERT INTO category(id, description) VALUES(1002, 'Movies');
INSERT INTO category(id, description) VALUES(1003, 'Books');

INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available, created_at) VALUES(1001, 'Crise nas infinitas Terras', 1001, 1001, 25, CURRENT_TIMESTAMP);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available, created_at) VALUES(1002, 'Interestelar', 1002, 1002, 25, CURRENT_TIMESTAMP);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available, created_at) VALUES(1003, 'Matrix', 1002, 1002, 25, CURRENT_TIMESTAMP);
INSERT INTO product(id, name, fk_supplier, fk_category, quantity_available, created_at) VALUES(1004, 'Até que nada mais importe', 1002, 1003, 25, CURRENT_TIMESTAMP);



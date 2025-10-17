-- Creación y uso de la base de datos
CREATE DATABASE IF NOT EXISTS bdinventario;
USE bdinventario;

-- 1. Tabla Categoria
CREATE TABLE Categoria (
    categoryid INT PRIMARY KEY AUTO_INCREMENT,
    categoryname VARCHAR(100) NOT NULL,
    description TEXT
);

-- 2. Tabla Marca
CREATE TABLE Marca (
    brandid INT PRIMARY KEY AUTO_INCREMENT,
    marcanombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    homepage VARCHAR(255)
);

-- 3. Tabla Producto
CREATE TABLE Producto (
    productid INT PRIMARY KEY AUTO_INCREMENT,
    productname VARCHAR(150) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    estado BOOLEAN DEFAULT TRUE,
    talla VARCHAR(20),
    categoryid INT,
    brandid INT,
    FOREIGN KEY (categoryid) REFERENCES Categoria(categoryid),
    FOREIGN KEY (brandid) REFERENCES Marca(brandid)
);

-- 4. Tabla Usuario (Agregada para la seguridad)
CREATE TABLE usuario (
    idusuario INT auto_increment NOT NULL,
    nomusuario varchar(100) NULL,
    email varchar(200) NULL,
    password varchar(300) NULL,
    nombres varchar(100) NULL,
    apellidos varchar(100) NULL,
    activo BOOL NULL,
    CONSTRAINT users_pk PRIMARY KEY (idusuario)
);

-- 5. Tabla Rol (Agregada para la seguridad)
CREATE TABLE rol (
    idrol INT auto_increment NOT NULL,
    nomrol varchar(300) NULL,
    CONSTRAINT roles_pk PRIMARY KEY (idrol)
);

-- 6. Tabla de Unión Usuario_Rol (Agregada para la seguridad)
CREATE TABLE usuario_rol (
    idusuario INT NOT NULL,
    idrol INT NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY (idusuario, idrol),
    CONSTRAINT user_role_FK FOREIGN KEY (idusuario) REFERENCES usuario(idusuario),
    CONSTRAINT user_role_FK_1 FOREIGN KEY (idrol) REFERENCES rol(idrol)
);

-- Inserción de datos en Categoria
INSERT INTO Categoria (categoryname, description) VALUES
('Ropa Deportiva', 'Prendas diseñadas para actividades deportivas'),
('Ropa Casual', 'Ropa para el día a día y estilo informal'),
('Zapatillas', 'Calzado cómodo para uso diario o deportivo'),
('Zapatos de Vestir', 'Calzado formal para ocasiones especiales'),
('Pulseras', 'Accesorios de muñeca, metálicos o de tela'),
('Collares', 'Accesorios para el cuello, con o sin colgantes');

-- Inserción de datos en Marca
INSERT INTO Marca (marcanombre, email, homepage) VALUES
('Nike', 'contacto@nike.com', 'https://www.nike.com'),
('Adidas', 'soporte@adidas.com', 'https://www.adidas.com'),
('Under Armour', 'info@underarmour.com', 'https://www.underarmour.com'),
('Puma', 'ventas@puma.com', 'https://www.puma.com'),
('Umbro', 'contact@umbro.com', 'https://www.umbro.com'),
('Reebok', 'info@reebok.com', 'https://www.reebok.com'),
('New Balance', 'contact@newbalance.com', 'https://www.newbalance.com'),
('Dr. Martens', 'info@drmartens.com', 'https://www.drmartens.com'),
('Pandora', 'contact@pandora.net', 'https://www.pandora.net'),
('Tiffany & Co.', 'service@tiffany.com', 'https://www.tiffany.com');

-- Inserción de datos en Producto
INSERT INTO Producto (productname, price, stock, estado, talla, categoryid, brandid) VALUES
('Polera Deportiva Nike Dri-FIT', 89.99, 25, TRUE, 'M', 1, 1),
('Casaca Adidas Climalite', 119.90, 15, TRUE, 'L', 1, 2),
('Leggings Under Armour HeatGear', 79.50, 30, TRUE, 'S', 1, 3),
('Short Puma Training', 45.00, 50, TRUE, 'M', 1, 4),
('Camiseta Umbro Teamwear', 35.99, 20, TRUE, 'L', 1, 5),
('Polera Adidas Essentials', 69.90, 40, TRUE, 'M', 2, 2),
('Jogger Nike Club Fleece', 95.50, 10, TRUE, 'L', 2, 1),
('Hoodie Puma Lifestyle', 85.00, 12, TRUE, 'XL', 2, 4),
('Zapatillas Nike Air Max', 350.00, 18, TRUE, '42', 3, 1),
('Zapatillas Adidas Ultraboost', 399.99, 10, TRUE, '41', 3, 2),
('Zapatillas Reebok Classic', 280.00, 22, TRUE, '43', 3, 6),
('Zapatillas New Balance 574', 320.00, 15, TRUE, '40', 3, 7),
('Zapato de vestir Dr. Martens 1461', 450.00, 5, TRUE, '42', 4, 8),
('Zapato de cuero formal Umbro Style', 299.99, 7, TRUE, '41', 4, 5),
('Pulsera Pandora Moments', 250.00, 14, TRUE, 'Ajustable', 5, 9),
('Pulsera Pandora Rose Gold', 280.00, 8, TRUE, 'Pequeña', 5, 9),
('Collar Tiffany & Co. Heart Tag', 750.00, 3, TRUE, 'Único', 6, 10),
('Collar Tiffany & Co. Bean Design', 890.00, 2, TRUE, 'Único', 6, 10),
('Collar Pandora Plateado Clásico', 199.99, 6, TRUE, 'Ajustable', 6, 9);

-- Inserción de datos para la seguridad
INSERT INTO rol (nomrol) VALUES ('ADMIN'), ('USER');
INSERT INTO usuario (nomusuario, email, password, nombres, apellidos, activo) VALUES ('admin', 'admin@cibertec.pe', '$2a$10$1GvwNikEc6u49yRbjovDre4MsNcGLDPbieXpVZkmZFY9q8kRCezN2', 'Admin', 'User', 1);
INSERT INTO usuario_rol (idusuario, idrol) VALUES (1, 1);

select *from producto;
select * from usuario_rol;
select * from usuario;
select * from rol;
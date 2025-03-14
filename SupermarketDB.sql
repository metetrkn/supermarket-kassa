# ---------------------------------------------------------------------- #
# Create database if not exists           #
# ---------------------------------------------------------------------- #

CREATE DATABASE IF NOT EXISTS `A101-db`;
USE `A101-db`;

# ---------------------------------------------------------------------- #
# Create table "Products"                                               #
# ---------------------------------------------------------------------- #

CREATE TABLE IF NOT EXISTS `Products` (
    `ProductID` INT NOT NULL AUTO_INCREMENT,
    `ProductName` VARCHAR(50) NOT NULL,
    `Price` DECIMAL(10, 2) NOT NULL,
    `Category` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`ProductID`)
);

# ---------------------------------------------------------------------- #
# Insert sample data into "Products"                                    #
# ---------------------------------------------------------------------- #

# Deleting data in the table
TRUNCATE TABLE Products;

INSERT INTO Products (ProductName, Price, Category) VALUES
('Kaffe', 39.90, 'Drycker'),
('Te', 29.90, 'Drycker'),
('Yoghurt', 15.50, 'Mejeriprodukter'),
('Mjölk', 10.00, 'Mejeriprodukter'),
('Ost', 49.90, 'Mejeriprodukter'),
('Smör', 25.00, 'Mejeriprodukter'),
('Ägg', 29.90, 'Mejeriprodukter'),
('Bröd', 19.90, 'Bakverk'),
('Bullar', 29.90, 'Bakverk'),
('Kex', 22.50, 'Snacks'),
('Chips', 25.00, 'Snacks'),
('Godis', 35.00, 'Snacks'),
('Frukt', 20.00, 'Färskvaror'),
('Grönsaker', 15.00, 'Färskvaror'),
('Köttfärs', 89.90, 'Kött'),
('Kyckling', 79.90, 'Kött'),
('Fisk', 99.90, 'Fisk'),
('Skinka', 59.90, 'Kött'),
('Korv', 45.00, 'Kött'),
('Pasta', 12.90, 'Kolhydrater'),
('Ris', 14.90, 'Kolhydrater'),
('Potatis', 10.00, 'Färskvaror'),
('Sallad', 25.00, 'Färskvaror'),
('Tomater', 20.00, 'Färskvaror'),
('Lök', 10.00, 'Färskvaror'),
('Vitlök', 15.00, 'Färskvaror'),
('Kryddor', 30.00, 'Kryddor'),
('Olja', 35.00, 'Köksprodukter'),
('Vinäger', 20.00, 'Köksprodukter'),
('Socker', 10.00, 'Köksprodukter'),
('Salt', 5.00, 'Köksprodukter'),
('Peppar', 15.00, 'Köksprodukter'),
('Ketchup', 25.00, 'Såser'),
('Senap', 20.00, 'Såser'),
('Majonnäs', 30.00, 'Såser'),
('Salsa', 35.00, 'Såser'),
('Chili', 25.00, 'Såser'),
('Paj', 49.90, 'Bakverk'),
('Tårta', 79.90, 'Bakverk'),
('Glass', 39.90, 'Desserter'),
('Choklad', 25.00, 'Desserter'),
('Kakor', 30.00, 'Desserter'),
('Müsli', 29.90, 'Frukost'),
('Havregryn', 15.00, 'Frukost'),
('Cornflakes', 20.00, 'Frukost'),
('Nötter', 45.00, 'Snacks'),
('Fröer', 35.00, 'Snacks'),
('Torkad frukt', 40.00, 'Snacks'),
('Kryddmix', 25.00, 'Kryddor'),
('Balsamico', 45.00, 'Köksprodukter'),
('Kokosmjölk', 20.00, 'Köksprodukter'),
('Syrad grädde', 15.00, 'Mejeriprodukter'),
('Färskost', 35.00, 'Mejeriprodukter'),
('Keso', 25.00, 'Mejeriprodukter'),
('Syrad mjölk', 20.00, 'Mejeriprodukter'),
('Hummus', 30.00, 'Dips'),
('Guacamole', 35.00, 'Dips'),
('Salsa Verde', 25.00, 'Dips'),
('Pesto', 40.00, 'Dips'),
('Rödbetssallad', 20.00, 'Sallader'),
('Potatissallad', 25.00, 'Sallader'),
('Coleslaw', 20.00, 'Sallader'),
('Kikärtor', 15.00, 'Konserver'),
('Bönor', 15.00, 'Konserver'),
('Tomatsås', 20.00, 'Såser'),
('Kokta grönsaker', 25.00, 'Konserver'),
('Soppa', 30.00, 'Färdigrätter'),
('Lasagne', 49.90, 'Färdigrätter'),
('Pizza', 59.90, 'Färdigrätter'),
('Pasta med sås', 45.00, 'Färdigrätter'),
('Färsk pasta', 35.00, 'Färdigrätter'),
('Sushi', 79.90, 'Färdigrätter'),
('Färdigmat', 50.00, 'Färdigrätter'),
('Kryddad olja', 30.00, 'Köksprodukter'),
('Kryddad vinäger', 25.00, 'Köksprodukter'),
('Syrad grönsaker', 20.00, 'Konserver'),
('Färsk frukt', 30.00, 'Färskvaror'),
('Färska grönsaker', 25.00, 'Färskvaror'),
('Färsk fisk', 99.90, 'Fisk'),
('Färskt kött', 89.90, 'Kött'),
('Färsk kyckling', 79.90, 'Kött'),
('Färsk skinka', 59.90, 'Kött'),
('Färsk korv', 45.00, 'Kött'),
('Färsk ost', 49.90, 'Mejeriprodukter'),
('Färsk mjölk', 10.00, 'Mejeriprodukter'),
('Färsk yoghurt', 15.50, 'Mejeriprodukter'),
('Färsk grädde', 25.00, 'Mejeriprodukter'),
('Färsk smör', 30.00, 'Mejeriprodukter'),
('Färsk glass', 39.90, 'Desserter'),
('Färsk tårta', 79.90, 'Desserter'),
('Färska bullar', 29.90, 'Bakverk'),
('Färska kex', 22.50, 'Snacks'),
('Färska chips', 25.00, 'Snacks'),
('Färsk godis', 35.00, 'Snacks');
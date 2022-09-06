CREATE DATABASE IF NOT EXISTS `car_lot`;
USE `car_lot`;
DELETE FROM mysql.user WHERE user = 'carLot';
CREATE USER 'carLot'@'localhost' IDENTIFIED BY 'carLot';
GRANT ALL PRIVILEGES ON * . * TO 'carLot'@'localhost';
ALTER USER 'carLot'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin';
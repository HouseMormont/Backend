CREATE DATABASE IF NOT EXISTS mormont;
USE mormont;
DROP TABLE users;
CREATE TABLE IF NOT EXISTS `Users` (
  id int AUTO_INCREMENT ,
  username VARCHAR(50)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  departament INT NOT NULL ,
  nume VARCHAR(20) NOT NULL ,
  prenume VARCHAR(20) NOT NULL ,
  authority int NOT NULL ,


  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
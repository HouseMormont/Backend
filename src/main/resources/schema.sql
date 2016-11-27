CREATE DATABASE IF NOT EXISTS mormont;
USE mormont;
#DROP TABLE users;
CREATE TABLE IF NOT EXISTS `Users` (
  id int AUTO_INCREMENT ,
  username VARCHAR(50)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  departament INT NOT NULL ,
  nume VARCHAR(20) NOT NULL ,
  prenume VARCHAR(20) NOT NULL ,
  authority int NOT NULL ,


  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `Functii`(
  id_functie int NOT NULL ,
  denumire VARCHAR(30) NOT NULL ,

  PRIMARY KEY (id_functie)
);

CREATE TABLE IF NOT EXISTS `Facultati`(
  id_facultate int NOT NULL ,
  denumire VARCHAR(60) NOT NULL ,

  PRIMARY KEY (id_facultate)
);

CREATE TABLE IF NOT EXISTS `Departamente`(
  id_departament int NOT NULL ,
  denumire VARCHAR(60) NOT NULL ,
  facultate INT NOT NULL,

  PRIMARY KEY (id_departament),
  FOREIGN KEY (facultate) REFERENCES Facultati(id_facultate)

);

CREATE TABLE IF NOT EXISTS `Mijloace_de_transport`(
  id_mijloc int NOT NULL ,
  denumire VARCHAR(20) NOT NULL ,

  PRIMARY KEY (id_mijloc)
);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_tip_solicitant`(
  id INT NOT NULL,
  descriere VARCHAR(100) NOT NULL ,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_tip_avizare`(
  id INT NOT NULL ,
  descriere VARCHAR(50),

  PRIMARY KEY (id)

);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Flux`(
  id INT NOT NULL ,
  id_tip_solicitant INT NOT NULL ,
  id_tip_avizare INT NOT NULL ,

  PRIMARY KEY (id),
  FOREIGN KEY (id_tip_avizare) REFERENCES Dispozitia_Rectorului_tip_avizare(id),
  FOREIGN KEY (id_tip_solicitant) REFERENCES Dispozitia_Rectorului_tip_solicitant(id)
);


CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului`(

  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,

  nume VARCHAR(20) NOT NULL ,
  prenume VARCHAR(20) NOT NULL ,

  id_flux INT NOT NULL ,
  status_flux INT NOT NULL , # -1 - Rejected
                             # 0 -  Pending/Waiting
                             # 1 -  Approved


  functia int NOT NULL,
  departament int NOT NULL ,

  ruta VARCHAR(255) NOT NULL ,
  data_inceput VARCHAR(11) NOT NULL ,
  data_sfarsit VARCHAR(11) NOT NULL ,
  mijloc_transport INT NOT NULL,
  numar_telefon VARCHAR(20) NOT NULL,
  email VARCHAR(30) NOT NULL ,
  scopul VARCHAR(255) NOT NULL ,
  suma_avans FLOAT ,
  data_cererii VARCHAR(11) NOT NULL ,
  nume_director_departament VARCHAR(40) NOT NULL ,
  nume_decan VARCHAR(40) NOT NULL ,
  nume_director_proiect VARCHAR(40) NOT NULL ,
  nume_director_scoala_doctorala VARCHAR(40) ,


  PRIMARY KEY (id_dispozitie,versiune),
  FOREIGN KEY (functia) REFERENCES Functii(id_functie),
  FOREIGN KEY (departament) REFERENCES Departamente(id_departament),
  FOREIGN KEY (mijloc_transport) REFERENCES Mijloace_de_transport(id_mijloc),
  FOREIGN KEY (id_flux) REFERENCES Dispozitia_Rectorului_Flux(id)

);

CREATE TABLE IF NOT EXISTS `Valute`(
  id_valuta int NOT NULL ,
  denumire VARCHAR(20) NOT NULL ,

  PRIMARY KEY (id_valuta)

);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Transport`(

  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,

  suma_atm FLOAT ,
  valuta_atm INT ,

  suma_auto_intern FLOAT,
  valuta_auto_intern INT,

  suma_auto_extern FLOAT,
  valuta_auto_extern INT,

  suma_transport_erasmus FLOAT,
  valuta_transport_erasmus INT,
  finantare_erasmus VARCHAR(50),

  suma_transport_intern_destinatie FLOAT,
  valuta_transport_intern_destinatie INT,
  finantare_transport_intern_destinatie VARCHAR(50),



  PRIMARY KEY (id_dispozitie,versiune),
  FOREIGN KEY (valuta_atm) REFERENCES Valute(id_valuta) ,
  FOREIGN KEY (valuta_auto_extern) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_transport_erasmus) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_transport_intern_destinatie) REFERENCES Valute(id_valuta)

)
  ENGINE = InnoDB,
  DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS Dispozitia_Rectorului_diurna (
  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,

  suma_diurna FLOAT  ,
  numar_zile_diurna INT  ,
  suma_total_diurna FLOAT ,
  valuta_diurna INT  ,
  finantare_diurna VARCHAR(50),

  suma_subzistenta FLOAT  ,
  numar_zile_subzistenta INT  ,
  valuta_subzistenta INT  ,
  finantare_subzistenta VARCHAR(50),

  suma_mobilitate FLOAT  ,
  numar_luni_mobilitate INT  ,
  suma_total_mobilitate FLOAT ,
  valuta_mobilitate INT  ,
  finantare_mobilitate VARCHAR(50),


  PRIMARY KEY (id_dispozitie,versiune),
  FOREIGN KEY (valuta_diurna) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_subzistenta) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_mobilitate) REFERENCES Valute(id_valuta)


);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Cazare`(
  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,

  suma_destinatie FLOAT  ,
  numar_zile_destinatie INT  ,
  total_destinatie FLOAT,
  valuta_destinatie INT  ,
  finantare_destinatie VARCHAR(50),

  suma_calatorie FLOAT  ,
  numar_zile_calatorie INT  ,
  total_calatorie FLOAT,
  valuta_calatorie INT  ,
  finantare_calatorie VARCHAR(50),

  PRIMARY KEY (id_dispozitie,versiune),
  FOREIGN KEY (valuta_calatorie) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_destinatie) REFERENCES Valute(id_valuta)

);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Altele`(
  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,

  suma_conferinta FLOAT,
  valuta_conferinta INT,
  finantare_conferinta VARCHAR(50),

  suma_viza FLOAT,
  valuta_viza INT,
  finantare_viza VARCHAR(50),

  suma_membru FLOAT,
  valuta_membru INT,
  finantare_membru VARCHAR(50),

  suma_autostrada FLOAT,
  valuta_autostrada INT,
  finantare_autostrada VARCHAR(50),

  suma_parcare FLOAT,
  valuta_parcare INT,
  finantare_parcare VARCHAR(50),

  suma_vaccin FLOAT,
  valuta_vaccin INT,
  finantare_vaccin VARCHAR(50),

  suma_papetarie FLOAT,
  valuta_papetarie INT,
  finantare_papetarie VARCHAR(50),

  suma_asigurare FLOAT,
  valuta_asigurare INT,
  finantare_asigurare VARCHAR(50),





  PRIMARY KEY (id_dispozitie,versiune),
  FOREIGN KEY (valuta_asigurare) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_autostrada) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_conferinta) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_membru) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_papetarie) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_parcare) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_viza) REFERENCES Valute(id_valuta),
  FOREIGN KEY (valuta_vaccin) REFERENCES Valute(id_valuta)

);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Declaratie`(

  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,
  nume VARCHAR(20) NOT NULL ,
  prenume VARCHAR(20) NOT NULL ,
  destinatie VARCHAR(30) NOT NULL ,
  data_inceput VARCHAR(11) NOT NULL ,
  data_sfarsit VARCHAR(11) NOT NULL ,
  data_plecare VARCHAR(11) NOT NULL,
  data_curenta VARCHAR(11) NOT NULL,
  data_sosire VARCHAR(11) NOT NULL ,
  suma FLOAT NOT NULL ,
  suma_autoturism FLOAT ,



  PRIMARY KEY (id_dispozitie,versiune)
);

CREATE TABLE IF NOT EXISTS `Dispozitia_Rectorului_Date_Virament` (

  id_dispozitie int NOT NULL ,
  versiune FLOAT NOT NULL ,
  nume VARCHAR(20) NOT NULL ,
  prenume VARCHAR(20) NOT NULL ,
  cnp VARCHAR(20) NOT NULL ,
  domiciliu VARCHAR(50) NOT NULL ,
  nume_banca VARCHAR(20) NOT NULL ,
  iban VARCHAR(20) NOT NULL ,
  data VARCHAR(11) NOT NULL ,

  PRIMARY KEY (id_dispozitie,versiune)


);

/*CREATE TABLE IF NOT EXISTS `Referat_Necesitate`(

  id_referat int NOT NULL ,
  versiune FLOAT NOT NULL ,



  departament INT NOT NULL ,
  numar_inregistrare VARCHAR(10) NOT NULL ,




  PRIMARY KEY (id_referat,versiune),
  FOREIGN KEY (departament) REFERENCES Departamente(id_departament)


);
*/





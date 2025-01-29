CREAT TABLE contractor (
  id int NOT NULL AUTO_INCREMENT,
  address varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE sub_contractor
(
    contractor_id          int          DEFAULT NULL,
    id                     int NOT NULL AUTO_INCREMENT,
    sub_contractor_list_id int          DEFAULT NULL,
    first_name             varchar(255) DEFAULT NULL,
    last_name              varchar(255) DEFAULT NULL,
    utr                    varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY                    FKqhp0ayg565tbguobj62jsphsn (contractor_id),
    KEY                    FKhgp34640pff2wfwv1qa5yj94w (sub_contractor_list_id),
    CONSTRAINT FKhgp34640pff2wfwv1qa5yj94w FOREIGN KEY (sub_contractor_list_id) REFERENCES contractor (id),
    CONSTRAINT FKqhp0ayg565tbguobj62jsphsn FOREIGN KEY (contractor_id) REFERENCES contractor (id)
);
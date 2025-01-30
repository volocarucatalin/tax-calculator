CREAT
TABLE contractor (
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


CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY, -- Primary key
    username VARCHAR(255) NOT NULL,             -- Username field
    email    VARCHAR(255) NOT NULL,             -- Email field
    password VARCHAR(255) NOT NULL,             -- Password field
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    active   BOOLEAN      NOT NULL              -- Active status (true/false)
);

ALTER TABLE Users AUTO_INCREMENT = 1;

CREATE TABLE users_seq (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           PRIMARY KEY (id)
);

commit;


drop table  users_seq;
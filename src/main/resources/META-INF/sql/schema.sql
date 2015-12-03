drop table if exists dp_dissection_diagnose;
drop table if exists dp_description_point;
drop table if exists dp_death_story_entry;
drop table if exists dp_hospital_ward_entry;
drop table if exists dp_histopathological_examination;
drop table if exists dp_report;
drop table if exists dp_dissection_protocol;
drop table if exists session_user_search;
drop table if exists settings_dissection_diagnose_source_option;
drop table if exists settings_dissection_diagnose_source;
drop table if exists settings_description_point_source;
drop table if exists settings_hospital_ward;
drop table if exists settings_hospital;
drop table if exists settings_doctor;
drop table if exists security_user_authorities;
drop table if exists security_user_account;

create table security_user_account(
  username varchar(50) not null primary key,
  password varchar(50) not null,
  enabled boolean not null
);

create table security_user_authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  FOREIGN KEY (username) REFERENCES security_user_account(username)
);

create unique index dp_ix_auth_username on security_user_authorities (username,authority);

CREATE TABLE settings_hospital (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
	address VARCHAR(200),
	post_code VARCHAR(10),
	city VARCHAR(100),
	phone VARCHAR(50),
	image_data longblob
);

CREATE TABLE settings_hospital_ward (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  hospital_id INT NOT NULL,
  name VARCHAR(100),
  dissection_ward bool not null,
  phone VARCHAR(50),
  image_data longblob,
  FOREIGN KEY (hospital_id) REFERENCES settings_hospital(id)
);

CREATE TABLE settings_doctor (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(100) not null,
	last_name VARCHAR(100) not null,
	title VARCHAR(50),
	phone VARCHAR(50)
);

CREATE TABLE settings_description_point_source (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description TEXT,
  point INT,
  position INT,
  type VARCHAR(20) not null,
  category_adult bool not null,
  category_newborn bool not null,
  category_fetus bool not null
);

CREATE TABLE settings_dissection_diagnose_source (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description_point_source_id INT,
  latin VARCHAR(1000) not null,
  translated VARCHAR(1000) not null,
  category_adult bool not null,
  category_newborn bool not null,
  category_fetus bool not null,
  type VARCHAR(50) not null,
  FOREIGN KEY (description_point_source_id) REFERENCES settings_description_point_source(id)
);

create table settings_dissection_diagnose_source_option (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_diagnose_source_id INT NOT NULL,
  latin VARCHAR(1000) not null,
  translated VARCHAR(1000) not null,
  FOREIGN KEY (dissection_diagnose_source_id) REFERENCES settings_dissection_diagnose_source(id)
);

create table session_user_search (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  owner varchar(50) not null,
  doctor_id INT,
  hospital_id INT,
  medical_examination_date DATE,
  medical_examination_date_from DATE,
  medical_examination_date_to DATE,
  medical_examination_time_from SMALLINT,
  medical_examination_time_to SMALLINT,
  patient_last_name VARCHAR(100),
  patient_first_name VARCHAR(100),
  patient_identification_number VARCHAR(50),
  medical_examination_number VARCHAR(50),
  FOREIGN KEY (owner) REFERENCES security_user_account(username),
  FOREIGN KEY (hospital_id) REFERENCES settings_hospital(id),
  FOREIGN KEY (doctor_id) REFERENCES settings_doctor(id)
);

create table dp_dissection_protocol(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  creation_date DATETIME NOT NULL,
  category VARCHAR(20) NOT NULL,
  protocol_number VARCHAR(50) NOT NULL,
  hospital_id INT,
  autopsy_doctor_executor_id INT,
  autopsy_doctor_executor_presence VARCHAR(50),
  autopsy_date DATE,
  autopsy_time INT,
  death_story_hospital_id INT,
  death_story_book_type VARCHAR(50),
  death_story_book_number VARCHAR(50),
  patient_last_name VARCHAR(100),
  patient_first_name VARCHAR(100),
  patient_identification_number VARCHAR(50),
  patient_description VARCHAR(200),
  patient_address_value VARCHAR(200),
  patient_address_post_code VARCHAR(10),
  patient_address_city VARCHAR(100),
  patient_years_age VARCHAR(10),
  clinical_diagnosis TEXT,
  clinical_data TEXT,
  medial_practice_analysis TEXT,
  status VARCHAR(50) NOT NULL,
  FOREIGN KEY (hospital_id) REFERENCES settings_hospital(id),
  FOREIGN KEY (autopsy_doctor_executor_id) REFERENCES settings_doctor(id),
  FOREIGN KEY (death_story_hospital_id) REFERENCES settings_hospital(id)
);

create table dp_death_story_entry(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT,
  name VARCHAR(50),
  --source_name ENUM('ADMISSION', 'MOTHER_ADMISSION', 'CHILD_BIRTH', 'DEATH_CHILD_BIRTH', 'DEATH'),
  source_name VARCHAR(50),
  occur_date DATETIME,
  occur_time INT,
  description VARCHAR(200),
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id)
);

CREATE TABLE dp_description_point(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  description_point_source_id INT NOT NULL,
  customization bool not null,
  description TEXT,
  point INT,
  position INT,
  sort_index INT NOT NULL,
  type VARCHAR(20) NOT NULL,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id),
  FOREIGN KEY (description_point_source_id) REFERENCES settings_description_point_source(id)
);

CREATE TABLE dp_dissection_diagnose (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  description_point_id INT,
  dissection_diagnose_source_id INT NOT NULL,
  latin VARCHAR(1000) not null,
  translated VARCHAR(1000) not null,
  sort_index INT NOT NULL,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id),
  FOREIGN KEY (description_point_id) REFERENCES dp_description_point(id),
  FOREIGN KEY (dissection_diagnose_source_id) REFERENCES settings_dissection_diagnose_source(id)
);

create table dp_dissection_diagnose_option (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_diagnose_id INT NOT NULL,
  dissection_diagnose_source_option_id INT NOT NULL,
  latin VARCHAR(1000) not null,
  translated VARCHAR(1000) not null,
  FOREIGN KEY (dissection_diagnose_id) REFERENCES dp_dissection_diagnose(id),
  FOREIGN KEY (dissection_diagnose_source_option_id) REFERENCES settings_dissection_diagnose_source_option(id)
);

CREATE TABLE dp_hospital_ward_entry (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  hospital_ward_id INT NOT NULL,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id),
  FOREIGN KEY (hospital_ward_id) REFERENCES settings_hospital_ward(id)
);

CREATE TABLE dp_histopathological_examination (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  number VARCHAR(50),
  from_day DATE,
  description TEXT,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id)
);

CREATE TABLE dp_report (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  status VARCHAR(20),
  latin_start_page LONGBLOB,
  translated_start_page LONGBLOB,
  content_pages LONGBLOB,
  report LONGBLOB,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id)
)
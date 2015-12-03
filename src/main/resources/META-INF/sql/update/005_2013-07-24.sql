ALTER TABLE settings_dissection_diagnose_source ADD type VARCHAR(50) not null;
update settings_dissection_diagnose_source set type = 'MACROSCOPIC';
update settings_hospital set address = LTRIM(address) where address like ' %';

create table settings_dissection_diagnose_source_option (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_diagnose_source_id INT NOT NULL,
  latin VARCHAR(1000) not null,
  translated VARCHAR(1000) not null,
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
)

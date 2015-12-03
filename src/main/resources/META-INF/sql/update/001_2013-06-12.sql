drop table if exists dp_histopathological_examination;
drop table if exists dp_report;

CREATE TABLE dp_report (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  status VARCHAR(20),
  latin_start_page MEDIUMBLOB,
  translated_start_page MEDIUMBLOB,
  content_pages MEDIUMBLOB,
  report LONGBLOB,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id)
);

insert into dp_report (dissection_protocol_id, status) select id, 'NEED_ALL' from dp_dissection_protocol;

CREATE TABLE dp_histopathological_examination (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dissection_protocol_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  number VARCHAR(50),
  from_day DATE,
  description TEXT,
  FOREIGN KEY (dissection_protocol_id) REFERENCES dp_dissection_protocol(id)
);

insert into dp_histopathological_examination (dissection_protocol_id, description, name) select id, histopathological_examination, 'NORMAL' from dp_dissection_protocol;

alter table dp_dissection_protocol drop histopathological_examination;

ALTER TABLE dp_dissection_protocol ADD death_story_book_type VARCHAR(50) AFTER death_story_hospital_id;
update dp_dissection_protocol set death_story_book_type = 'MAIN';

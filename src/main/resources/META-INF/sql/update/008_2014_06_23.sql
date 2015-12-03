ALTER TABLE dp_report MODIFY latin_start_page LONGBLOB;
ALTER TABLE dp_report MODIFY translated_start_page LONGBLOB;
ALTER TABLE dp_report MODIFY content_pages LONGBLOB;
ALTER TABLE dp_report MODIFY report LONGBLOB;
ALTER TABLE settings_hospital_ward MODIFY image_data LONGBLOB;
ALTER TABLE settings_hospital MODIFY image_data LONGBLOB;

update dp_death_story_entry set source_name = 'MOTHER_ADMISSION' where source_name = 'ADMISSION' and dissection_protocol_id in (select id from dp_dissection_protocol where category = 'NEWBORN');
update dp_death_story_entry set source_name = 'ADMISSION' where source_name = 'MOTHER_ADMISSION' and occur_date is null and dissection_protocol_id in (select id from dp_dissection_protocol where category = 'NEWBORN');

ALTER TABLE settings_dissection_diagnose_source_option ADD sort_index INT NOT NULL;
ALTER TABLE dp_dissection_diagnose_option ADD sort_index INT NOT NULL;
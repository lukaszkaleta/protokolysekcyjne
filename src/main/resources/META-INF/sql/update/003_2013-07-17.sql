ALTER TABLE dp_dissection_diagnose ADD sort_index INT NOT NULL;
update dp_dissection_diagnose set sort_index = id;

ALTER TABLE dp_dissection_protocol ADD status VARCHAR(50);
update dp_dissection_protocol set status = 'WORK_IN_PROGRESS';
ALTER TABLE dp_dissection_protocol MODIFY status VARCHAR(50) NOT NULL;

insert into security_user_authorities (username, authority) select username, 'USER' from security_user_account;
insert into security_user_authorities (username, authority) values ('AndrzejGuzik', 'ADMIN');
insert into security_user_authorities (username, authority) values ('LukaszKaleta', 'ADMIN');

INSERT INTO auth_user_status (status_name) VALUES ( 'ACTIVE');
INSERT INTO auth_user_status (status_name) VALUES ( 'INACTIVE');
INSERT INTO auth_user_status (status_name) VALUES ( 'DELETED');

INSERT INTO auth_role (role_name) VALUES ( 'OFFICER');
INSERT INTO auth_role (role_name) VALUES ( 'ADMIN');
INSERT INTO auth_role (role_name) VALUES ( 'USER');

INSERT INTO auth_permission (permission_name) VALUES ( 'READ');
INSERT INTO auth_permission (permission_name) VALUES ( 'WRITE');
INSERT INTO auth_permission (permission_name) VALUES ( 'CREATE');
INSERT INTO auth_permission (permission_name) VALUES ( 'DELETE');

INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 1,1);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 1,2);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 1,3);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 2,1);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 2,2);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 2,3);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 2,4);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 3,1);
INSERT INTO auth_permission_role (role_id,permission_id) VALUES ( 3,2);

INSERT INTO auth_user (user_status_id,username,email,external_id,name) VALUES (1,'isuru','isuru@gmail.com',1,'isuru');
INSERT INTO auth_user_role (role_id,user_id) VALUES (1,1);

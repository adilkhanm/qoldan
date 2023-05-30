insert into address (id) values
(1);

insert into _user (id, email, firstname, lastname, mobile, password, address_id) values
(1, 'adilkhan.muratov@gmail.com', 'Adilkhan', 'Muratov', '87719029842', '$2a$10$ww7HRokETS6WDXk2lJ.2UO2kI4WPNTZD73jASKsxn/d06benxngUe', 1);

insert into _role (id, name) values
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

insert into users_roles (user_id, role_id) values
(1, 2);
GRANT ALL PRIVILEGES ON DATABASE odedb TO postgres;
CREATE TABLE credential(credential_id BIGSERIAL PRIMARY KEY, username VARCHAR(255) NOT NULL,password VARCHAR(255) NOT NULL);
CREATE TABLE manager(manager_id BIGSERIAL PRIMARY KEY,employee_name VARCHAR(255) NOT NULL,credential_id BIGINT REFERENCES credential (credential_id) ON DELETE CASCADE NOT NULL );
CREATE TABLE associate(employee_id BIGSERIAL PRIMARY KEY,employee_name VARCHAR(255) NOT NULL,credential_id BIGINT REFERENCES credential (credential_id) ON DELETE CASCADE NOT NULL,manager_id BIGINT REFERENCES manager (manager_id) NOT NULL);
CREATE TABLE task(task_id BIGSERIAL PRIMARY KEY,employee_task VARCHAR(255),employee_date_from VARCHAR(255),employee_hours_spent double precision,employee_id BIGINT REFERENCES associate (employee_id) ON DELETE CASCADE);
ALTER TABLE credential ADD COLUMN employee_id BIGINT REFERENCES associate (employee_id) ON DELETE CASCADE;
ALTER TABLE credential ADD COLUMN manager_id BIGINT REFERENCES manager (manager_id) ON DELETE CASCADE;
INSERT INTO credential (credential_id, username, password, employee_id, manager_id)
VALUES (1, 'johndoe', 'password123', NULL, NULL),
       (2, 'janedoe', 'password456', NULL, NULL),
       (3, 'alicesmith', 'password789', NULL, NULL),
       (4, 'bobsmith', 'password101112', NULL, NULL),
       (5, 'mikejohnson', 'password131415', NULL, NULL),
       (6, 'ashleyjohnson', 'password161718', NULL, NULL),
       (7, 'sarahwilliams', 'password19202122', NULL, NULL),
       (8, 'mattwilliams', 'password232425', NULL, NULL),
       (9, 'davidsmith', 'password262728', NULL, NULL),
       (10, 'emmawhite', 'password29303132', NULL, NULL),
       (11, 'chrisbrown', 'password333334', NULL, NULL),
       (12, 'katiebrown', 'password353637', NULL, NULL),
       (13, 'jamesdoe', 'password383940', NULL, NULL),
       (14, 'racheladams', 'password414243', NULL, NULL);
INSERT INTO manager (manager_id, employee_name, credential_id)
VALUES (1, 'John Doe', 1),
       (2, 'Jane Doe', 2),
       (3, 'Alice Smith', 3),
       (4, 'Bob Smith', 4),
       (5, 'mike johnson', 5);
INSERT INTO associate (employee_id, employee_name, credential_id, manager_id)
VALUES (1, 'ashley johnson', 6, 1),
       (2, 'sarah williams', 7, 2),
       (3, 'matt williams', 8, 3),
       (4, 'david smith', 9, 2),
       (5, 'emma white', 10, 3),
       (6, 'chris brown ', 11, 4),
       (7, 'katie brown', 12, 5),
       (8, 'james doe', 13, 4),
       (9, 'rachel adams', 14, 5);
UPDATE credential SET manager_id = 1 WHERE credential_id = 1;
UPDATE credential SET manager_id = 2 WHERE credential_id = 2;
UPDATE credential SET manager_id = 3 WHERE credential_id = 3;
UPDATE credential SET manager_id = 4 WHERE credential_id = 4;
UPDATE credential SET manager_id = 5 WHERE credential_id = 5;
UPDATE credential SET employee_id = 1 WHERE credential_id = 6;
UPDATE credential SET employee_id = 2 WHERE credential_id = 7;
UPDATE credential SET employee_id = 3 WHERE credential_id = 8;
UPDATE credential SET employee_id = 4 WHERE credential_id = 9;
UPDATE credential SET employee_id = 5 WHERE credential_id = 10;
UPDATE credential SET employee_id = 6 WHERE credential_id = 11;
UPDATE credential SET employee_id = 7 WHERE credential_id = 12;
UPDATE credential SET employee_id = 8 WHERE credential_id = 13;
UPDATE credential SET employee_id = 9 WHERE credential_id = 14;











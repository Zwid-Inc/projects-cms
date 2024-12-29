
-- users
INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("anna.kozak@pbs.edu.pl",111222,"Kozak","Anna","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("anna.dymana@pbs.edu.pl",222333,"Dymana","Anna","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("admin.test@pbs.edu.pl",333444,"Test","Admin","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("secondadmin.test@pbs.edu.pl",444555,"Test","Second admin","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("john.doe@pbs.edu.pl",555666,"Doe","John","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("jan.kozaczek@pbs.edu.pl",666777,"Kozaczek","Jan","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("geralt.bialy@pbs.edu.pl",777888,"Bialy","Geralt","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("jacek.nowak@pbs.edu.pl",888999,"Nowak","Jacek","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("genowefa.kowalska@pbs.edu.pl",112223,"Kowalska","Genowefa","test123");

INSERT INTO user (email,index_nr,last_name,name,password)
VALUES ("maria.wesolowska@pbs.edu.pl",113224,"Wesolowska","Maria","test123");


-- user_roles
INSERT INTO user_roles (user_id,roles) VALUES (1,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (2,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (3,"ADMIN");
INSERT INTO user_roles (user_id,roles) VALUES (4,"ADMIN");
INSERT INTO user_roles (user_id,roles) VALUES (5,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (6,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (7,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (8,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (9,"USER");
INSERT INTO user_roles (user_id,roles) VALUES (10,"USER");

-- projects

INSERT INTO project (id,project_name,project_description,
                     project_owner_id,creation_time,release_date)
VALUES (1,"Project 1 name","Project 1 description",1,"2024-11-01T15:00:00","2024-11-02T17:00:00");

INSERT INTO project (id,project_name,project_description,
                     project_owner_id,creation_time,release_date)
VALUES (2,"Project 2 name","Project 2 description",2,"2024-11-03T15:00:00","2024-11-04T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (3, "Project 3 name", "Project 3 description", 1, "2024-11-05T15:00:00", "2024-11-06T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (4, "Project 4 name", "Project 4 description", 2, "2024-11-07T15:00:00", "2024-11-08T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (5, "Project 5 name", "Project 5 description", 3, "2024-11-09T15:00:00", "2024-11-10T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (6, "Project 6 name", "Project 6 description", 4, "2024-11-11T15:00:00", "2024-11-12T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (7, "Project 7 name", "Project 7 description", 3, "2024-11-13T15:00:00", "2024-11-14T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (8, "Project 8 name", "Project 8 description", 4, "2024-11-15T15:00:00", "2024-11-16T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (9, "Project 9 name", "Project 9 description", 1, "2024-11-17T15:00:00", "2024-11-18T17:00:00");

INSERT INTO project (id, project_name, project_description, project_owner_id, creation_time, release_date)
VALUES (10, "Project 10 name", "Project 10 description", 2, "2024-11-19T15:00:00", "2024-11-20T17:00:00");




-- tasks

-- Tasks for Project 1
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (1, 1, 1, "Task 1.1", "Description for Task 1.1", "2024-11-01T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (2, 1, 2, "Task 1.2", "Description for Task 1.2", "2024-11-01T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (3, 1, 3, "Task 1.3", "Description for Task 1.3", "2024-11-01T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (4, 1, 4, "Task 1.4", "Description for Task 1.4", "2024-11-01T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (5, 1, 5, "Task 1.5", "Description for Task 1.5", "2024-11-01T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (6, 1, 6, "Task 1.6", "Description for Task 1.6", "2024-11-01T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (7, 1, 7, "Task 1.7", "Description for Task 1.7", "2024-11-01T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (8, 1, 8, "Task 1.8", "Description for Task 1.8", "2024-11-01T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (9, 1, 9, "Task 1.9", "Description for Task 1.9", "2024-11-01T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (10, 1, 10, "Task 1.10", "Description for Task 1.10", "2024-11-02T00:00:00");



-- Tasks for Project 2
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (11, 2, 1, "Task 2.1", "Description for Task 2.1", "2024-11-01T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (12, 2, 2, "Task 2.2", "Description for Task 2.2", "2024-11-01T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (13, 2, 3, "Task 2.3", "Description for Task 2.3", "2024-11-01T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (14, 2, 4, "Task 2.4", "Description for Task 2.4", "2024-11-01T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (15, 2, 5, "Task 2.5", "Description for Task 2.5", "2024-11-01T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (16, 1, 6, "Task 2.6", "Description for Task 2.6", "2024-11-01T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (17, 1, 7, "Task 2.7", "Description for Task 2.7", "2024-11-01T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (18, 1, 8, "Task 2.8", "Description for Task 2.8", "2024-11-01T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (19, 1, 9, "Task 2.9", "Description for Task 2.9", "2024-11-01T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (20, 1, 10, "Task 2.10", "Description for Task 2.10", "2024-11-02T00:00:00");

-- Tasks for Project 3
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (21, 3, 1, "Task 3.1", "Description for Task 3.1", "2024-11-05T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (22, 3, 2, "Task 3.2", "Description for Task 3.2", "2024-11-05T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (23, 3, 3, "Task 3.3", "Description for Task 3.3", "2024-11-05T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (24, 3, 4, "Task 3.4", "Description for Task 3.4", "2024-11-05T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (25, 3, 5, "Task 3.5", "Description for Task 3.5", "2024-11-05T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (26, 3, 6, "Task 3.6", "Description for Task 3.6", "2024-11-05T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (27, 3, 7, "Task 3.7", "Description for Task 3.7", "2024-11-05T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (28, 3, 8, "Task 3.8", "Description for Task 3.8", "2024-11-05T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (29, 3, 9, "Task 3.9", "Description for Task 3.9", "2024-11-05T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (30, 3, 10, "Task 3.10", "Description for Task 3.10", "2024-11-06T00:00:00");


-- Tasks for Project 4
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (31, 4, 1, "Task 4.1", "Description for Task 4.1", "2024-11-07T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (32, 4, 2, "Task 4.2", "Description for Task 4.2", "2024-11-07T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (33, 4, 3, "Task 4.3", "Description for Task 4.3", "2024-11-07T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (34, 4, 4, "Task 4.4", "Description for Task 4.4", "2024-11-07T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (35, 4, 5, "Task 4.5", "Description for Task 4.5", "2024-11-07T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (36, 4, 6, "Task 4.6", "Description for Task 4.6", "2024-11-07T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (37, 4, 7, "Task 4.7", "Description for Task 4.7", "2024-11-07T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (38, 4, 8, "Task 4.8", "Description for Task 4.8", "2024-11-07T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (39, 4, 9, "Task 4.9", "Description for Task 4.9", "2024-11-07T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (40, 4, 10, "Task 4.10", "Description for Task 4.10", "2024-11-08T00:00:00");

-- Tasks for Project 5
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (41, 5, 1, "Task 5.1", "Description for Task 5.1", "2024-11-09T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (42, 5, 2, "Task 5.2", "Description for Task 5.2", "2024-11-09T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (43, 5, 3, "Task 5.3", "Description for Task 5.3", "2024-11-09T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (44, 5, 4, "Task 5.4", "Description for Task 5.4", "2024-11-09T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (45, 5, 5, "Task 5.5", "Description for Task 5.5", "2024-11-09T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (46, 5, 6, "Task 5.6", "Description for Task 5.6", "2024-11-09T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (47, 5, 7, "Task 5.7", "Description for Task 5.7", "2024-11-09T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (48, 5, 8, "Task 5.8", "Description for Task 5.8", "2024-11-09T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (49, 5, 9, "Task 5.9", "Description for Task 5.9", "2024-11-09T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (50, 5, 10, "Task 5.10", "Description for Task 5.10", "2024-11-10T00:00:00");

-- Tasks for Project 6
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (51, 6, 1, "Task 6.1", "Description for Task 6.1", "2024-11-11T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (52, 6, 2, "Task 6.2", "Description for Task 6.2", "2024-11-11T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (53, 6, 3, "Task 6.3", "Description for Task 6.3", "2024-11-11T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (54, 6, 4, "Task 6.4", "Description for Task 6.4", "2024-11-11T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (55, 6, 5, "Task 6.5", "Description for Task 6.5", "2024-11-11T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (56, 6, 6, "Task 6.6", "Description for Task 6.6", "2024-11-11T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (57, 6, 7, "Task 6.7", "Description for Task 6.7", "2024-11-11T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (58, 6, 8, "Task 6.8", "Description for Task 6.8", "2024-11-11T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (59, 6, 9, "Task 6.9", "Description for Task 6.9", "2024-11-11T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (60, 6, 10, "Task 6.10", "Description for Task 6.10", "2024-11-12T00:00:00");

-- Tasks for Project 7
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (61, 7, 1, "Task 7.1", "Description for Task 7.1", "2024-11-13T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (62, 7, 2, "Task 7.2", "Description for Task 7.2", "2024-11-13T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (63, 7, 3, "Task 7.3", "Description for Task 7.3", "2024-11-13T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (64, 7, 4, "Task 7.4", "Description for Task 7.4", "2024-11-13T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (65, 7, 5, "Task 7.5", "Description for Task 7.5", "2024-11-13T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (66, 7, 6, "Task 7.6", "Description for Task 7.6", "2024-11-13T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (67, 7, 7, "Task 7.7", "Description for Task 7.7", "2024-11-13T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (68, 7, 8, "Task 7.8", "Description for Task 7.8", "2024-11-13T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (69, 7, 9, "Task 7.9", "Description for Task 7.9", "2024-11-13T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (70, 7, 10, "Task 7.10", "Description for Task 7.10", "2024-11-14T00:00:00");

-- Tasks for Project 8
INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (71, 8, 1, "Task 8.1", "Description for Task 8.1", "2024-11-15T15:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (72, 8, 2, "Task 8.2", "Description for Task 8.2", "2024-11-15T16:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (73, 8, 3, "Task 8.3", "Description for Task 8.3", "2024-11-15T17:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (74, 8, 4, "Task 8.4", "Description for Task 8.4", "2024-11-15T18:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (75, 8, 5, "Task 8.5", "Description for Task 8.5", "2024-11-15T19:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (76, 8, 6, "Task 8.6", "Description for Task 8.6", "2024-11-15T20:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (77, 8, 7, "Task 8.7", "Description for Task 8.7", "2024-11-15T21:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (78, 8, 8, "Task 8.8", "Description for Task 8.8", "2024-11-15T22:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (79, 8, 9, "Task 8.9", "Description for Task 8.9", "2024-11-15T23:00:00");

INSERT INTO task (id, project_id, sequence_nr, task_name, description, creation_date_time)
VALUES (80, 8, 10, "Task 8.10", "Description for Task 8.10", "2024-11-16T00:00:00");

--- project_project_maintainers


-- Project 1 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (1, 1), (1, 2), (1, 3);  -- Project 1 owner (user 1) and other maintainers

-- Project 2 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (2, 2), (2, 3), (2, 4);  -- Project 2 owner (user 2) and other maintainers

-- Project 3 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (3, 1), (3, 4), (3, 5);  -- Project 3 owner (user 1) and other maintainers

-- Project 4 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (4, 2), (4, 5), (4, 6);  -- Project 4 owner (user 2) and other maintainers

-- Project 5 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (5, 3), (5, 6), (5, 7);  -- Project 5 owner (user 3) and other maintainers

-- Project 6 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (6, 4), (6, 7), (6, 8);  -- Project 6 owner (user 4) and other maintainers

-- Project 7 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (7, 3), (7, 8), (7, 5);  -- Project 7 owner (user 3) and other maintainers

-- Project 8 Maintainers
INSERT INTO project_project_maintainers (project_id, project_maintainers_id)
VALUES (8, 4), (8, 6), (8, 7);  -- Project 8 owner (user 4) and other maintainers


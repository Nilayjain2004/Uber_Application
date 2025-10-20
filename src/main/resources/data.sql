-- ======================================
-- 1. Insert Users
-- ======================================
INSERT INTO app_user ( name, email, password) VALUES
('Aarav Mehta', 'aarav.mehta@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Ishita Sharma', 'ishita.sharma@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Rohan Gupta', 'rohan.gupta@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Ananya Patel', 'ananya.patel@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Vihaan Kapoor', 'vihaan.kapoor@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Kriti Verma', 'kriti.verma@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Arjun Singh', 'arjun.singh@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Neha Jain', 'neha.jain@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Dev Khanna', 'dev.khanna@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Simran Kaur', 'simran.kaur@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Kabir Malhotra', 'kabir.malhotra@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Sanya Bansal', 'sanya.bansal@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Aditya Joshi', 'aditya.joshi@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Meera Reddy', 'meera.reddy@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Yash Agarwal', 'yash.agarwal@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Tanya Choudhary', 'tanya.choudhary@example.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu'),
('Rahul Deshmukh', 'rahul.deshmukh@example.com', 'password123'),
('Priya Nair', 'priya.nair@example.com', 'password123'),
('Manav Saxena', 'manav.saxena@example.com', 'password123'),
('Shruti Kulkarni', 'shruti.kulkarni@example.com', 'password123'),
('Nikhil Bhatia', 'nikhil.bhatia@example.com', 'password123'),
('Pooja Sethi', 'pooja.sethi@example.com', 'password123'),
('Harsh Vora', 'harsh.vora@example.com', 'password123'),
('Avni Mishra', 'avni.mishra@example.com', 'password123'),
('Sahil Mathur', 'sahil.mathur@example.com', 'password123'),
('Ritika Anand', 'ritika.anand@example.com', 'password123'),
('Vikram Chawla', 'vikram.chawla@example.com', 'password123'),
('Anjali Saxena', 'anjali.saxena@example.com', 'password123'),
('Karan Oberoi', 'karan.oberoi@example.com', 'password123'),
('Swati Kapoor', 'swati.kapoor@example.com', 'password123'),
('Nilay Jain', 'nilayjain@gamil.com', '$2a$10$1BRZyo7aec3yOxmlQUTY7OJoC4NP2tW2nwpPCiWxiHjJIdsqVnRVu');

-- ======================================
-- 2. Insert User Roles
-- ======================================
INSERT INTO user_roles (user_id, roles) VALUES
(1, 'RIDER'),
(2, 'RIDER'), (2, 'DRIVER'),
(3, 'DRIVER'), (3, 'RIDER'),
(4, 'DRIVER'), (4, 'RIDER'),
(5, 'DRIVER'), (5, 'RIDER'),
(6, 'DRIVER'), (6, 'RIDER'),
(7, 'DRIVER'), (7, 'RIDER'),
(8, 'DRIVER'), (8, 'RIDER'),
(9, 'DRIVER'), (9, 'RIDER'),
(10, 'DRIVER'), (10, 'RIDER'),
(11, 'DRIVER'), (11, 'RIDER'),
(12, 'DRIVER'), (12, 'RIDER'),
(13, 'DRIVER'), (13, 'RIDER'),
(14, 'DRIVER'), (14, 'RIDER'),
(15, 'DRIVER'), (15, 'RIDER'),
(16, 'DRIVER'), (16, 'RIDER'),
(17, 'DRIVER'), (17, 'RIDER'),
(18, 'DRIVER'), (18, 'RIDER'),
(19, 'DRIVER'), (19, 'RIDER'),
(20, 'DRIVER'), (20, 'RIDER'),
(21, 'DRIVER'), (21, 'RIDER'),
(22, 'DRIVER'), (22, 'RIDER'),
(23, 'DRIVER'), (23, 'RIDER'),
(24, 'DRIVER'), (24, 'RIDER'),
(25, 'DRIVER'), (25, 'RIDER'),
(26, 'DRIVER'), (26, 'RIDER'),
(27, 'DRIVER'), (27, 'RIDER'),
(28, 'DRIVER'), (28, 'RIDER'),
(29, 'DRIVER'), (29, 'RIDER'),
(30, 'DRIVER'), (30, 'RIDER'),
(31, 'DRIVER'), (31, 'RIDER'),(31,'ADMIN');

-- ======================================
-- 3. Insert Rider
-- ======================================
INSERT INTO rider ( user_id, rating) VALUES
(1, 4.9);

-- ======================================
-- 4. Insert Drivers
-- ======================================
INSERT INTO driver ( user_id, rating, available, current_location) VALUES
(2, 4.7, TRUE, ST_GeomFromText('POINT(75.8400 22.7220)', 4326)),
(3, 4.2, TRUE, ST_GeomFromText('POINT(75.8600 22.7220)', 4326)),
(4, 4.9, FALSE, ST_GeomFromText('POINT(75.8650 22.7250)', 4326)),
(5, 4.3, TRUE, ST_GeomFromText('POINT(75.8500 22.7180)', 4326)),
(6, 4.6, TRUE, ST_GeomFromText('POINT(75.8580 22.7200)', 4326)),
(7, 4.1, TRUE, ST_GeomFromText('POINT(75.8620 22.7190)', 4326)),
(8, 4.5, TRUE, ST_GeomFromText('POINT(75.8590 22.7230)', 4326)),
(9, 4.8, TRUE, ST_GeomFromText('POINT(75.8560 22.7170)', 4326)),
(10, 4.4, FALSE, ST_GeomFromText('POINT(75.8575 22.7215)', 4326)),
(11, 4.9, TRUE, ST_GeomFromText('POINT(75.8610 22.7165)', 4326)),
(12, 4.2, TRUE, ST_GeomFromText('POINT(75.8530 22.7240)', 4326)),
(13, 4.3, FALSE, ST_GeomFromText('POINT(75.8570 22.7195)', 4326)),
(14, 4.6, TRUE, ST_GeomFromText('POINT(75.8605 22.7225)', 4326)),
(15, 4.7, TRUE, ST_GeomFromText('POINT(75.8555 22.7185)', 4326)),
(16, 4.1, FALSE, ST_GeomFromText('POINT(75.8625 22.7210)', 4326)),
(17, 4.8, TRUE, ST_GeomFromText('POINT(75.8585 22.7205)', 4326)),
(18, 4.4, TRUE, ST_GeomFromText('POINT(75.8545 22.7175)', 4326)),
(19, 4.5, FALSE, ST_GeomFromText('POINT(75.8595 22.7235)', 4326)),
(20, 4.2, TRUE, ST_GeomFromText('POINT(75.8565 22.7160)', 4326)),
(21, 4.9, TRUE, ST_GeomFromText('POINT(75.8615 22.7250)', 4326)),
(22, 4.3, TRUE, ST_GeomFromText('POINT(75.8535 22.7190)', 4326)),
(23, 4.6, FALSE, ST_GeomFromText('POINT(75.8570 22.7220)', 4326)),
(24, 4.8, TRUE, ST_GeomFromText('POINT(75.8600 22.7180)', 4326)),
(25, 4.7, TRUE, ST_GeomFromText('POINT(75.8550 22.7210)', 4326)),
(26, 4.2, FALSE, ST_GeomFromText('POINT(75.8590 22.7195)', 4326)),
(27, 4.5, TRUE, ST_GeomFromText('POINT(75.8570 22.7165)', 4326)),
(28, 4.4, TRUE, ST_GeomFromText('POINT(75.8610 22.7220)', 4326)),
(29, 4.9, FALSE, ST_GeomFromText('POINT(75.8540 22.7205)', 4326)),
(30, 4.6, TRUE, ST_GeomFromText('POINT(75.8580 22.7240)', 4326)),
(31, 4.3, TRUE, ST_GeomFromText('POINT(75.8560 22.7190)', 4326));



INSERT INTO wallet ( user_id, balance) VALUES
( 1, 100),
( 2, 500);
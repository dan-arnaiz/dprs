-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2024 at 07:00 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

START TRANSACTION;

SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */
;


/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */
;


/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */
;


/*!40101 SET NAMES utf8mb4 */
;

--
-- Database: `dprs`
--
-- --------------------------------------------------------
--
-- Table structure for table `patientrecords`
--
CREATE TABLE `patientrecords` (
    `id` int(10) NOT NULL,
    `given_name` varchar(20) NOT NULL,
    `last_name` varchar(20) NOT NULL,
    `middle_name` varchar(20) NOT NULL,
    `age` int(3) NOT NULL,
    `civil_status` varchar(10) NOT NULL,
    `occupation` varchar(20) NOT NULL,
    `contact_no` varchar(255) NOT NULL,
    `address` varchar(99) NOT NULL) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `patientrecords`
--
INSERT INTO `patientrecords` (`id`, `given_name`, `last_name`, `middle_name`, `age`, `civil_status`, `occupation`, `contact_no`, `address`)
    VALUES (1, 'Dan', 'Luayon', 'Arnaiz', 21, 'Single', 'Student', '09513872624', 'Mapua Malayan Colleges Mindanao'),
    (2, 'Marki', 'Querequincia', 'II', 21, 'Single', 'Student', '09123456789', 'Mapua Malayan Colleges Mindanao'),
    (3, 'Karyll Grace', 'K.', 'Bontuyan', 20, 'Single', 'Student', '09876543213', 'Mapua Malayan Colleges Mindanao');

-- --------------------------------------------------------
--
-- Table structure for table `patientvisit`
--
CREATE TABLE `patientvisit` (
    `visit_id` int(255) NOT NULL,
    `patient_id` int(255) NOT NULL,
    `reason` varchar(255) NOT NULL,
    `price` varchar(255) NOT NULL,
    `date` varchar(255) NOT NULL) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

--
-- Dumping data for table `patientvisit`
--
INSERT INTO `patientvisit` (`visit_id`, `patient_id`, `reason`, `price`, `date`)
    VALUES (2, 41, 'Sakit ngipon kaayo', '600', '2024-06-09'),
    (3, 41, 'Teeth sakit hehe', '5000', '2024-11-11'),
    (4, 41, 'Teeth sakit hehe', '5000', '2024-11-11'),
    (5, 41, 'Ngiponnnn', '25000', '2024-08-08'),
    (8, 125, 'Brace for impact', '2500', '2024-03-03'),
    (13, 28, 'Sakit ngipon', '10000', '2024-05-05'),
    (16, 155, 'Ngipon sakit kaayo', '2500', '2024-05-05'),
    (18, 157, 'Sakit', '2500', '2024-05-09'),
    (19, 158, 'Sakit', '1900', '2024-12-12'),
    (20, 29, 'dasdasd', '122', '2024-06-06'),
    (22, 125, 'Sakit nasad ngipon', '2500', '2024-10-03'),
    (23, 1, 'Idk', '250', '3/3/2024'),
    (24, 1, 'help', '999', '3-10-2024'),
    (31, 1, 'Toothache', '2000', '2024-03-12'),
    (33, 3, 'Dental Checkup', '1000', '2024-12-09'),
    (38, 3, 'Toothache', '700', '2024-03-12');

-- --------------------------------------------------------
--
-- Table structure for table `usersdata`
--
CREATE TABLE `usersdata` (
    `usersID` int(255) NOT NULL,
    `accessLevel` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL) ENGINE = InnoDB DEFAULT CHARSET = latin1 COLLATE = latin1_swedish_ci;

--
-- Dumping data for table `usersdata`
--
INSERT INTO `usersdata` (`usersID`, `accessLevel`, `username`, `password`)
    VALUES (1, 'Admin', 'MarkII', 'password'),
    (2, 'Admin', 'Joe123', 'password'),
    (3, 'Admin', 'MaeBee', '12345'),
    (6, 'Admin', 'danarnaiz', 'test');

--
-- Indexes for dumped tables
--
--
-- Indexes for table `patientrecords`
--
ALTER TABLE `patientrecords`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `patientvisit`
--
ALTER TABLE `patientvisit`
    ADD PRIMARY KEY (`visit_id`);

--
-- Indexes for table `usersdata`
--
ALTER TABLE `usersdata`
    ADD PRIMARY KEY (`usersID`);

--
-- AUTO_INCREMENT for dumped tables
--
--
-- AUTO_INCREMENT for table `patientrecords`
--
ALTER TABLE `patientrecords` MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT = 160;

--
-- AUTO_INCREMENT for table `patientvisit`
--
ALTER TABLE `patientvisit` MODIFY `visit_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT = 39;

--
-- AUTO_INCREMENT for table `usersdata`
--
ALTER TABLE `usersdata` MODIFY `usersID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT = 7;

COMMIT;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */
;


/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */
;


/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */
;

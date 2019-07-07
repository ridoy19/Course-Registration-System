-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql12.freesqldatabase.com
-- Generation Time: Jul 07, 2019 at 07:14 PM
-- Server version: 5.5.58-0ubuntu0.14.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql12296784`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `code` varchar(8) NOT NULL,
  `title` varchar(55) NOT NULL,
  `credit` double(2,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`code`, `title`, `credit`) VALUES
('CSE2015', 'Introduction to Java', 3.0),
('CSE2016', 'Introduction to Java Lab', 1.0),
('CSE3031', 'Operating Systems', 3.0),
('CSE3032', 'Operating Systems Lab', 1.0),
('CSE4041', 'Artificial Intelligence', 3.0),
('CSE4047', 'Advanced Java', 3.0),
('CSE4048', 'Advanced Java Lab', 1.0);

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `initial` varchar(5) NOT NULL,
  `name` varchar(65) NOT NULL,
  `rank` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`initial`, `name`, `rank`) VALUES
('KMH', 'Monirul Hasan', 'Senior Lecturer'),
('RAJ', 'Roksana Akter Jolly', 'Assistant Professor'),
('RIK', 'Rezwan Al Islam Khan', 'Assistant Professor'),
('SM', 'Shahriar Manzoor', 'Associate Professor');

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `reg_id` int(5) NOT NULL,
  `student_id` varchar(13) DEFAULT NULL,
  `course_code` varchar(8) DEFAULT NULL,
  `section_id` int(5) DEFAULT NULL,
  `faculty_initial` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`reg_id`, `student_id`, `course_code`, `section_id`, `faculty_initial`) VALUES
(3, '2016000000044', 'CSE4047', 1, 'KMH'),
(4, '2016000000044', 'CSE4041', 1, 'RIK'),
(10, '2016000000046', 'CSE4047', 1, 'KMH'),
(11, '2016000000046', 'CSE2015', 2, 'RIK'),
(12, '2016000000047', 'CSE2016', 1, 'RAJ'),
(13, '2016000000045', 'CSE2015', 1, 'RAJ'),
(28, '2016000000047', 'CSE4047', 1, 'RIK'),
(29, '2016000000044', 'CSE2015', 1, 'RAJ'),
(30, '2016000000044', 'CSE2016', 1, 'RAJ');

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

CREATE TABLE `section` (
  `id` int(5) NOT NULL,
  `semester` int(5) NOT NULL,
  `section_number` int(5) NOT NULL,
  `seat_limit` int(5) NOT NULL,
  `available_seats` int(5) NOT NULL,
  `room` varchar(10) NOT NULL,
  `course_code` varchar(8) NOT NULL,
  `faculty_initial` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`id`, `semester`, `section_number`, `seat_limit`, `available_seats`, `room`, `course_code`, `faculty_initial`) VALUES
(1, 10, 1, 30, 29, 'AR1001', 'CSE4047', 'KMH'),
(2, 10, 2, 30, 29, 'AR1002', 'CSE4047', 'KMH'),
(10, 11, 1, 25, 24, 'B2513', 'CSE2015', 'RAJ'),
(11, 11, 1, 30, 29, 'B2512', 'CSE2016', 'RAJ'),
(12, 11, 2, 30, 29, 'AR1103', 'CSE2015', 'RIK');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` varchar(13) NOT NULL,
  `name` varchar(65) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `name`) VALUES
('2016000000044', 'Elon Musk'),
('2016000000045', 'Jeff Bezos'),
('2016000000046', 'Mark Zuckerburg'),
('2016000000047', 'Robert Darwin'),
('2016000000050', 'Elon Musk');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`initial`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`reg_id`),
  ADD KEY `student_id` (`student_id`),
  ADD KEY `faculty_initial` (`faculty_initial`),
  ADD KEY `course_code` (`course_code`),
  ADD KEY `section_id` (`section_id`);

--
-- Indexes for table `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`id`),
  ADD KEY `faculty_initial` (`faculty_initial`),
  ADD KEY `course_code` (`course_code`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `registration`
--
ALTER TABLE `registration`
  MODIFY `reg_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `section`
--
ALTER TABLE `section`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `registration`
--
ALTER TABLE `registration`
  ADD CONSTRAINT `registration_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  ADD CONSTRAINT `registration_ibfk_2` FOREIGN KEY (`faculty_initial`) REFERENCES `faculty` (`initial`),
  ADD CONSTRAINT `registration_ibfk_3` FOREIGN KEY (`course_code`) REFERENCES `course` (`code`),
  ADD CONSTRAINT `registration_ibfk_4` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`);

--
-- Constraints for table `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `section_ibfk_1` FOREIGN KEY (`course_code`) REFERENCES `course` (`code`),
  ADD CONSTRAINT `section_ibfk_2` FOREIGN KEY (`faculty_initial`) REFERENCES `faculty` (`initial`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

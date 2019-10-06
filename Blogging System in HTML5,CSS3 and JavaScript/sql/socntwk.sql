-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 10, 2018 at 03:46 PM
-- Server version: 5.7.21
-- PHP Version: 5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `socntwk`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `blogthread_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `blogthread_view`;
CREATE TABLE IF NOT EXISTS `blogthread_view` (
`user_author` varchar(25)
,`user_follower` varchar(25)
,`user_thread_date` timestamp
,`user_thread_txt` text
,`blog_title` varchar(120)
);

-- --------------------------------------------------------

--
-- Table structure for table `blogusers`
--

DROP TABLE IF EXISTS `blogusers`;
CREATE TABLE IF NOT EXISTS `blogusers` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `password` varchar(64) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `cpassword` varchar(64) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `register_date` date NOT NULL,
  `user_status` char(1) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT 'N',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blogusers`
--

INSERT INTO `blogusers` (`user_id`, `user_name`, `password`, `cpassword`, `register_date`, `user_status`) VALUES
(1, 'VBELAGULI', 'vasuvij1', 'vasuvij1', '2014-07-14', 'Y'),
(2, 'AKHILESH', 'vasuvij1', 'vasuvij1', '2014-07-14', 'Y'),
(3, 'ABHIJEET', 'raju', 'raju', '2014-07-23', 'Y'),
(4, 'ABHISHEK', 'bsri', 'bsri', '2018-10-28', 'Y'),
(5, 'VARUN', 'varun', 'varun', '2018-10-28', 'Y'),
(6, 'RADHA', 'RADHA', 'RADHA', '2018-10-28', 'Y'),
(7, 'SUDHA', 'sudha', 'sudha', '2018-10-28', 'Y'),
(8, 'PAVAN', 'pavan1', 'pavan1', '2018-11-10', 'Y'),
(9, 'ANITA', 'anita', 'anita', '2018-11-10', 'Y'),
(10, 'AJAY', 'ajay', 'ajay', '2018-11-10', 'Y');

-- --------------------------------------------------------

--
-- Stand-in structure for view `bloguser_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `bloguser_view`;
CREATE TABLE IF NOT EXISTS `bloguser_view` (
`user_name` varchar(25)
,`user_fname` varchar(25)
,`user_lname` varchar(25)
,`time` timestamp
,`page` varchar(100)
,`ip` varchar(30)
);

-- --------------------------------------------------------

--
-- Table structure for table `msg_followers`
--

DROP TABLE IF EXISTS `msg_followers`;
CREATE TABLE IF NOT EXISTS `msg_followers` (
  `user_msg_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_author` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_authmsg_date` datetime NOT NULL,
  `user_follower` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_thread_txt` varchar(4096) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_thread_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `blog_title` varchar(120) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  PRIMARY KEY (`user_msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `msg_followers`
--

INSERT INTO `msg_followers` (`user_msg_id`, `user_author`, `user_authmsg_date`, `user_follower`, `user_thread_txt`, `user_thread_date`, `blog_title`) VALUES
(29, 'VBELAGULI', '2018-11-01 15:11:43', 'VARUN', 'Varuns response 1st response to vbelagulis 1st blog post', '2018-11-10 14:38:04', 'vbelagulis 1st blog post'),
(30, 'VBELAGULI', '2018-11-01 15:11:43', 'AKHILESH', 'Akhileshs response to vbelagulis 5th blog post\r\nAkhileshs response to vbelagulis 5th blog post\r\nAkhileshs response to vbelagulis 5th blog post\r\nAkhileshs response to vbelagulis 5th blog post', '2018-11-10 14:38:04', 'vbelagulis 5th blog post'),
(31, 'VARUN', '2018-11-01 15:16:54', 'AKHILESH', 'AKHILESH\'S  RESPONSE TO THE THREAD VARUN DAYANIDHIS 3RD BLOG POST', '2018-11-10 14:38:04', 'varun dayanidhi 3rd blog post'),
(32, 'VARUN', '2018-11-01 15:16:54', 'ABHIJEET', 'ABHIJEETS RESPONSE TO VARUNS 3RD BLOG POST\r\nABHIJEETS RESPONSE TO VARUNS 3RD BLOG POST\r\nABHIJEETS RESPONSE TO VARUNS 3RD BLOG POST', '2018-11-01 15:45:58', 'varun dayanidhi 3rd blog post'),
(33, 'VARUN', '2018-11-01 15:16:54', 'ABHISHEK', 'abhsheks response to varun dayanidhis 1st blog post\r\nabhsheks response to varun dayanidhis 1st blog postabhsheks response to varun dayanidhis 1st blog postabhsheks response to varun dayanidhis 1st blog postabhsheks response to varun dayanidhis 1st blog postabhsheks response to varun dayanidhis 1st blog post', '2018-11-01 15:47:36', 'varun dayanidhi 1st blog post'),
(34, 'ABHISHEK', '2018-11-01 15:48:07', 'RADHA', 'radhas response thread post for this post of abhisheks', '2018-11-10 14:38:04', 'abhisheks only one blog post'),
(35, 'AKHILESH', '2018-11-01 15:26:22', 'VBELAGULI', 'my response to akhilieshs first post', '2018-11-10 14:38:04', 'akhileshs 1st blogging post'),
(36, 'AKHILESH', '2018-11-01 15:26:22', 'SUDHA', 'Sudha Murthy\'s response to Akhilesh 2nd blog post.', '2018-11-10 14:38:32', 'akhileshs 2nd blogging post');

-- --------------------------------------------------------

--
-- Table structure for table `thread_messages`
--

DROP TABLE IF EXISTS `thread_messages`;
CREATE TABLE IF NOT EXISTS `thread_messages` (
  `user_thread_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_thread_author` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_follower` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_thread_txt` varchar(4096) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_thread_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `blog_title` varchar(120) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  PRIMARY KEY (`user_thread_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `thread_messages`
--

INSERT INTO `thread_messages` (`user_thread_id`, `user_thread_author`, `user_follower`, `user_thread_txt`, `user_thread_date`, `blog_title`) VALUES
(1, 'VARUN', 'VARUN', 'Varuns response 2nd response to vbelagulis 1st blog post', '2018-11-01 15:18:53', 'varun dayanidhi 1st blog post'),
(2, 'VARUN', 'VARUN', 'Varuns response 1st response to vbelagulis 2nd blog post', '2018-11-01 15:19:40', 'varun dayanidhi 2nd blog post'),
(3, 'VARUN', 'VARUN', 'varuns 1st response to his own 1st blog post', '2018-11-01 15:20:47', 'varun dayanidhi 1st blog post'),
(4, 'VBELAGULI', 'VBELAGULI', 'belagulis first response to his won 5th blog post', '2018-11-10 14:40:08', 'vbelagulis 5th blog post'),
(5, 'VBELAGULI', 'VBELAGULI', 'vbelagulis first thread response to his own 1st blog post', '2018-11-10 14:40:08', 'vbelagulis 1st blog post'),
(6, 'AKHILESH', 'AKHILESH', 'Akhileshs response to his own thread akhileshs 2nd blogging post', '2018-11-10 14:40:08', 'akhileshs 2nd blogging post'),
(7, 'VBELAGULI', 'VBELAGULI', 'My own response to 1st blog post', '2018-11-10 14:40:08', 'vbelagulis 1st blog post');

-- --------------------------------------------------------

--
-- Table structure for table `user_friends`
--

DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE IF NOT EXISTS `user_friends` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `friend_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_user_settings` (`user_name`,`friend_name`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_friends`
--

INSERT INTO `user_friends` (`user_id`, `user_name`, `friend_name`) VALUES
(63, 'ABHIJEET', 'AKHILESH'),
(62, 'ABHISHEK', 'AKHILESH'),
(66, 'ABHISHEK', 'RADHA'),
(67, 'AKHILESH', 'SUDHA'),
(57, 'AKHILESH', 'VBELAGULI'),
(61, 'RADHA', 'AKHILESH'),
(60, 'SUDHA', 'AKHILESH'),
(64, 'VARUN', 'ABHIJEET'),
(65, 'VARUN', 'ABHISHEK'),
(59, 'VARUN', 'AKHILESH'),
(56, 'VARUN', 'VBELAGULI'),
(58, 'VBELAGULI', 'AKHILESH');

-- --------------------------------------------------------

--
-- Table structure for table `user_messages`
--

DROP TABLE IF EXISTS `user_messages`;
CREATE TABLE IF NOT EXISTS `user_messages` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_message` varchar(4096) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_msg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_msg_status` char(2) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL DEFAULT 'PR',
  `blog_title` varchar(120) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_messages`
--

INSERT INTO `user_messages` (`user_id`, `user_name`, `user_message`, `user_msg_date`, `user_msg_status`, `blog_title`) VALUES
(55, 'VBELAGULI', 'vbelagulis 1st blog post\r\nvbelagulis 1st blog post\r\nvbelagulis 1st blog post\r\nvbelagulis 1st blog post', '2018-11-10 14:43:41', 'PR', 'vbelagulis 1st blog post'),
(56, 'VBELAGULI', 'vbelagulis 2nd blog post\r\nvbelagulis 2nd blog post\r\nvbelagulis 2nd blog post\r\nvbelagulis 2nd blog post', '2018-11-10 14:43:41', 'PR', 'vbelagulis 2nd blog post'),
(57, 'VBELAGULI', 'vbelagulis 3rd blog post\r\nvbelagulis 3rd blog post\r\nvbelagulis 3rd blog post\r\nvbelagulis 3rd blog post', '2018-11-10 14:43:41', 'PR', 'vbelagulis 3rd blog post'),
(58, 'VBELAGULI', 'vbelagulis 4th blog post\r\nvbelagulis 4th blog postvbelagulis 4th blog postvbelagulis 4th blog postvbelagulis 4th blog postvbelagulis 4th blog post', '2018-11-10 14:43:41', 'PR', 'vbelagulis 4th blog post'),
(59, 'VBELAGULI', 'vbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog postvbelagulis 5th blog post', '2018-11-10 14:43:41', 'PR', 'vbelagulis 5th blog post'),
(60, 'VARUN', 'varun dayanidhi 1st blog postvarun dayanidhi 1st blog postvarun dayanidhi 1st blog postvarun dayanidhi 1st blog post', '2018-11-01 09:46:54', 'PR', 'varun dayanidhi 1st blog post'),
(61, 'VARUN', 'varun dayanidhi 2nd blog postvarun dayanidhi 2nd blog postvarun dayanidhi 2nd blog postvarun dayanidhi 2nd blog postvarun dayanidhi 2nd blog post', '2018-11-01 09:47:06', 'PR', 'varun dayanidhi 2nd blog post'),
(62, 'VARUN', 'varun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog postvarun dayanidhi 3rd blog post', '2018-11-01 09:47:18', 'PR', 'varun dayanidhi 3rd blog post'),
(63, 'AKHILESH', 'akhileshs 1st blogging postakhileshs 1st blogging postakhileshs 1st blogging postakhileshs 1st blogging postakhileshs 1st blogging postakhileshs 1st blogging postakhileshs 1st blogging post', '2018-11-10 14:43:41', 'PR', 'akhileshs 1st blogging post'),
(64, 'AKHILESH', 'akhileshs 2nd blogging postakhileshs 2nd blogging postakhileshs 2nd blogging postakhileshs 2nd blogging postakhileshs 2nd blogging post', '2018-11-10 14:43:41', 'PR', 'akhileshs 2nd blogging post'),
(65, 'ABHISHEK', 'abhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog postabhisheks only one blog post', '2018-11-01 10:18:07', 'PR', 'abhisheks only one blog post'),
(66, 'SUDHA', 'This is my first blog post about\r\nInfosys foundation is committed to the communities in which it operates. This has led to the creation of Infosys Foundation to support the underprivileged sections of society. A not-for-profit initiative aimed at fulfilling the social responsibility of Infosys Ltd., the Infosys Foundation creates opportunities and strives towards a more equitable society.', '2018-11-10 14:43:41', 'PR', 'Sudha murthys first blog post');

-- --------------------------------------------------------

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE IF NOT EXISTS `user_profile` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_fname` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_lname` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  `user_dob` date NOT NULL,
  `user_gender` varchar(6) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_email` varchar(32) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_mobile` bigint(16) NOT NULL,
  `user_phone` bigint(16) DEFAULT NULL,
  `user_verified` char(1) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT 'N',
  `user_address` varchar(120) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `user_photo` varchar(200) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  `user_video` varchar(200) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  `user_audio` varchar(200) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_profile`
--

INSERT INTO `user_profile` (`user_id`, `user_name`, `user_fname`, `user_lname`, `user_dob`, `user_gender`, `user_email`, `user_mobile`, `user_phone`, `user_verified`, `user_address`, `user_photo`, `user_video`, `user_audio`) VALUES
(1, 'VBELAGULI', 'VIJAYAKUMAR', 'BELAGULI', '1960-08-31', 'male', 'vbelaguli@gmail.com', 8028604094, 9880667499, 'Y', 'HOD CSE\r\nGAT', 'photos/vbelaguli.jpg', NULL, NULL),
(2, 'AKHILESH', 'AKHILESH', 'VIJAYAKUMAR', '1993-10-09', 'male', 'akhilesh@gmail.com', 9880666777, 8028604194, 'Y', 'RNSIT,\r\nISE dept,\r\nbangalore', 'photos/akhilesh.jpg', NULL, NULL),
(3, 'ABHIJEET', 'ABHIJEET', 'KUMAR', '1960-08-12', 'male', 'rjan@gat.ac.in', 9344343255, 9880034565, 'Y', '224, f-road\r\nrr nagar\r\nbngalore', 'photos/raju.jpg', NULL, NULL),
(4, 'ABHISHEK', 'ABHISHEK', 'ANAND', '1940-11-12', 'male', 'bsri@gmail.com', 9343432234, 28604111, 'Y', 'sriram puram', NULL, NULL, NULL),
(5, 'VARUN', 'VARUN', 'DAYANIDHI', '1999-11-12', 'male', 'varunday@gmail.com', 9823224411, 24255555, 'Y', '19/1 3rd cross', NULL, NULL, NULL),
(6, 'RADHA', 'RADHA', 'M', '1975-11-12', 'female', 'radhamk@gmail.com', 923423223, 343253254, 'Y', 'vaishnavi', NULL, NULL, NULL),
(7, 'SUDHA', 'SUDHA', 'MURTHY', '1967-11-12', 'female', 'sudhamurthy@gmail.com', 858999994, 32453456, 'Y', 'Infosys foundation', 'photos/sudha.jpg', NULL, NULL),
(8, 'PAVAN', 'PAVAN', 'KUMAR', '1997-08-19', 'male', 'pavan.rnsit@gmail.com', 7834924595, 28603434, 'Y', '', NULL, NULL, NULL),
(9, 'ANITA', 'ANITA', 'R', '1997-05-12', 'female', 'anita.rnsit@gmail.com', 7788885999, 83435235, 'Y', '', NULL, NULL, NULL),
(10, 'AJAY', 'AJAY', 'KUMAR', '1997-12-12', 'male', 'ajay.kumar@gmail.com', 7834384352, 34353636, 'Y', 'rnsit hostel', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `webcam`
--

DROP TABLE IF EXISTS `webcam`;
CREATE TABLE IF NOT EXISTS `webcam` (
  `ID` int(12) NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `images` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `whoisonline`
--

DROP TABLE IF EXISTS `whoisonline`;
CREATE TABLE IF NOT EXISTS `whoisonline` (
  `user_name` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `page` varchar(100) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  `ip` varchar(30) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure for view `blogthread_view`
--
DROP TABLE IF EXISTS `blogthread_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `blogthread_view`  AS  select `a`.`user_author` AS `user_author`,`a`.`user_follower` AS `user_follower`,`a`.`user_thread_date` AS `user_thread_date`,`a`.`user_thread_txt` AS `user_thread_txt`,`c`.`blog_title` AS `blog_title` from (`msg_followers` `a` join `user_messages` `c` on(((`a`.`user_author` = `c`.`user_name`) and (`a`.`blog_title` = `c`.`blog_title`)))) union select `d`.`user_thread_author` AS `user_thread_author`,`d`.`user_follower` AS `user_follower`,`d`.`user_thread_date` AS `user_thread_date`,`d`.`user_thread_txt` AS `user_thread_txt`,`d`.`blog_title` AS `blog_title` from (`thread_messages` `d` join `user_messages` `e` on(((`d`.`user_thread_author` = `e`.`user_name`) and (`d`.`blog_title` = `e`.`blog_title`)))) ;

-- --------------------------------------------------------

--
-- Structure for view `bloguser_view`
--
DROP TABLE IF EXISTS `bloguser_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `bloguser_view`  AS  select `a`.`user_name` AS `user_name`,`b`.`user_fname` AS `user_fname`,`b`.`user_lname` AS `user_lname`,`a`.`time` AS `time`,`a`.`page` AS `page`,`a`.`ip` AS `ip` from (`whoisonline` `a` join `user_profile` `b`) where (`a`.`user_name` = `b`.`user_name`) ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

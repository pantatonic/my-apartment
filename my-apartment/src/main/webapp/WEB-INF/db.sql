-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2017 at 04:21 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `my_apartment`
--

-- --------------------------------------------------------

--
-- Table structure for table `building`
--

CREATE TABLE IF NOT EXISTS `building` (
`id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` text,
  `tel` varchar(255) DEFAULT NULL,
  `electricity_meter_digit` int(11) NOT NULL,
  `electricity_charge_per_unit` decimal(20,2) NOT NULL,
  `min_electricity_unit` int(11) DEFAULT NULL,
  `min_electricity_charge` decimal(20,2) DEFAULT NULL,
  `water_meter_digit` int(11) NOT NULL,
  `water_charge_per_unit` decimal(20,2) NOT NULL,
  `min_water_unit` int(11) DEFAULT NULL,
  `min_water_charge` decimal(20,2) DEFAULT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `building`
--

INSERT INTO `building` (`id`, `name`, `address`, `tel`, `electricity_meter_digit`, `electricity_charge_per_unit`, `min_electricity_unit`, `min_electricity_charge`, `water_meter_digit`, `water_charge_per_unit`, `min_water_unit`, `min_water_charge`) VALUES
(1, 'อาคาร 1', 'ที่อยู่ อาคาร 1', '0000000000', 4, '1.00', 2, '3.00', 4, '4.00', 5, '6.00'),
(2, 'อาคาร 2', 'ที่อยู่ อาคาร 2', '', 4, '7.00', NULL, NULL, 4, '6.00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `check_in_out_history`
--

CREATE TABLE IF NOT EXISTS `check_in_out_history` (
`id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `check_in_date` date NOT NULL,
  `id_card` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `address` text,
  `remark` text,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `current_check_in`
--

CREATE TABLE IF NOT EXISTS `current_check_in` (
  `room_id` int(11) NOT NULL,
  `check_in_date` date NOT NULL,
  `id_card` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `address` text,
  `remark` text,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE IF NOT EXISTS `room` (
`id` int(11) NOT NULL,
  `building_id` int(11) NOT NULL,
  `floor_seq` int(11) NOT NULL,
  `room_no` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price_per_month` decimal(20,2) NOT NULL,
  `room_status_id` int(11) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `building_id`, `floor_seq`, `room_no`, `name`, `price_per_month`, `room_status_id`) VALUES
(1, 1, 1, '1/1', '', '1500.00', 1),
(2, 2, 1, '1/1', '', '1500.00', 1),
(3, 1, 1, '1/2', '', '1501.00', 1),
(4, 1, 1, '1/3', '', '1550.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `room_reservation`
--

CREATE TABLE IF NOT EXISTS `room_reservation` (
`id` int(11) NOT NULL,
  `reserve_date` date NOT NULL,
  `reserve_expired` date DEFAULT NULL,
  `room_id` int(11) NOT NULL,
  `id_card` varchar(50) NOT NULL,
  `reserve_name` varchar(255) NOT NULL,
  `reserve_lastname` varchar(255) DEFAULT NULL,
  `remark` text,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `status` int(1) NOT NULL COMMENT '1=reserve, 2=cancel, 3=cancel_for_checkin'
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `room_reservation`
--

INSERT INTO `room_reservation` (`id`, `reserve_date`, `reserve_expired`, `room_id`, `id_card`, `reserve_name`, `reserve_lastname`, `remark`, `created_date`, `updated_date`, `status`) VALUES
(1, '2017-04-16', NULL, 1, '1111', '1', '11', '111', '2017-04-16 17:25:13', '2017-04-16 17:26:28', 2),
(2, '2017-04-16', NULL, 1, '2222', '2', '22', '222', '2017-04-16 17:26:40', '2017-04-16 17:26:47', 3),
(3, '2017-04-16', NULL, 1, '3333', '3', '33', '333', '2017-04-16 17:26:56', '2017-04-16 17:27:15', 2),
(4, '2017-04-16', '2017-04-20', 3, '8', '88', '888', '8888', '2017-04-16 17:28:41', NULL, 1),
(5, '2017-04-19', NULL, 1, '1x1x1x1x', '1x', '1x1x', '1x1x1x1x', '2017-04-16 17:29:19', '2017-04-19 13:01:12', 2),
(6, '2017-04-19', NULL, 1, '11111', '1', '11', '111', '2017-04-19 13:01:30', '2017-04-19 21:05:33', 2),
(7, '2017-04-20', '2017-04-22', 1, '1111', '1', '11', '111', '2017-04-19 21:05:55', '2017-04-20 15:14:38', 2),
(8, '2017-04-20', '2017-04-24', 1, '1', '11', '111', '1111', '2017-04-20 15:15:00', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `room_status`
--

CREATE TABLE IF NOT EXISTS `room_status` (
`id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `room_status`
--

INSERT INTO `room_status` (`id`, `status`) VALUES
(1, 'room.status.enabled'),
(2, 'room.status.disabled'),
(3, 'room.status.maintenance');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE IF NOT EXISTS `test` (
`id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`id`, `name`, `surname`) VALUES
(1, 'Somchai', 'Jaidee'),
(2, 'Somsri', 'Jaiyen');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` int(1) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `email`, `password`, `is_admin`, `status`) VALUES
(1, 'System', 'Admin', 'admin@admin.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `building`
--
ALTER TABLE `building`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `check_in_out_history`
--
ALTER TABLE `check_in_out_history`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room_reservation`
--
ALTER TABLE `room_reservation`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room_status`
--
ALTER TABLE `room_status`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `building`
--
ALTER TABLE `building`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `check_in_out_history`
--
ALTER TABLE `check_in_out_history`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `room_reservation`
--
ALTER TABLE `room_reservation`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `room_status`
--
ALTER TABLE `room_status`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
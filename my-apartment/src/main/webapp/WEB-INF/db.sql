-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 14, 2017 at 08:46 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `my_apartment`
--

-- --------------------------------------------------------

--
-- Table structure for table `building`
--

CREATE TABLE `building` (
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `building`
--

INSERT INTO `building` (`id`, `name`, `address`, `tel`, `electricity_meter_digit`, `electricity_charge_per_unit`, `min_electricity_unit`, `min_electricity_charge`, `water_meter_digit`, `water_charge_per_unit`, `min_water_unit`, `min_water_charge`) VALUES
(1, 'อาคาร 1', 'ที่อยู่ อาคาร 1', '0000000000', 4, '7.00', 2, '14.00', 4, '6.00', 1, '50.00'),
(2, 'อาคาร 2', 'ที่อยู่ อาคาร 2', '', 4, '7.10', NULL, NULL, 4, '6.00', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `check_in_out_history`
--

CREATE TABLE `check_in_out_history` (
  `id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `check_in_date` date NOT NULL,
  `check_out_date` date DEFAULT NULL,
  `id_card` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `address` text,
  `remark` text,
  `number_code` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `current_check_in`
--

CREATE TABLE `current_check_in` (
  `room_id` int(11) NOT NULL,
  `check_in_date` date NOT NULL,
  `id_card` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `address` text,
  `remark` text,
  `number_code` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `electricity_meter`
--

CREATE TABLE `electricity_meter` (
  `room_id` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `previous_meter` varchar(10) NOT NULL,
  `present_meter` varchar(10) NOT NULL,
  `charge_per_unit` decimal(20,2) NOT NULL,
  `usage_unit` int(11) NOT NULL,
  `value` decimal(20,2) NOT NULL,
  `use_minimun_unit_calculate` int(1) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `electricity_meter`
--

INSERT INTO `electricity_meter` (`room_id`, `month`, `year`, `previous_meter`, `present_meter`, `charge_per_unit`, `usage_unit`, `value`, `use_minimun_unit_calculate`, `created_date`, `updated_date`) VALUES
(9, 6, 2017, '2366', '2366', '7.10', 1, '7.10', 0, '2017-06-14 10:13:02', NULL),
(10, 6, 2017, '7858', '7858', '7.10', 3, '21.30', 0, '2017-06-14 10:13:02', NULL),
(11, 6, 2017, '9147', '9147', '7.10', 5, '35.50', 0, '2017-06-14 10:13:02', NULL),
(12, 6, 2017, '8538', '8538', '7.10', 7, '49.70', 0, '2017-06-14 10:13:02', NULL),
(1, 6, 2017, '5642', '5643', '7.00', 1, '14.00', 1, '2017-06-14 13:13:43', NULL),
(2, 6, 2017, '1374', '1400', '7.00', 26, '182.00', 1, '2017-06-14 13:13:43', NULL),
(3, 6, 2017, '1285', '1300', '7.00', 15, '105.00', 1, '2017-06-14 13:13:43', NULL),
(4, 6, 2017, '8611', '8700', '7.00', 89, '623.00', 1, '2017-06-14 13:13:43', NULL),
(5, 6, 2017, '9991', '9999', '7.00', 8, '56.00', 1, '2017-06-14 13:13:43', NULL),
(6, 6, 2017, '6461', '6500', '7.00', 39, '273.00', 1, '2017-06-14 13:13:43', NULL),
(7, 6, 2017, '3452', '3500', '7.00', 48, '336.00', 1, '2017-06-14 13:13:43', NULL),
(8, 6, 2017, '4213', '4300', '7.00', 87, '609.00', 1, '2017-06-14 13:13:43', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `notice_check_out`
--

CREATE TABLE `notice_check_out` (
  `room_id` int(11) NOT NULL,
  `notice_check_out_date` date NOT NULL,
  `remark` text,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `building_id` int(11) NOT NULL,
  `floor_seq` int(11) NOT NULL,
  `room_no` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price_per_month` decimal(20,2) NOT NULL,
  `room_status_id` int(11) NOT NULL,
  `startup_electricity_meter` varchar(11) NOT NULL,
  `startup_water_meter` varchar(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`id`, `building_id`, `floor_seq`, `room_no`, `name`, `price_per_month`, `room_status_id`, `startup_electricity_meter`, `startup_water_meter`) VALUES
(1, 1, 1, '1-1/1', '', '1500.00', 1, '5642', '2464'),
(2, 1, 1, '1-1/2', '', '1500.00', 1, '1374', '3221'),
(3, 1, 1, '1-1/3', '', '1500.00', 1, '1285', '4165'),
(4, 1, 1, '1-1/4', '', '1500.00', 1, '8611', '6112'),
(5, 1, 2, '1-2/1', '', '1600.00', 1, '9991', '9992'),
(6, 1, 2, '1-2/2', '', '1600.00', 1, '6461', '7635'),
(7, 1, 2, '1-2/3', '', '1600.00', 1, '3452', '4544'),
(8, 1, 2, '1-2/4', '', '1600.00', 1, '4213', '0121'),
(9, 2, 1, '2-1/1', '', '2100.00', 1, '2365', '4513'),
(10, 2, 1, '2-1/2', '', '2100.00', 1, '7855', '5364'),
(11, 2, 1, '2-1/3', '', '2100.00', 1, '9142', '6843'),
(12, 2, 1, '2-1/4', '', '2100.00', 1, '8531', '3121');

-- --------------------------------------------------------

--
-- Table structure for table `room_reservation`
--

CREATE TABLE `room_reservation` (
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `room_status`
--

CREATE TABLE `room_status` (
  `id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` int(1) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `lastname`, `email`, `password`, `is_admin`, `status`) VALUES
(1, 'System', 'Admin', 'admin@admin.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `water_meter`
--

CREATE TABLE `water_meter` (
  `room_id` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `previous_meter` varchar(10) NOT NULL,
  `present_meter` varchar(10) NOT NULL,
  `charge_per_unit` decimal(20,2) NOT NULL,
  `usage_unit` int(11) NOT NULL,
  `value` decimal(20,2) NOT NULL,
  `use_minimun_unit_calculate` int(1) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `water_meter`
--

INSERT INTO `water_meter` (`room_id`, `month`, `year`, `previous_meter`, `present_meter`, `charge_per_unit`, `usage_unit`, `value`, `use_minimun_unit_calculate`, `created_date`, `updated_date`) VALUES
(1, 6, 2017, '2464', '2475', '6.00', 11, '66.00', 1, '2017-06-14 13:13:43', NULL),
(2, 6, 2017, '3221', '3311', '6.00', 90, '540.00', 1, '2017-06-14 13:13:43', NULL),
(3, 6, 2017, '4165', '4183', '6.00', 18, '108.00', 1, '2017-06-14 13:13:43', NULL),
(4, 6, 2017, '6112', '6195', '6.00', 83, '498.00', 1, '2017-06-14 13:13:43', NULL),
(5, 6, 2017, '9992', '9999', '6.00', 7, '42.00', 1, '2017-06-14 13:13:43', NULL),
(6, 6, 2017, '7635', '7685', '6.00', 50, '300.00', 1, '2017-06-14 13:13:43', NULL),
(7, 6, 2017, '4544', '4598', '6.00', 54, '324.00', 1, '2017-06-14 13:13:43', NULL),
(8, 6, 2017, '0121', '0135', '6.00', 14, '84.00', 1, '2017-06-14 13:13:43', NULL);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `check_in_out_history`
--
ALTER TABLE `check_in_out_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `room_reservation`
--
ALTER TABLE `room_reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `room_status`
--
ALTER TABLE `room_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
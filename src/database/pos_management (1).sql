-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 23, 2024 at 09:30 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pos_management`
--
CREATE DATABASE IF NOT EXISTS `pos_management` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `pos_management`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `ACCOUNT_ID` varchar(50) NOT NULL,
  `VERITFY` tinyint(1) NOT NULL,
  `TEMP_PASS` tinyint(1) NOT NULL,
  `STATUS` tinyint(1) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `verify_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`ACCOUNT_ID`, `VERITFY`, `TEMP_PASS`, `STATUS`, `ROLE_ID`, `PASSWORD`, `verify_code`) VALUES
('ACC0000001', 1, 0, 1, 1, '$2a$10$Km0QibT4/fv7ExX4HcsrduuhwQId5VS75zNzb46kfUSLVBVpzyPzG', NULL),
('ACC0000002', 1, 0, 1, 2, '$2a$10$hJBrv0IUZORPRuJ9V6J9I.6I3KzYMnV1z1MO/dSkFDfmonQ8dRkXe', NULL),
('ACC0000003', 1, 0, 0, 2, 'qwerty', NULL),
('ACC0000004', 1, 0, 1, 1, 'abc123', NULL),
('ACC0000005', 1, 0, 1, 1, 'adminpass', NULL),
('ACC0000006', 1, 0, 1, 2, 'userpass', NULL),
('ACC0000007', 1, 0, 1, 1, 'testpass', NULL),
('ACC0000008', 1, 0, 1, 2, 'password123', NULL),
('ACC0000009', 1, 0, 1, 2, 'securepass', NULL),
('ACC0000010', 1, 0, 1, 2, 'admin123', NULL),
('ACC0000011', 1, 0, 1, 1, '$2a$10$utSzPFSsV7EdfoEvrCYH4OOh6lztoBf2T/B8S.FcA7.gmey1DoZGu', NULL),
('ACC0000012', 1, 1, 1, 2, '$2a$10$ZZoc/4Zza7uHuu11hg1pKeRSeBmycj1.O1U0LR3axeH4.b7xQb5Aa', NULL),
('ACC0000013', 1, 1, 1, 2, '$2a$10$eSQZd1CJaeHEmdqpVsNd0epoX/3F/d0liMdKiex3NABkIgKJavSH2', NULL),
('ACC0000014', 0, 1, 1, 2, '$2a$10$6zC8RGixpD9WZLBNgBWYJ.VpJ/6BOgLdipO5rplrsmd6BLlOHpMbO', '20240416160438-f53f7db1-aaed-4a4b-9e7f-1660abb5fa00');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `CATEGORY_ID` int(11) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CATEGORY_ID`, `NAME`, `DESCRIPTION`) VALUES
(1, 'Electronics', 'Electronic gadgets'),
(2, 'Clothing', 'Fashion items'),
(3, 'Books', 'Literary works'),
(4, 'Home Decor', 'Interior furnishings'),
(5, 'Beauty', 'Cosmetic products'),
(6, 'Sports', 'Athletic gear'),
(7, 'Toys', 'Playtime essentials'),
(8, 'Food', 'Edible delights'),
(9, 'Furniture', 'Household furnishings'),
(10, 'Jewelry', 'Adornments');

-- --------------------------------------------------------

--
-- Table structure for table `category_seq`
--

CREATE TABLE `category_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `category_seq`
--

INSERT INTO `category_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CUSTOMER_ID` varchar(50) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `date_created` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CUSTOMER_ID`, `NAME`, `ADDRESS`, `PHONE_NUMBER`, `EMAIL`, `date_created`) VALUES
('CUS0000001', 'John Doe', '123 Main St', '555-1234', 'john@example.com', '2023-12-19 15:10:30'),
('CUS0000002', 'Jane Smith', '456 Oak St', '555-5678', 'jane@example.com', '2023-12-19 15:10:30'),
('CUS0000003', 'Bob Johnson', '789 Pine St', '555-9876', 'bob@example.com', '2023-12-19 15:10:30'),
('CUS0000004', 'Alice Brown', '101 Cedar St', '555-5432', 'alice@example.com', '2023-12-19 15:10:30'),
('CUS0000005', 'Charlie White', '202 Maple St', '555-6789', 'charlie@example.com', '2023-12-19 15:10:30'),
('CUS0000006', 'Eva Green', '303 Elm St', '555-2345', 'eva@example.com', '2023-12-19 15:10:30'),
('CUS0000007', 'David Black', '404 Birch St', '555-8765', 'david@example.com', '2023-12-19 15:10:30'),
('CUS0000008', 'Grace Gray', '505 Redwood St', '555-4321', 'grace@example.com', '2023-12-19 15:10:30'),
('CUS0000009', 'Frank Orange', '606 Oakwood St', '555-7654', 'frank@example.com', '2023-12-19 15:10:30'),
('CUS0000010', 'Holly Purple', '707 Walnut St', '555-3456', 'holly@example.com', '2023-12-19 15:10:30'),
('CUS0000011', 'Tạ Tiến Đạt', 'Admin Address', '0356779197', 'tadat290903@gmail.com', '2023-12-26 02:43:28'),
('CUS0000012', 'Jake John', 'Admin Address', '0147258369', 'jakejohn3004@gmail.com', '2023-12-26 02:42:39'),
('CUS0000013', 'Jake John 2', 'Admin Address', '0123456789', 'jakejohn@gmail.com', '2023-12-26 02:41:16'),
('CUS0000014', 'Jake John 2', 'Admin Address', '0147896325', 'jakejohn@gmail.com', '2023-12-26 16:44:11'),
('CUS0000015', 'Jammy Carrol', 'California', '0321654987', 'jammycarrol@gmail.com', NULL),
('CUS0000016', 'Tạ Tiến Đạt', 'Thành phố Hà Nội', '0231456987', 'jakystyle@gmail.com', NULL),
('CUS0000017', 'Tạ Tiến Đạt', 'Thành phố Cần Thơ', '0231456666', 'AJStyle@gmail.com', NULL),
('CUS0000018', 'Admin 2', 'Tỉnh Sơn La', '0789456123', 'admin2@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `customers_voucher`
--

CREATE TABLE `customers_voucher` (
  `CUSTOMER_VOUCHER_ID` varchar(50) NOT NULL,
  `VOUCHER_ID` int(11) NOT NULL,
  `CUSTOMER_ID` varchar(500) NOT NULL,
  `DATE_USED` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers_voucher`
--

INSERT INTO `customers_voucher` (`CUSTOMER_VOUCHER_ID`, `VOUCHER_ID`, `CUSTOMER_ID`, `DATE_USED`) VALUES
('CSV0000001', 1, 'CUS0000011', '2024-04-09'),
('CSV0000002', 1, 'CUS0000011', '2024-04-21');

-- --------------------------------------------------------

--
-- Table structure for table `log_history`
--

CREATE TABLE `log_history` (
  `LOG_ID` varchar(500) NOT NULL,
  `USER_ID` varchar(500) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  `STATUS` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `log_history`
--

INSERT INTO `log_history` (`LOG_ID`, `USER_ID`, `DATE`, `STATUS`) VALUES
('LOG0000001', 'USE0000001', '2024-04-20 00:33:49', 'LOGIN'),
('LOG0000002', 'USE0000001', '2024-04-20 00:38:00', 'LOGIN'),
('LOG0000003', 'USE0000001', '2024-04-20 00:40:14', 'LOGIN'),
('LOG0000004', 'USE0000001', '2024-04-20 01:41:26', 'LOGIN'),
('LOG0000005', 'USE0000001', '2024-04-20 01:42:24', 'LOGIN'),
('LOG0000006', 'USE0000001', '2024-04-20 10:20:36', 'LOGIN'),
('LOG0000007', 'USE0000001', '2024-04-20 10:21:56', 'LOGIN'),
('LOG0000008', 'USE0000001', '2024-04-20 10:22:02', 'LOGOUT'),
('LOG0000009', 'USE0000001', '2024-04-21 11:20:19', 'LOGIN'),
('LOG0000010', 'USE0000001', '2024-04-21 15:05:00', 'LOGIN'),
('LOG0000011', 'USE0000001', '2024-04-21 15:08:49', 'LOGIN'),
('LOG0000012', 'USE0000001', '2024-04-21 15:11:04', 'LOGIN'),
('LOG0000013', 'USE0000001', '2024-04-21 15:11:07', 'LOGIN'),
('LOG0000014', 'USE0000001', '2024-04-21 15:11:54', 'LOGIN'),
('LOG0000015', 'USE0000001', '2024-04-21 15:12:56', 'LOGIN'),
('LOG0000016', 'USE0000001', '2024-04-21 15:17:07', 'LOGIN'),
('LOG0000017', 'USE0000001', '2024-04-21 15:17:12', 'LOGIN'),
('LOG0000018', 'USE0000001', '2024-04-21 15:18:52', 'LOGIN'),
('LOG0000019', 'USE0000001', '2024-04-21 15:19:01', 'LOGIN'),
('LOG0000020', 'USE0000001', '2024-04-21 15:21:31', 'LOGIN'),
('LOG0000021', 'USE0000001', '2024-04-21 15:23:07', 'LOGIN'),
('LOG0000022', 'USE0000001', '2024-04-21 15:25:36', 'LOGIN'),
('LOG0000023', 'USE0000001', '2024-04-21 15:27:30', 'LOGIN'),
('LOG0000024', 'USE0000001', '2024-04-21 15:27:34', 'LOGIN'),
('LOG0000025', 'USE0000001', '2024-04-21 15:31:59', 'LOGIN'),
('LOG0000026', 'USE0000001', '2024-04-23 01:22:38', 'LOGIN'),
('LOG0000027', 'USE0000001', '2024-04-23 10:44:43', 'LOGIN'),
('LOG0000028', 'USE0000001', '2024-04-23 11:10:31', 'LOGIN'),
('LOG0000029', 'USE0000001', '2024-04-23 11:16:47', 'LOGIN'),
('LOG0000030', 'USE0000001', '2024-04-23 11:19:10', 'LOGIN'),
('LOG0000031', 'USE0000001', '2024-04-23 11:20:58', 'LOGIN'),
('LOG0000032', 'USE0000001', '2024-04-23 13:07:25', 'LOGIN'),
('LOG0000033', 'USE0000001', '2024-04-23 13:10:01', 'LOGIN'),
('LOG0000034', 'USE0000001', '2024-04-23 13:12:17', 'LOGIN'),
('LOG0000035', 'USE0000001', '2024-04-23 13:12:20', 'LOGIN'),
('LOG0000036', 'USE0000001', '2024-04-23 13:19:45', 'LOGIN'),
('LOG0000037', 'USE0000001', '2024-04-23 13:21:11', 'LOGIN'),
('LOG0000038', 'USE0000001', '2024-04-23 13:25:17', 'LOGIN'),
('LOG0000039', 'USE0000001', '2024-04-23 13:31:03', 'LOGIN'),
('LOG0000040', 'USE0000001', '2024-04-23 13:42:25', 'LOGIN'),
('LOG0000041', 'USE0000001', '2024-04-23 14:25:18', 'LOGIN'),
('LOG0000042', 'USE0000001', '2024-04-23 14:25:20', 'LOGOUT'),
('LOG0000043', 'USE0000002', '2024-04-23 14:25:26', 'LOGIN'),
('LOG0000044', 'USE0000002', '2024-04-23 14:25:30', 'LOGOUT');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `ORDER_ID` varchar(50) NOT NULL,
  `USER_ID` varchar(50) NOT NULL,
  `DATE_CREATED` datetime DEFAULT current_timestamp(),
  `NOTE` varchar(100) DEFAULT NULL,
  `CUSTOMER_ID` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`ORDER_ID`, `USER_ID`, `DATE_CREATED`, `NOTE`, `CUSTOMER_ID`) VALUES
('ORD0000001', 'USE0000001', '2023-12-19 14:52:27', '1', 'CUS0000001'),
('ORD0000002', 'USE0000002', '2023-12-19 14:52:27', '2', 'CUS0000001'),
('ORD0000003', 'USE0000003', '2023-12-20 14:52:27', '3', 'CUS0000001'),
('ORD0000004', 'USE0000004', '2023-12-18 14:52:27', '1', 'CUS0000004'),
('ORD0000005', 'USE0000005', '2023-12-19 14:52:27', '1', 'CUS0000005'),
('ORD0000006', 'USE0000006', '2023-12-19 14:52:27', '1', 'CUS0000006'),
('ORD0000007', 'USE0000007', '2023-12-20 00:00:00', '1', 'CUS0000007'),
('ORD0000008', 'USE0000008', '2023-12-19 14:52:27', '1', 'CUS0000008'),
('ORD0000009', 'USE0000009', '2023-11-28 00:05:19', '1', 'CUS0000009'),
('ORD0000010', 'USE0000010', '2023-12-19 14:52:27', '1', 'CUS0000010'),
('ORD0000011', 'USE0000001', '2023-12-23 00:07:33', '1', 'CUS0000002'),
('ORD0000012', 'USE0000002', '2023-12-24 00:08:02', '1', 'CUS0000003'),
('ORD0000013', 'USE0000001', '2023-12-25 15:21:21', '2', 'CUS0000002'),
('ORD0000014', 'USE0000002', '2023-12-25 15:28:24', '4', 'CUS0000001'),
('ORD0000015', 'USE0000001', '2023-12-25 15:32:24', '5', 'CUS0000001'),
('ORD0000016', 'USE0000001', '2023-12-26 01:41:19', '6', 'CUS0000001'),
('ORD0000017', 'USE0000001', '2023-12-26 02:18:54', '1', 'CUS0000011'),
('ORD0000018', 'USE0000001', '2023-12-26 02:41:16', '1', 'CUS0000013'),
('ORD0000019', 'USE0000001', '2023-12-26 02:42:39', '1', 'CUS0000012'),
('ORD0000020', 'USE0000001', '2023-12-26 02:43:28', '2', 'CUS0000011'),
('ORD0000021', 'USE0000001', '2023-12-26 02:46:37', '3', 'CUS0000011'),
('ORD0000022', 'USE0000001', '2023-12-26 13:01:15', '7', 'CUS0000001'),
('ORD0000023', 'USE0000001', '2023-12-26 13:05:51', '8', 'CUS0000001'),
('ORD0000024', 'USE0000001', '2023-12-26 16:44:11', '1', 'CUS0000014'),
('ORD0000025', 'USE0000001', '2024-03-19 21:19:14', '4', 'CUS0000011'),
('ORD0000026', 'USE0000001', '2024-03-22 14:48:03', '5', 'CUS0000011'),
('ORD0000027', 'USE0000001', '2024-04-03 15:44:30', '6', 'CUS0000011'),
('ORD0000028', 'USE0000001', '2024-04-03 15:45:15', '7', 'CUS0000011'),
('ORD0000029', 'USE0000001', '2024-04-08 13:51:24', '8', 'CUS0000011'),
('ORD0000030', 'USE0000001', '2024-04-08 22:41:15', '9', 'CUS0000011'),
('ORD0000031', 'USE0000001', '2024-04-08 23:44:42', '10', 'CUS0000011'),
('ORD0000032', 'USE0000001', '2024-04-09 00:09:16', '11', 'CUS0000011'),
('ORD0000033', 'USE0000001', '2024-04-09 15:08:02', '12', 'CUS0000011'),
('ORD0000034', 'USE0000001', '2024-04-18 19:59:29', '13', 'CUS0000011'),
('ORD0000035', 'USE0000001', '2024-04-18 20:27:34', '14', 'CUS0000011'),
('ORD0000036', 'USE0000001', '2024-04-18 20:31:44', '15', 'CUS0000011'),
('ORD0000037', 'USE0000001', '2024-04-18 20:50:31', '16', 'CUS0000011'),
('ORD0000038', 'USE0000001', '2024-04-21 11:21:13', '17', 'CUS0000011'),
('ORD0000039', 'USE0000001', '2024-04-21 15:05:18', '18', 'CUS0000011'),
('ORD0000040', 'USE0000001', '2024-04-21 15:05:48', '19', 'CUS0000011'),
('ORD0000041', 'USE0000001', '2024-04-21 15:06:38', '20', 'CUS0000011'),
('ORD0000042', 'USE0000001', '2024-04-21 15:27:41', '21', 'CUS0000011');

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `ORDER_DETAILS_ID` varchar(255) NOT NULL,
  `ORDER_ID` varchar(50) NOT NULL,
  `PRODUCT_ID` varchar(50) NOT NULL,
  `QUANTITY` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`ORDER_DETAILS_ID`, `ORDER_ID`, `PRODUCT_ID`, `QUANTITY`) VALUES
('ODT0000001', 'ORD0000001', 'PRO0000001', 2),
('ODT0000002', 'ORD0000002', 'PRO0000002', 1),
('ODT0000003', 'ORD0000003', 'PRO0000003', 3),
('ODT0000004', 'ORD0000004', 'PRO0000004', 1),
('ODT0000005', 'ORD0000005', 'PRO0000005', 2),
('ODT0000006', 'ORD0000006', 'PRO0000006', 1),
('ODT0000007', 'ORD0000007', 'PRO0000007', 2),
('ODT0000008', 'ORD0000008', 'PRO0000008', 3),
('ODT0000009', 'ORD0000009', 'PRO0000009', 1),
('ODT0000010', 'ORD0000010', 'PRO0000010', 2),
('ODT0000011', 'ORD0000011', 'PRO0000006', 3),
('ODT0000012', 'ORD0000012', 'PRO0000004', 5),
('ODT0000014', 'ORD0000002', 'PRO0000007', 12),
('ODT0000015', 'ORD0000003', 'PRO0000007', 6),
('ODT0000016', 'ORD0000012', 'PRO0000002', 7),
('ODT0000017', 'ORD0000003', 'PRO0000003', 11),
('ODT0000018', 'ORD0000009', 'PRO0000010', 3),
('ODT0000019', 'ORD0000004', 'PRO0000010', 7),
('ODT0000020', 'ORD0000004', 'PRO0000007', 11),
('ODT0000021', 'ORD0000003', 'PRO0000008', 9),
('ODT0000022', 'ORD0000003', 'PRO0000002', 6),
('ODT0000023', 'ORD0000003', 'PRO0000010', 1),
('ODT0000024', 'ORD0000010', 'PRO0000003', 2),
('ODT0000025', 'ORD0000009', 'PRO0000010', 3),
('ODT0000026', 'ORD0000008', 'PRO0000004', 11),
('ODT0000027', 'ORD0000007', 'PRO0000010', 8),
('ODT0000028', 'ORD0000003', 'PRO0000002', 5),
('ODT0000029', 'ORD0000011', 'PRO0000006', 7),
('ODT0000030', 'ORD0000013', 'PRO0000006', 3),
('ODT0000031', 'ORD0000013', 'PRO0000007', 2),
('ODT0000032', 'ORD0000013', 'PRO0000005', 2),
('ODT0000033', 'ORD0000013', 'PRO0000002', 2),
('ODT0000034', 'ORD0000013', 'PRO0000003', 2),
('ODT0000036', 'ORD0000013', 'PRO0000003', 2),
('ODT0000037', 'ORD0000013', 'PRO0000002', 4),
('ODT0000038', 'ORD0000013', 'PRO0000008', 3),
('ODT0000039', 'ORD0000013', 'PRO0000008', 5),
('ODT0000040', 'ORD0000013', 'PRO0000010', 5),
('ODT0000042', 'ORD0000014', 'PRO0000006', 5),
('ODT0000043', 'ORD0000014', 'PRO0000002', 2),
('ODT0000044', 'ORD0000015', 'PRO0000003', 4),
('ODT0000045', 'ORD0000015', 'PRO0000006', 5),
('ODT0000046', 'ORD0000016', 'PRO0000006', 3),
('ODT0000047', 'ORD0000016', 'PRO0000003', 2),
('ODT0000048', 'ORD0000016', 'PRO0000002', 1),
('ODT0000049', 'ORD0000017', 'PRO0000003', 1),
('ODT0000050', 'ORD0000017', 'PRO0000002', 1),
('ODT0000051', 'ORD0000018', 'PRO0000006', 3),
('ODT0000052', 'ORD0000019', 'PRO0000003', 1),
('ODT0000053', 'ORD0000020', 'PRO0000003', 2),
('ODT0000054', 'ORD0000021', 'PRO0000006', 3),
('ODT0000055', 'ORD0000022', 'PRO0000002', 2),
('ODT0000056', 'ORD0000022', 'PRO0000009', 1),
('ODT0000057', 'ORD0000023', 'PRO0000008', 1),
('ODT0000058', 'ORD0000023', 'PRO0000004', 2),
('ODT0000059', 'ORD0000024', 'PRO0000001', 2),
('ODT0000060', 'ORD0000024', 'PRO0000006', 3),
('ODT0000061', 'ORD0000025', 'PRO0000006', 5),
('ODT0000062', 'ORD0000026', 'PRO0000002', 1),
('ODT0000063', 'ORD0000026', 'PRO0000009', 3),
('ODT0000064', 'ORD0000026', 'PRO0000010', 1),
('ODT0000065', 'ORD0000026', 'PRO0000011', 2),
('ODT0000066', 'ORD0000026', 'PRO0000012', 1),
('ODT0000067', 'ORD0000027', 'PRO0000009', 1),
('ODT0000068', 'ORD0000028', 'PRO0000007', 1),
('ODT0000069', 'ORD0000029', 'PRO0000002', 1),
('ODT0000070', 'ORD0000029', 'PRO0000003', 2),
('ODT0000071', 'ORD0000029', 'PRO0000004', 1),
('ODT0000072', 'ORD0000030', 'PRO0000011', 2),
('ODT0000073', 'ORD0000030', 'PRO0000012', 2),
('ODT0000074', 'ORD0000031', 'PRO0000005', 1),
('ODT0000075', 'ORD0000032', 'PRO0000011', 1),
('ODT0000076', 'ORD0000033', 'PRO0000003', 1),
('ODT0000077', 'ORD0000033', 'PRO0000002', 1),
('ODT0000078', 'ORD0000034', 'PRO0000011', 2),
('ODT0000079', 'ORD0000034', 'PRO0000012', 2),
('ODT0000080', 'ORD0000035', 'PRO0000012', 1),
('ODT0000081', 'ORD0000036', 'PRO0000008', 3),
('ODT0000082', 'ORD0000037', 'PRO0000008', 4),
('ODT0000083', 'ORD0000038', 'PRO0000008', 2),
('ODT0000084', 'ORD0000038', 'PRO0000007', 2),
('ODT0000085', 'ORD0000039', 'PRO0000002', 1),
('ODT0000086', 'ORD0000040', 'PRO0000011', 2),
('ODT0000087', 'ORD0000041', 'PRO0000012', 1),
('ODT0000088', 'ORD0000042', 'PRO0000011', 1);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `PAYMENT_ID` varchar(50) NOT NULL,
  `PAYMENT_METHOD_ID` varchar(50) NOT NULL,
  `total_amount` float DEFAULT NULL,
  `change_given` float DEFAULT NULL,
  `DATE_CREATED` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`PAYMENT_ID`, `PAYMENT_METHOD_ID`, `total_amount`, `change_given`, `DATE_CREATED`) VALUES
('PAY0000001', 'PMM0000001', 2199.98, 0, '2023-12-19 14:52:27'),
('PAY0000002', 'PMM0000002', 504.87, 0, '2023-12-19 14:52:27'),
('PAY0000003', 'PMM0000003', 1566.59, 0, '2023-12-20 14:52:27'),
('PAY0000004', 'PMM0000004', 5409.81, 0, '2023-12-18 14:52:27'),
('PAY0000005', 'PMM0000005', 29.98, 0, '2023-12-19 14:52:27'),
('PAY0000006', 'PMM0000003', 99.99, 0, '2023-12-19 14:52:27'),
('PAY0000007', 'PMM0000004', 5679.9, 0, '2023-12-20 00:00:00'),
('PAY0000008', 'PMM0000002', 793.86, 0, '2023-12-19 14:52:27'),
('PAY0000009', 'PMM0000001', 4449.93, 0, '2023-11-28 00:05:19'),
('PAY0000010', 'PMM0000005', 1439.96, 0, '2023-12-19 14:52:27'),
('PAY0000011', 'PMM0000003', 999.9, 0, '2023-12-23 00:07:33'),
('PAY0000012', 'PMM0000002', 524.88, 0, '2023-12-24 00:08:02'),
('PAY0000013', 'PMM0000002', 4203.7, 796.3, '2023-12-25 15:21:21'),
('PAY0000014', 'PMM0000002', 549.93, 50.07, '2023-12-25 15:28:24'),
('PAY0000015', 'PMM0000002', 579.91, 20.09, '2023-12-25 15:32:24'),
('PAY0000016', 'PMM0000002', 364.94, 5.06, '2023-12-26 01:41:19'),
('PAY0000017', 'PMM0000002', 44.98, 0, '2023-12-26 02:18:54'),
('PAY0000018', 'PMM0000002', 299.97, 0.03, '2023-12-26 02:41:16'),
('PAY0000019', 'PMM0000002', 19.99, 0.01, '2023-12-26 02:42:39'),
('PAY0000020', 'PMM0000002', 39.98, 0.02, '2023-12-26 02:43:28'),
('PAY0000021', 'PMM0000002', 299.97, 0.03, '2023-12-26 02:46:37'),
('PAY0000022', 'PMM0000002', 299.97, 0.03, '2023-12-26 13:01:15'),
('PAY0000023', 'PMM0000002', 147.97, 52.03, '2023-12-26 13:05:51'),
('PAY0000024', 'PMM0000002', 2499.95, 500.05, '2023-12-26 16:44:11'),
('PAY0000025', 'PMM0000002', 499.95, 4500.05, '2024-03-19 21:19:14'),
('PAY0000026', 'PMM0000002', 4874.95, 125.05, '2024-03-22 14:48:03'),
('PAY0000027', 'PMM0000002', 249.99, 750.01, '2024-04-03 15:44:30'),
('PAY0000028', 'PMM0000003', 39.99, 60.01, '2024-04-03 15:45:15'),
('PAY0000029', 'PMM0000003', 134.96, 5865.04, '2024-04-08 13:51:24'),
('PAY0000030', 'PMM0000002', 6400, 600, '2024-04-08 22:41:15'),
('PAY0000031', 'PMM0000002', 14.99, 585.01, '2024-04-08 23:44:42'),
('PAY0000032', 'PMM0000002', 200, 400, '2024-04-09 00:09:16'),
('PAY0000033', 'PMM0000002', 40.48, 9.52, '2024-04-09 15:08:02'),
('PAY0000034', 'PMM0000002', 6400, 0, '2024-04-18 19:59:29'),
('PAY0000035', 'PMM0000002', 3000, 0, '2024-04-18 20:27:34'),
('PAY0000036', 'PMM0000002', 23.97, 0.03, '2024-04-18 20:31:44'),
('PAY0000037', 'PMM0000002', 31.96, 0.04, '2024-04-18 20:50:31'),
('PAY0000038', 'PMM0000002', 95.96, 0.04, '2024-04-21 11:21:13'),
('PAY0000039', 'PMM0000002', 24.99, 0.01, '2024-04-21 15:05:18'),
('PAY0000040', 'PMM0000002', 400, 100, '2024-04-21 15:05:48'),
('PAY0000041', 'PMM0000002', 3000, 1000, '2024-04-21 15:06:38'),
('PAY0000042', 'PMM0000002', 180, 0, '2024-04-21 15:27:41');

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `payment_method_id` varchar(50) NOT NULL,
  `payment_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `payment_method`
--

INSERT INTO `payment_method` (`payment_method_id`, `payment_name`) VALUES
('PMM0000001', 'Credit Card'),
('PMM0000002', 'Cash'),
('PMM0000003', 'PayPal'),
('PMM0000004', 'Apple Pay'),
('PMM0000005', 'Google Pay');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` varchar(50) NOT NULL,
  `NAME` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `QTY_STOCK` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `retail_price` float DEFAULT NULL,
  `DATE_CREATED` timestamp NOT NULL DEFAULT current_timestamp(),
  `barcode` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `NAME`, `DESCRIPTION`, `QTY_STOCK`, `price`, `CATEGORY_ID`, `image`, `retail_price`, `DATE_CREATED`, `barcode`) VALUES
('PRO0000001', 'Laptop', 'High-performance laptop', 8, 999.99, 1, 'PRO0000001.png', 1099.99, '2023-12-19 07:52:27', 'PRO0000001_barcode.png'),
('PRO0000002', 'T-shirt', 'Cotton T-shirt', 44, 19.99, 2, 'PRO0000002.png', 24.99, '2023-12-19 07:52:27', 'PRO0000002_barcode.png'),
('PRO0000003', 'Book', 'Bestseller novel', 10, 14.99, 3, 'PRO0000003.png', 19.99, '2023-12-19 07:52:27', 'PRO0000003_barcode.png'),
('PRO0000004', 'Vase', 'Elegant home decor', 12, 49.99, 4, 'PRO0000004.png', 69.99, '2023-12-19 07:52:27', 'PRO0000004_barcode.png'),
('PRO0000005', 'Lipstick', 'Matte finish', 25, 9.99, 5, 'PRO0000005.png', 14.99, '2023-12-19 07:52:27', 'PRO0000005_barcode.png'),
('PRO0000006', 'Running Shoes', 'Comfortable running shoes', 15, 79.99, 6, 'PRO0000006.png', 99.99, '2023-12-19 07:52:27', 'PRO0000006_barcode.png'),
('PRO0000007', 'Toy Car', 'Remote-controlled car', 37, 29.99, 7, 'PRO0000007.png', 39.99, '2023-12-19 07:52:27', 'PRO0000007_barcode.png'),
('PRO0000008', 'Chocolate', 'Assorted chocolates', 40, 4.99, 8, 'PRO0000008.png', 7.99, '2023-12-19 07:52:27', 'PRO0000008_barcode.png'),
('PRO0000009', 'Coffee Table', 'Modern furniture', 7, 199.99, 9, 'PRO0000009.png', 249.99, '2023-12-19 07:52:27', 'PRO0000009_barcode.png'),
('PRO0000010', 'Diamond Ring', '18k gold', 4, 499.99, 10, 'PRO0000010.png', 699.99, '2023-12-19 07:52:27', 'PRO0000010_barcode.png'),
('PRO0000011', 'Pant', 'This is pants from UK', 90, 100, 2, 'PRO0000011.png', 200, '2023-12-25 17:02:57', 'PRO0000011_barcode.png'),
('PRO0000012', 'MacBook Air Pro', 'Mac Book Air Black', 43, 2000, 1, 'PRO0000012.png', 3000, '2023-12-26 09:25:31', 'PRO0000012_barcode.png'),
('PRO0000013', 'T-Shirt Vietnam', 'This is a Vietnam T-Shirt 2', 121, 20, 2, 'PRO0000013.png', 40, '2024-04-23 04:12:49', 'PRO0000013_barcode.png');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`ROLE_ID`, `ROLE`) VALUES
(1, 'Admin'),
(2, 'Staff');

-- --------------------------------------------------------

--
-- Table structure for table `role_seq`
--

CREATE TABLE `role_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `role_seq`
--

INSERT INTO `role_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `TRANSACTION_ID` varchar(50) NOT NULL,
  `PAYMENT_ID` varchar(50) NOT NULL,
  `STATUS` varchar(50) NOT NULL,
  `ORDER_ID` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TRANSACTION_ID`, `PAYMENT_ID`, `STATUS`, `ORDER_ID`) VALUES
('TRA0000001', 'PAY0000001', 'Completed', 'ORD0000001'),
('TRA0000002', 'PAY0000002', 'Completed', 'ORD0000002'),
('TRA0000003', 'PAY0000003', 'Completed', 'ORD0000003'),
('TRA0000004', 'PAY0000004', 'Completed', 'ORD0000004'),
('TRA0000005', 'PAY0000005', 'Completed', 'ORD0000005'),
('TRA0000006', 'PAY0000006', 'Completed', 'ORD0000006'),
('TRA0000007', 'PAY0000007', 'Completed', 'ORD0000007'),
('TRA0000008', 'PAY0000008', 'Completed', 'ORD0000008'),
('TRA0000009', 'PAY0000009', 'Completed', 'ORD0000009'),
('TRA0000010', 'PAY0000010', 'Completed', 'ORD0000010'),
('TRA0000011', 'PAY0000011', 'Completed', 'ORD0000011'),
('TRA0000012', 'PAY0000012', 'Completed', 'ORD0000012'),
('TRA0000013', 'PAY0000013', 'Completed', 'ORD0000013'),
('TRA0000014', 'PAY0000014', 'Completed', 'ORD0000014'),
('TRA0000015', 'PAY0000015', 'Completed', 'ORD0000015'),
('TRA0000016', 'PAY0000016', 'Completed', 'ORD0000016'),
('TRA0000017', 'PAY0000017', 'Completed', 'ORD0000017'),
('TRA0000018', 'PAY0000018', 'Completed', 'ORD0000018'),
('TRA0000019', 'PAY0000019', 'Completed', 'ORD0000019'),
('TRA0000020', 'PAY0000020', 'Completed', 'ORD0000020'),
('TRA0000021', 'PAY0000021', 'Completed', 'ORD0000021'),
('TRA0000022', 'PAY0000022', 'Completed', 'ORD0000022'),
('TRA0000023', 'PAY0000023', 'Completed', 'ORD0000023'),
('TRA0000024', 'PAY0000024', 'Completed', 'ORD0000024'),
('TRA0000025', 'PAY0000025', 'Completed', 'ORD0000025'),
('TRA0000026', 'PAY0000026', 'Completed', 'ORD0000026'),
('TRA0000027', 'PAY0000027', 'Completed', 'ORD0000027'),
('TRA0000028', 'PAY0000028', 'Completed', 'ORD0000028'),
('TRA0000029', 'PAY0000029', 'Completed', 'ORD0000029'),
('TRA0000030', 'PAY0000030', 'Completed', 'ORD0000030'),
('TRA0000031', 'PAY0000031', 'Completed', 'ORD0000031'),
('TRA0000032', 'PAY0000032', 'Completed', 'ORD0000032'),
('TRA0000033', 'PAY0000033', 'Completed', 'ORD0000033'),
('TRA0000034', 'PAY0000034', 'Completed', 'ORD0000034'),
('TRA0000035', 'PAY0000035', 'Completed', 'ORD0000035'),
('TRA0000036', 'PAY0000036', 'Completed', 'ORD0000036'),
('TRA0000037', 'PAY0000037', 'Completed', 'ORD0000037'),
('TRA0000038', 'PAY0000038', 'Completed', 'ORD0000038'),
('TRA0000039', 'PAY0000039', 'Completed', 'ORD0000039'),
('TRA0000040', 'PAY0000040', 'Completed', 'ORD0000040'),
('TRA0000041', 'PAY0000041', 'Completed', 'ORD0000041'),
('TRA0000042', 'PAY0000042', 'Completed', 'ORD0000042');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `USER_ID` varchar(50) NOT NULL,
  `FIRST_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `PHONE_NUMBER` varchar(20) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `ACCOUNT_ID` varchar(50) NOT NULL,
  `birthday` date DEFAULT NULL,
  `GENDER` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`USER_ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PHONE_NUMBER`, `ADDRESS`, `image`, `ACCOUNT_ID`, `birthday`, `GENDER`) VALUES
('USE0000001', 'Admin 2', 'Tạ', 'admin@example.com', '0987654321', 'Thành phố Hồ Chí Minh', 'USE0000001.png', 'ACC0000001', '1990-02-02', 'Male'),
('USE0000002', 'Regular', 'Senna', 'user@example.com', '555-user', 'Tỉnh Nghệ An', 'user_profile.png', 'ACC0000002', '1995-05-05', 'Female'),
('USE0000003', 'Test', 'User', 'test@example.com', '555-test', 'Tỉnh Sóc Trăng', 'user_profile.png', 'ACC0000003', '1988-10-10', 'Male'),
('USE0000004', 'Demo', 'User', 'demo@example.com', '555-demo', 'Thành phố Hải Phòng', 'user_profile.png', 'ACC0000004', '1985-06-15', 'Female'),
('USE0000005', 'John', 'Doe', 'john.doe@example.com', '555-john', 'Tỉnh Hưng Yên', 'user_profile.png', 'ACC0000005', '1978-03-20', 'Male'),
('USE0000006', 'Jane', 'Smith', 'jane.smith@example.com', '555-jane', 'Thành phố Hồ Chí Minh', 'user_profile.png', 'ACC0000006', '1982-07-25', 'Female'),
('USE0000007', 'Bob', 'Johnson', 'bob.johnson@example.com', '555-bob', 'Tỉnh Gia Lai', 'user_profile.png', 'ACC0000007', '1993-12-30', 'Male'),
('USE0000008', 'Alice', 'Brown', 'alice.brown@example.com', '555-alice', 'Tỉnh Bà Rịa - Vũng Tàu', 'user_profile.png', 'ACC0000008', '1980-05-18', 'Female'),
('USE0000009', 'Charlie', 'White', 'charlie.white@example.com', '555-charlie', 'Tỉnh Bắc Giang', 'user_profile.png', 'ACC0000009', '1987-09-22', 'Male'),
('USE0000010', 'Eva', 'Green', 'eva.green@example.com', '555-eva', 'Tỉnh Lào Cai', 'user_profile.png', 'ACC0000010', '1991-04-12', 'Female'),
('USE0000011', 'Tạ', 'Tiến Đạt', 'tadat290903@gmail.com', '0356779197', 'Tỉnh Bắc Ninh', 'user_profile.png', 'ACC0000011', '2024-01-01', 'Male'),
('USE0000012', 'Tạ', 'Tiến Đạt', 'jakejohn3004@gmail.com', '0123456789', 'Tỉnh Bến Tre', 'user_profile.png', 'ACC0000012', '2024-03-13', 'Male'),
('USE0000013', 'Tạ', 'Tiến Đạt', 'tiendat79197@gmail.com', '0213654789', 'Thành phố Hồ Chí Minh', 'user_profile.png', 'ACC0000013', '2024-03-07', 'Male'),
('USE0000014', 'Tạ', 'Tiến Đạt', 'tadat290903@gmail.com', '0356779197', 'Tỉnh Bến Tre', 'user_profile.png', 'ACC0000014', '2024-04-04', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `voucher`
--

CREATE TABLE `voucher` (
  `VOUCHER_ID` int(50) NOT NULL,
  `VOUCHER_NAME` varchar(500) DEFAULT NULL,
  `VOUCHER_DISCOUNT` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `voucher`
--

INSERT INTO `voucher` (`VOUCHER_ID`, `VOUCHER_NAME`, `VOUCHER_DISCOUNT`) VALUES
(1, 'HAPPY10', '10'),
(2, 'HAPPY20', '20'),
(3, 'HAPPY30', '30');

-- --------------------------------------------------------

--
-- Table structure for table `voucher_seq`
--

CREATE TABLE `voucher_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `voucher_seq`
--

INSERT INTO `voucher_seq` (`next_val`) VALUES
(1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`ACCOUNT_ID`),
  ADD KEY `fk_account_role` (`ROLE_ID`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`CATEGORY_ID`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CUSTOMER_ID`);

--
-- Indexes for table `customers_voucher`
--
ALTER TABLE `customers_voucher`
  ADD PRIMARY KEY (`CUSTOMER_VOUCHER_ID`,`VOUCHER_ID`,`CUSTOMER_ID`);

--
-- Indexes for table `log_history`
--
ALTER TABLE `log_history`
  ADD PRIMARY KEY (`LOG_ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ORDER_ID`),
  ADD KEY `fk_order_user` (`USER_ID`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`ORDER_DETAILS_ID`,`ORDER_ID`,`PRODUCT_ID`) USING BTREE;

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`PAYMENT_ID`),
  ADD KEY `fk_payment_method` (`PAYMENT_METHOD_ID`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`payment_method_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`PRODUCT_ID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`ROLE_ID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`TRANSACTION_ID`),
  ADD KEY `fk_transaction_order` (`ORDER_ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`USER_ID`),
  ADD KEY `fk_user_account` (`ACCOUNT_ID`);

--
-- Indexes for table `voucher`
--
ALTER TABLE `voucher`
  ADD PRIMARY KEY (`VOUCHER_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `CATEGORY_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `voucher`
--
ALTER TABLE `voucher`
  MODIFY `VOUCHER_ID` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_account_role` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_order_user` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_method` FOREIGN KEY (`PAYMENT_METHOD_ID`) REFERENCES `payment_method` (`payment_method_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `fk_transaction_order` FOREIGN KEY (`ORDER_ID`) REFERENCES `orders` (`ORDER_ID`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_account` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ACCOUNT_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

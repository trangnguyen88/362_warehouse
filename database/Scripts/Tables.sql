CREATE DATABASE `warehouse` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(100) NOT NULL,
  `customer_address` varchar(4000) NOT NULL,
  `customer_contact` varchar(100) DEFAULT NULL,
  `customer_phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `idx_customer_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `department` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `total_amount` double NOT NULL,
  `order_date` date NOT NULL,
  `expected_date` date DEFAULT NULL,
  `delivered_date` date DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `IX_ORDER_ID` (`order_id`),
  KEY `FK_VENDOR_ID_idx` (`vendor_id`),
  KEY `FK_EMPLOYEE_ID_idx` (`employee_id`),
  CONSTRAINT `FK_EMPLOYEE_ID` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VENDOR_ID` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_details` (
  `order_detail_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `IX_ORDER_DETAILS` (`order_detail_id`),
  KEY `FK_ORDER_ID_idx` (`order_id`),
  KEY `FK_PRODUCT_ID_idx` (`product_id`),
  CONSTRAINT `FK_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product_master` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product_master` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(500) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `product_description` varchar(4000) DEFAULT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `idx_product_master_product_id` (`product_id`),
  KEY `FK_VENDOR_ID_idx` (`vendor_id`),
  CONSTRAINT `FK1_VENDOR_ID` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Product Master Table';

CREATE TABLE `sales` (
  `invoice_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `invoice_date` date NOT NULL,
  `shipping_date` date DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `idx_sales_invoice_id` (`invoice_id`),
  KEY `FK_CUSTOMER_ID_idx` (`customer_id`),
  KEY `FK_EMPLOYEE_ID1_idx` (`employee_id`),
  CONSTRAINT `FK_CUSTOMER_ID` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EMPLOYEE_ID1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sales_details` (
  `invoice_details_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`invoice_details_id`),
  KEY `idx_sales_details_invoice_details_id` (`invoice_details_id`),
  KEY `FK_INVOICE_ID_idx` (`invoice_id`),
  KEY `FK1_PRODUCT_ID_idx` (`product_id`),
  KEY `FK1_CUSTOMER_ID_idx` (`customer_id`),
  CONSTRAINT `FK1_CUSTOMER_ID` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK1_PRODUCT_ID` FOREIGN KEY (`product_id`) REFERENCES `product_master` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_INVOICE_ID` FOREIGN KEY (`invoice_id`) REFERENCES `sales` (`invoice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `vendor` (
  `vendor_id` int(11) NOT NULL,
  `vendor_name` varchar(100) NOT NULL,
  `vendor_address` varchar(4000) NOT NULL,
  `vendor_contact` varchar(45) DEFAULT NULL,
  `vendor_telephone` varchar(45) DEFAULT NULL,
  `vendor_fax` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`vendor_id`),
  KEY `idx_vendor_vendor_id` (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


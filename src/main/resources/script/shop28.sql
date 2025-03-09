CREATE DATABASE shop28;
use shop28;

CREATE TABLE `users` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255),
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  `phone` INT NOT NULL,
  `is_lock` boolean DEFAULT FALSE,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `addresses` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `number` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `ward` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `roles` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `user_has_role` (
  `id` integer PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `role_id` integer NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `products` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `category_id` integer,
  `description` TEXT NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `star` float NOT NULL DEFAULT 0,
  `price` integer NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `product_details` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `product_id` integer NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `color_id` integer NOT NULL,
  `size_id` integer NOT NULL,
  `price` integer NOT NULL,
  `stock_quantity` integer NOT NULL,
  `sold_quantity` integer NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `categories` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `feedbacks` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `order_detail_id` INTEGER NOT NULL,
  `user_id` INTEGER NOT NULL,
  `star` INTEGER NOT NULL,
  `comment` TEXT,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `sizes` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `colors` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `orders` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `address_id` integer NOT NULL,
  `title` TEXT NOT NULL,
  `total_price` integer NOT NULL,
  `status` varchar(50) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `order_details` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `order_id` integer NOT NULL,
  `product_detail_id` integer NOT NULL,
  `quantity` integer NOT NULL,
  `price` integer NOT NULL,
  `status` varchar(50) NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

CREATE TABLE `cart_items` (
  `id` integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `product_detail_id` integer NOT NULL,
  `quantity` integer NOT NULL,
  `price` integer NOT NULL,
  `create_at` datetime,
  `update_at` datetime
);

ALTER TABLE `user_has_role` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `user_has_role` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE;

ALTER TABLE `product_details` ADD FOREIGN KEY (`size_id`) REFERENCES `sizes` (`id`) ON DELETE CASCADE;

ALTER TABLE `product_details` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart_items` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart_items` ADD FOREIGN KEY (`product_detail_id`) REFERENCES `product_details` (`id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `order_details` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE;

ALTER TABLE `order_details` ADD FOREIGN KEY (`product_detail_id`) REFERENCES `product_details` (`id`) ON DELETE CASCADE;

ALTER TABLE `product_details` ADD FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`) ON DELETE CASCADE;

ALTER TABLE `feedbacks` ADD FOREIGN KEY (`order_detail_id`) REFERENCES `order_details` (`id`) ON DELETE CASCADE;

ALTER TABLE `feedbacks` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE CASCADE;

ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE;

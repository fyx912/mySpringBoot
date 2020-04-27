CREATE TABLE `ding`.`t_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `orderNo` VARCHAR(45) NULL,
  `orderStatus` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `ding`.`t_pay` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `payNo` VARCHAR(45) NULL,
  `payStatus` VARCHAR(45) NULL,
  `money` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

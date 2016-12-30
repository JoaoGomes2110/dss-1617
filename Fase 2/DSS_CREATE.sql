-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema divide_despesas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema divide_despesas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `divide_despesas` DEFAULT CHARACTER SET utf8 ;
USE `divide_despesas` ;

-- -----------------------------------------------------
-- Table `divide_despesas`.`administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`administrador` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `divide_despesas`.`morador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`morador` (
  `username` VARCHAR(15) NOT NULL,
  `password` VARCHAR(15) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `data_chegada` DATE NOT NULL,
  `data_saida` DATE NULL,
  `saldo` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `divide_despesas`.`despesa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`despesa` (
  `id` INT(11) NOT NULL,
  `info` VARCHAR(45) NOT NULL,
  `valor` DECIMAL(5,2) UNSIGNED NOT NULL,
  `data_emissao` DATE NOT NULL,
  `data_limite` DATE NOT NULL,
  `data_pagamento` DATE NULL DEFAULT NULL,
  `tipo` VARCHAR(30) NOT NULL,
  `morador` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `morador_idx` (`morador` ASC),
  CONSTRAINT `morador`
    FOREIGN KEY (`morador`)
    REFERENCES `divide_despesas`.`morador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `divide_despesas`.`quarto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`quarto` (
  `id` INT(10) UNSIGNED NOT NULL,
  `preco` DECIMAL(5,2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `divide_despesas`.`moradorquarto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`moradorquarto` (
  `quarto` INT(10) UNSIGNED NOT NULL,
  `morador` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`quarto`, `morador`),
  INDEX `quarto_idx` (`quarto` ASC),
  INDEX `morador_quarto` (`morador` ASC),
  CONSTRAINT `morador_quarto`
    FOREIGN KEY (`morador`)
    REFERENCES `divide_despesas`.`morador` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `quarto`
    FOREIGN KEY (`quarto`)
    REFERENCES `divide_despesas`.`quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `divide_despesas`.`senhorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `divide_despesas`.`senhorio` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(15) NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

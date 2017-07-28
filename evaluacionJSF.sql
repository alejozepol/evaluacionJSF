-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.1.19-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win32
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para db_sesiones
CREATE DATABASE IF NOT EXISTS `db_sesiones` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_sesiones`;

-- Volcando estructura para tabla db_sesiones.adjuntos
CREATE TABLE IF NOT EXISTS `adjuntos` (
  `idAdjunto` int(11) NOT NULL AUTO_INCREMENT,
  `ruta` varchar(255) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAdjunto`),
  KEY `FK_documentos_usuario` (`idUsuario`),
  CONSTRAINT `FK_documentos_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.adjuntos: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `adjuntos` DISABLE KEYS */;
/*!40000 ALTER TABLE `adjuntos` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.permisos
CREATE TABLE IF NOT EXISTS `permisos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `url` text,
  `icon` varchar(45) DEFAULT NULL,
  `permiso_padre` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_permisos_permisos1_idx` (`permiso_padre`),
  CONSTRAINT `fk_permisos_permisos1` FOREIGN KEY (`permiso_padre`) REFERENCES `permisos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.permisos: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT IGNORE INTO `permisos` (`id`, `nombre`, `url`, `icon`, `permiso_padre`) VALUES
	(1, 'Usuario', '', 'fa-user', NULL),
	(2, 'Reportes', NULL, 'fa-home', NULL),
	(3, 'Adjuntos', NULL, NULL, NULL),
	(11, 'Consultar usuarios', 'List.xhtml', NULL, 1),
	(12, 'Registrar usuario', 'Create.xhtml', NULL, 1),
	(21, 'Productos más vendidos', 'app/reportes/p_mas_vendidos.xhtml', NULL, 2),
	(22, 'Clientes VIP', 'app/reportes/clientes_VIP.xhtml', NULL, 2);
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.roles: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT IGNORE INTO `roles` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'Administrador', NULL),
	(2, 'Aspirante', NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.roles_has_permisos
CREATE TABLE IF NOT EXISTS `roles_has_permisos` (
  `id_rol` int(11) NOT NULL,
  `id_permiso` int(11) NOT NULL,
  PRIMARY KEY (`id_rol`,`id_permiso`),
  KEY `fk_roles_has_permisos_permisos1_idx` (`id_permiso`),
  KEY `fk_roles_has_permisos_roles1_idx` (`id_rol`),
  CONSTRAINT `fk_roles_has_permisos_permisos1` FOREIGN KEY (`id_permiso`) REFERENCES `permisos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_roles_has_permisos_roles1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.roles_has_permisos: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `roles_has_permisos` DISABLE KEYS */;
INSERT IGNORE INTO `roles_has_permisos` (`id_rol`, `id_permiso`) VALUES
	(1, 1),
	(1, 2),
	(1, 11),
	(1, 12),
	(1, 21),
	(1, 22),
	(2, 2),
	(2, 21);
/*!40000 ALTER TABLE `roles_has_permisos` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.roles_has_usuarios
CREATE TABLE IF NOT EXISTS `roles_has_usuarios` (
  `id_rol` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_rol`,`id_usuario`),
  KEY `fk_roles_has_usuarios_usuarios1_idx` (`id_usuario`),
  KEY `fk_roles_has_usuarios_roles1_idx` (`id_rol`),
  CONSTRAINT `fk_roles_has_usuarios_roles1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_roles_has_usuarios_usuarios1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.roles_has_usuarios: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `roles_has_usuarios` DISABLE KEYS */;
INSERT IGNORE INTO `roles_has_usuarios` (`id_rol`, `id_usuario`) VALUES
	(1, 1),
	(1, 3),
	(2, 1),
	(2, 2);
/*!40000 ALTER TABLE `roles_has_usuarios` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.tiposdocumentos
CREATE TABLE IF NOT EXISTS `tiposdocumentos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) NOT NULL,
  `sigla` varchar(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.tiposdocumentos: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `tiposdocumentos` DISABLE KEYS */;
INSERT IGNORE INTO `tiposdocumentos` (`id`, `tipo`, `sigla`) VALUES
	(1, 'Cédula de ciudanía', 'C.C.'),
	(2, 'Tarjeta de identidad', 'T.I.');
/*!40000 ALTER TABLE `tiposdocumentos` ENABLE KEYS */;

-- Volcando estructura para tabla db_sesiones.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_documento` int(11) NOT NULL,
  `documento` int(11) NOT NULL,
  `primer_nombre` varchar(45) NOT NULL,
  `segundo_nombre` varchar(45) DEFAULT NULL,
  `primer_apellido` varchar(45) NOT NULL,
  `segundo_apellido` varchar(45) DEFAULT NULL,
  `correo` text NOT NULL,
  `clave` varchar(45) NOT NULL,
  `estado` int(11) NOT NULL,
  `tipoUsuario` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuarios_tiposdocumentos_idx` (`tipo_documento`),
  CONSTRAINT `fk_usuarios_tiposdocumentos` FOREIGN KEY (`tipo_documento`) REFERENCES `tiposdocumentos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla db_sesiones.usuarios: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT IGNORE INTO `usuarios` (`id`, `tipo_documento`, `documento`, `primer_nombre`, `segundo_nombre`, `primer_apellido`, `segundo_apellido`, `correo`, `clave`, `estado`, `tipoUsuario`) VALUES
	(1, 1, 123456, 'Saul', 'Ernesto', 'Garavito', NULL, 'sernestog@miapp.com', '1234', 1, ''),
	(2, 1, 234567, 'Maria', NULL, 'Sandoval', 'Gutierrez', 'msandovalg@miapp.com', '2345', 1, ''),
	(3, 1, 345678, 'Yuri', 'Mayerly', 'Rodriguez', 'Prieto', 'ymayerlyr@miapp.com', '4567', 2, '');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

-- Esquema inicial simplificado para GlobalHands
-- MySQL 8.0+
-- Incluye usuarios voluntarios, organizaciones, oportunidades,
-- solicitudes y categorías de oportunidades.

CREATE DATABASE IF NOT EXISTS `globalhands`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `globalhands`;

CREATE TABLE `usuarios` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(120) NOT NULL,
  `apellido` VARCHAR(120) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(25) DEFAULT NULL,
  `pais` VARCHAR(100) DEFAULT NULL,
  `ciudad` VARCHAR(100) DEFAULT NULL,
  `fecha_registro` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_usuarios_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `organizaciones` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(150) NOT NULL,
  `descripcion` TEXT DEFAULT NULL,
  `email` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(25) DEFAULT NULL,
  `ciudad` VARCHAR(100) DEFAULT NULL,
  `pais` VARCHAR(100) DEFAULT NULL,
  `fecha_registro` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_organizaciones_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `voluntariados` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `organizacion_id` BIGINT UNSIGNED NOT NULL,
  `titulo` VARCHAR(180) NOT NULL,
  `descripcion` TEXT NOT NULL,
  `modalidad` VARCHAR(20) NOT NULL DEFAULT 'presencial',
  `ciudad` VARCHAR(100) DEFAULT NULL,
  `pais` VARCHAR(100) DEFAULT NULL,
  `fecha_inicio` DATE DEFAULT NULL,
  `fecha_fin` DATE DEFAULT NULL,
  `plazas` INT UNSIGNED DEFAULT NULL,
  `estado` VARCHAR(20) NOT NULL DEFAULT 'publicado',
  `fecha_creacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_voluntariados_organizacion` (`organizacion_id`),
  KEY `idx_voluntariados_estado` (`estado`),
  CONSTRAINT `fk_voluntariados_organizacion`
    FOREIGN KEY (`organizacion_id`) REFERENCES `organizaciones` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_voluntariados_modalidad`
    CHECK (`modalidad` IN ('presencial', 'online', 'hibrida')),
  CONSTRAINT `chk_voluntariados_estado`
    CHECK (`estado` IN ('borrador', 'publicado', 'cerrado')),
  CONSTRAINT `chk_voluntariados_fechas`
    CHECK (`fecha_fin` IS NULL OR `fecha_inicio` IS NULL OR `fecha_fin` >= `fecha_inicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `categorias` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_categorias_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `voluntariado_categorias` (
  `voluntariado_id` BIGINT UNSIGNED NOT NULL,
  `categoria_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`voluntariado_id`, `categoria_id`),
  KEY `idx_voluntariado_categorias_categoria` (`categoria_id`),
  CONSTRAINT `fk_voluntariado_categorias_voluntariado`
    FOREIGN KEY (`voluntariado_id`) REFERENCES `voluntariados` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_voluntariado_categorias_categoria`
    FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `solicitudes` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_id` BIGINT UNSIGNED NOT NULL,
  `voluntariado_id` BIGINT UNSIGNED NOT NULL,
  `mensaje` TEXT DEFAULT NULL,
  `fecha_solicitud` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` VARCHAR(20) NOT NULL DEFAULT 'pendiente',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_solicitudes_usuario_voluntariado` (`usuario_id`, `voluntariado_id`),
  KEY `idx_solicitudes_voluntariado` (`voluntariado_id`),
  KEY `idx_solicitudes_estado` (`estado`),
  CONSTRAINT `fk_solicitudes_usuario`
    FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_solicitudes_voluntariado`
    FOREIGN KEY (`voluntariado_id`) REFERENCES `voluntariados` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_solicitudes_estado`
    CHECK (`estado` IN ('pendiente', 'aceptada', 'rechazada'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Datos de ejemplo para probar la API. Se pueden borrar cuando se quiera.
INSERT INTO `categorias` (`nombre`, `descripcion`) VALUES
  ('Medio ambiente', 'Acciones de cuidado y conservación del entorno.'),
  ('Educación', 'Apoyo educativo y actividades formativas.'),
  ('Apoyo social', 'Acompañamiento y ayuda a personas y comunidades.');

INSERT INTO `organizaciones` (`nombre`, `descripcion`, `email`, `telefono`, `ciudad`, `pais`) VALUES
  ('Global Hands Demo', 'Organización de ejemplo para probar la API.', 'demo@globalhands.test', NULL, 'Madrid', 'España');

INSERT INTO `voluntariados`
  (`organizacion_id`, `titulo`, `descripcion`, `modalidad`, `ciudad`, `pais`, `fecha_inicio`, `fecha_fin`, `plazas`, `estado`)
VALUES
  (1, 'Limpieza de parque urbano', 'Participa en una jornada de limpieza y cuidado de zonas verdes.', 'presencial', 'Madrid', 'España', NULL, NULL, 12, 'publicado'),
  (1, 'Tutorías de apoyo escolar', 'Ayuda a estudiantes con sus tareas y hábitos de estudio.', 'hibrida', 'Madrid', 'España', NULL, NULL, 8, 'publicado'),
  (1, 'Traducción de recursos educativos', 'Colabora en la traducción de materiales educativos desde casa.', 'online', NULL, 'España', NULL, NULL, 20, 'publicado');

INSERT INTO `voluntariado_categorias` (`voluntariado_id`, `categoria_id`) VALUES
  (1, 1),
  (2, 2),
  (3, 2);

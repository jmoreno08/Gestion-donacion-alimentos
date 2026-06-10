-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-06-2026 a las 17:17:57
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_donacion_alimentos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `beneficiarios`
--

CREATE TABLE `beneficiarios` (
  `id_beneficiario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `documento` varchar(20) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  `numero_integrantes` int(11) DEFAULT NULL,
  `condicion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `beneficiarios`
--

INSERT INTO `beneficiarios` (`id_beneficiario`, `nombre`, `documento`, `telefono`, `direccion`, `numero_integrantes`, `condicion`) VALUES
(1, 'Ana Torres', '100123456', '3001112233', 'Cali', 4, 'Bajos recursos'),
(2, 'Luis Perez', '100123457', '3001112234', 'Palmira', 5, 'Desplazado'),
(3, 'Maria Rojas', '100123458', '3001112235', 'Yumbo', 3, 'Madre cabeza de hogar'),
(4, 'Carlos Diaz', '100123459', '3001112236', 'Cali', 6, 'Bajos recursos'),
(5, 'Laura Gomez', '100123460', '3001112237', 'Jamundi', 2, 'Adulto mayor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `donaciones`
--

CREATE TABLE `donaciones` (
  `id_donacion` int(11) NOT NULL,
  `id_donante` int(11) NOT NULL,
  `fecha_donacion` date NOT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `donaciones`
--

INSERT INTO `donaciones` (`id_donacion`, `id_donante`, `fecha_donacion`, `estado`, `observacion`) VALUES
(1, 1, '2026-06-01', 'Recibida', 'Donacion alimentos'),
(2, 2, '2026-06-02', 'Recibida', 'Donacion empresarial'),
(3, 3, '2026-06-03', 'Pendiente', 'Validacion'),
(4, 4, '2026-06-04', 'Entregada', 'Distribuida'),
(5, 5, '2026-06-05', 'Recibida', 'Ayuda social');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `donantes`
--

CREATE TABLE `donantes` (
  `id_donante` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo_donante` varchar(50) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `donantes`
--

INSERT INTO `donantes` (`id_donante`, `nombre`, `tipo_donante`, `telefono`, `correo`, `direccion`) VALUES
(1, 'Juan Perez', 'Persona', '3009999999', 'juan@gmail.com', 'Cali'),
(2, 'Maria Gomez', 'Empresa', '3002222222', 'maria@gmail.com', 'Bogota'),
(3, 'Carlos Ruiz', 'Persona', '3003333333', 'carlos@gmail.com', 'Medellin'),
(4, 'Fundacion Esperanza', 'Fundacion', '3004444444', 'fundacion@gmail.com', 'Cali'),
(5, 'Empresa XYZ', 'Empresa', '3005555555', 'xyz@gmail.com', 'Barranquilla');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entregas`
--

CREATE TABLE `entregas` (
  `id_entrega` int(11) NOT NULL,
  `id_beneficiario` int(11) NOT NULL,
  `fecha_entrega` date NOT NULL,
  `responsable` varchar(100) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `entregas`
--

INSERT INTO `entregas` (`id_entrega`, `id_beneficiario`, `fecha_entrega`, `responsable`, `observacion`) VALUES
(1, 1, '2026-06-05', 'Carlos Ramirez', 'Entrega mensual'),
(2, 2, '2026-06-06', 'Ana Torres', 'Ayuda alimentaria'),
(3, 3, '2026-06-07', 'Luis Gomez', 'Entrega programada'),
(4, 4, '2026-06-08', 'Maria Perez', 'Apoyo social'),
(5, 5, '2026-06-09', 'Jorge Ruiz', 'Beneficio comunitario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `nombre_producto` varchar(100) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `unidad_medida` varchar(30) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre_producto`, `categoria`, `unidad_medida`, `fecha_vencimiento`) VALUES
(1, 'Arroz', 'Granos', 'Kg', '2027-01-01'),
(2, 'Frijoles', 'Granos', 'Kg', '2027-02-01'),
(3, 'Leche', 'Lacteos', 'Litro', '2026-12-01'),
(4, 'Aceite', 'Abarrotes', 'Litro', '2027-03-01'),
(5, 'Atun', 'Enlatados', 'Unidad', '2028-01-01');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `beneficiarios`
--
ALTER TABLE `beneficiarios`
  ADD PRIMARY KEY (`id_beneficiario`);

--
-- Indices de la tabla `donaciones`
--
ALTER TABLE `donaciones`
  ADD PRIMARY KEY (`id_donacion`),
  ADD KEY `id_donante` (`id_donante`);

--
-- Indices de la tabla `donantes`
--
ALTER TABLE `donantes`
  ADD PRIMARY KEY (`id_donante`);

--
-- Indices de la tabla `entregas`
--
ALTER TABLE `entregas`
  ADD PRIMARY KEY (`id_entrega`),
  ADD KEY `id_beneficiario` (`id_beneficiario`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `beneficiarios`
--
ALTER TABLE `beneficiarios`
  MODIFY `id_beneficiario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `donaciones`
--
ALTER TABLE `donaciones`
  MODIFY `id_donacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `donantes`
--
ALTER TABLE `donantes`
  MODIFY `id_donante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `entregas`
--
ALTER TABLE `entregas`
  MODIFY `id_entrega` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `donaciones`
--
ALTER TABLE `donaciones`
  ADD CONSTRAINT `donaciones_ibfk_1` FOREIGN KEY (`id_donante`) REFERENCES `donantes` (`id_donante`);

--
-- Filtros para la tabla `entregas`
--
ALTER TABLE `entregas`
  ADD CONSTRAINT `entregas_ibfk_1` FOREIGN KEY (`id_beneficiario`) REFERENCES `beneficiarios` (`id_beneficiario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

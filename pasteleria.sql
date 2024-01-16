-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-01-2024 a las 00:45:25
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pasteleria`
--
CREATE DATABASE IF NOT EXISTS `pasteleria` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `pasteleria`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pasteles`
--

CREATE TABLE `pasteles` (
  `Pastel_ID` int(11) NOT NULL,
  `Titulo_Pastel` varchar(50) DEFAULT NULL,
  `Descripcion_Pastel` varchar(300) DEFAULT NULL,
  `Imagen_Pastel` varchar(100) DEFAULT NULL,
  `Costo_u_Pastel` double DEFAULT NULL,
  `Stock_Pastel` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pasteles`
--

INSERT INTO `pasteles` (`Pastel_ID`, `Titulo_Pastel`, `Descripcion_Pastel`, `Imagen_Pastel`, `Costo_u_Pastel`, `Stock_Pastel`) VALUES
(1, 'Mermaid', 'Pastel de frambuesa, con chocolate, decorado con chocolate blanco y polvo de colores azul, rosa, aqua, morado y dorado comestibles con burbujas de azúcar color perla', '/Imagenes/pastel1.png', 800, 10),
(2, 'Emily', 'Pastel de chocolate Planco con zarzamora decorado con crema pastelera color azul y mariposas con flores blancas hechas de fondandt', '/Imagenes/pastel2.png', 700, 10),
(3, 'Sun', 'Pastel de fresa con frambuesas y crema pastelera decorado con crema de mantequilla color azul y flores de crema de mantequilla con perlas comestibles', '/Imagenes/pastel3.png', 850, 10),
(4, 'Cloud', 'Pastel de mango con crema pastelera decorado con crema de mantequilla color azul y azul oscuro haciendo un degradado agregando unas nubes', '/Imagenes/pastel4.png', 750, 10),
(5, 'Berry', 'Pastel de crema pastelera con fresas, decorado con crema pastelera y fresas', '/Imagenes/pastel5.png', 850, 10);

--
-- RELACIONES PARA LA TABLA `pasteles`:
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `Pedido_ID` int(11) NOT NULL,
  `User_ID` int(11) NOT NULL,
  `Pedido_Productos` varchar(100) NOT NULL,
  `Pedido_Total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `pedidos`:
--   `User_ID`
--       `users` -> `User_ID`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `User_ID` int(11) NOT NULL,
  `User_Correo` varchar(50) NOT NULL,
  `User_Contraseña` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- RELACIONES PARA LA TABLA `users`:
--

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pasteles`
--
ALTER TABLE `pasteles`
  ADD PRIMARY KEY (`Pastel_ID`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`Pedido_ID`),
  ADD KEY `Users` (`User_ID`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`User_ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pasteles`
--
ALTER TABLE `pasteles`
  MODIFY `Pastel_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `Pedido_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `User_ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `Users` FOREIGN KEY (`User_ID`) REFERENCES `users` (`User_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

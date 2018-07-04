-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 04, 2018 at 10:05 AM
-- Server version: 5.7.22-0ubuntu0.16.04.1
-- PHP Version: 7.0.30-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sisfopus_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `kode_buku` varchar(20) NOT NULL,
  `judul_buku` varchar(150) NOT NULL,
  `penerbit_buku` varchar(150) NOT NULL,
  `penulis_buku` varchar(100) NOT NULL,
  `stok_buku` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`kode_buku`, `judul_buku`, `penerbit_buku`, `penulis_buku`, `stok_buku`) VALUES
('BK180501001', 'Laskar Pelanga', 'CVCV', 'Andrea Hirata', 12),
('BK180606003', 'Buku Pelajaran', 'Matahari', 'Bulan', 5),
('BK180606004', 'PERAHU KERTAS', 'BSI', 'ADI', 10);

-- --------------------------------------------------------

--
-- Table structure for table `denda`
--

CREATE TABLE `denda` (
  `kode_denda` varchar(20) NOT NULL,
  `denda_perhari` int(5) NOT NULL,
  `waktu_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `denda`
--

INSERT INTO `denda` (`kode_denda`, `denda_perhari`, `waktu_update`) VALUES
('DD001', 500, '2018-06-06 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `kode_member` varchar(20) NOT NULL,
  `nama_member` varchar(100) NOT NULL,
  `nim_member` varchar(100) NOT NULL,
  `telepon_member` varchar(20) NOT NULL,
  `waktu_bergabung_member` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_member` enum('1','2') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`kode_member`, `nama_member`, `nim_member`, `telepon_member`, `waktu_bergabung_member`, `status_member`) VALUES
('MEM123', 'Kevin', '123', '199', '2018-04-25 12:25:14', '1'),
('MEM123123', 'Ria', '123123', '08771717', '2018-04-25 12:46:23', '1');

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

CREATE TABLE `pegawai` (
  `kode_pegawai` varchar(20) NOT NULL,
  `nip` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama_pegawai` varchar(100) NOT NULL,
  `telepon_pegawai` varchar(20) NOT NULL,
  `level_pegawai` enum('1','2') NOT NULL,
  `status_pegawai` enum('1','2') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`kode_pegawai`, `nip`, `password`, `nama_pegawai`, `telepon_pegawai`, `level_pegawai`, `status_pegawai`) VALUES
('PEG1806123', '123', 'ADMINADMIN', 'ADMIN', '123', '1', '1'),
('PEG1806456', '456', 'PUSTAKAWAN', 'PUSTAKAWAN', '08989898989', '2', '1');

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman_pengembalian`
--

CREATE TABLE `peminjaman_pengembalian` (
  `kode_peminjaman` varchar(20) NOT NULL,
  `kode_pegawai` varchar(20) NOT NULL,
  `kode_member` varchar(20) NOT NULL,
  `kode_buku` varchar(20) NOT NULL,
  `waktu_mulai_peminjaman` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `waktu_akhir_peminjaman` timestamp NULL DEFAULT NULL,
  `waktu_pengembalian` timestamp NULL DEFAULT NULL,
  `status_pinjam` enum('1','2','3') NOT NULL,
  `jumlah_hari_keterlambatan` int(5) NOT NULL DEFAULT '0',
  `denda_perhari` int(7) NOT NULL DEFAULT '0',
  `total_denda` int(9) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `peminjaman_pengembalian`
--

INSERT INTO `peminjaman_pengembalian` (`kode_peminjaman`, `kode_pegawai`, `kode_member`, `kode_buku`, `waktu_mulai_peminjaman`, `waktu_akhir_peminjaman`, `waktu_pengembalian`, `status_pinjam`, `jumlah_hari_keterlambatan`, `denda_perhari`, `total_denda`) VALUES
('TRANS1805031001', 'PEG1806123', 'MEM123', 'BK18050101', '2018-05-01 00:00:00', '2018-05-03 00:00:00', '2018-06-08 00:00:00', '2', 0, 0, 0),
('TRANS1805031002', 'PEG1806123', 'MEM123', 'BK18050101', '2018-05-09 00:00:00', '2018-05-11 00:00:00', '2018-06-10 00:00:00', '2', 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`kode_buku`);

--
-- Indexes for table `denda`
--
ALTER TABLE `denda`
  ADD PRIMARY KEY (`kode_denda`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`kode_member`);

--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`kode_pegawai`);

--
-- Indexes for table `peminjaman_pengembalian`
--
ALTER TABLE `peminjaman_pengembalian`
  ADD PRIMARY KEY (`kode_peminjaman`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

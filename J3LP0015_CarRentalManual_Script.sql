USE [master]
GO
/****** Object:  Database [J3LP0015_CarRental]    Script Date: 05-Mar-21 2:35:36 PM ******/
CREATE DATABASE [J3LP0015_CarRental]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'J3LP0015_CarRental', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\J3LP0015_CarRental.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'J3LP0015_CarRental_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\J3LP0015_CarRental_log.ldf' , SIZE = 816KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [J3LP0015_CarRental] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [J3LP0015_CarRental].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [J3LP0015_CarRental] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET ARITHABORT OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [J3LP0015_CarRental] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [J3LP0015_CarRental] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [J3LP0015_CarRental] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET  ENABLE_BROKER 
GO
ALTER DATABASE [J3LP0015_CarRental] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [J3LP0015_CarRental] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [J3LP0015_CarRental] SET  MULTI_USER 
GO
ALTER DATABASE [J3LP0015_CarRental] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [J3LP0015_CarRental] SET DB_CHAINING OFF 
GO
ALTER DATABASE [J3LP0015_CarRental] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [J3LP0015_CarRental] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [J3LP0015_CarRental] SET DELAYED_DURABILITY = DISABLED 
GO
USE [J3LP0015_CarRental]
GO
/****** Object:  Table [dbo].[TblCar]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblCar](
	[carID] [varchar](20) NOT NULL,
	[carName] [nvarchar](20) NOT NULL,
	[image] [nvarchar](250) NOT NULL,
	[color] [nvarchar](50) NOT NULL,
	[year] [date] NOT NULL,
	[price] [real] NOT NULL,
	[quantity] [int] NOT NULL,
	[description] [nvarchar](500) NOT NULL,
	[categoryId] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[carID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TblCategory]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblCategory](
	[categoryID] [varchar](20) NOT NULL,
	[categoryName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TblDiscount]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblDiscount](
	[discountCode] [varchar](20) NOT NULL,
	[expiryDate] [date] NOT NULL,
	[discount] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[discountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TblOrder]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblOrder](
	[orderID] [varchar](20) NOT NULL,
	[bookingDate] [date] NOT NULL,
	[returnDate] [date] NOT NULL,
	[status] [varchar](20) NOT NULL,
	[totalAmount] [real] NOT NULL,
	[totalAfterDiscount] [real] NOT NULL,
	[email] [varchar](256) NOT NULL,
 CONSTRAINT [PK__TblOrder__0809337D81A0D83D] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TblOrderDetail]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblOrderDetail](
	[carID] [varchar](20) NOT NULL,
	[orderID] [varchar](20) NOT NULL,
	[quantity] [int] NOT NULL,
	[point] [int] NULL,
	[total] [real] NOT NULL,
	[feedback] [nvarchar](500) NULL,
 CONSTRAINT [PK__TblOrder__D4B663A3001A7CEF] PRIMARY KEY CLUSTERED 
(
	[carID] ASC,
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[TblUsers]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TblUsers](
	[email] [varchar](256) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[phone] [varchar](10) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
	[address] [nvarchar](200) NOT NULL,
	[dateCreate] [datetime] NULL DEFAULT (getdate()),
	[role] [varchar](10) NOT NULL,
	[status] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Crossover1', N'Mitsubishi Outlander', N'Mitsubishi-Outlander-1-9630-1613533179.jpg', N'White', CAST(N'2021-02-01' AS Date), 500, 5, N'Automatic Transmission, Fuel Engine, Led Light', N'2')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Crossover2', N'
Honda CR-V', N'Honda_CRV.jpg', N'White', CAST(N'2019-07-07' AS Date), 335, 10, N'Automatic Transmission, Fuel Engine, Led Light', N'2')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Crossover3', N'Kia Sorento', N'Kia-Sorento.jpg', N'Black', CAST(N'2021-04-04' AS Date), 500, 10, N'Automatic Transmission, Fuel Engine, Led Light', N'2')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Crossover4', N'Hyundai Tucson', N'Huyndai-tucson.jpg', N'Black', CAST(N'2021-05-05' AS Date), 450, 9, N'Automatic Transmission, Fuel Engine, Led Light', N'2')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'MPV1', N'Mitsubishi Xpander', N'Mitsubishi-Xpander-3-2319-1595230270.jpg', N'White', CAST(N'2019-09-09' AS Date), 250, 20, N'Automatic Transmission, Fuel Engine, Led Light', N'3')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'MPV2', N'Toyota Innova', N'TOYOTA-INNOVA-scaled.jpg', N'Purple', CAST(N'2020-02-02' AS Date), 300, 15, N'Automatic Transmission, Fuel Engine, Led Light', N'3')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'MPV3', N'Suzuki Ertiga', N'ertiga-exterior-right-front-three-quarter-141878.jpeg', N'Red', CAST(N'2019-02-02' AS Date), 290, 10, N'Automatic Transmission, Fuel Engine, Led Light', N'3')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'MPV4', N'Kia Sedona', N'Kia-sedona.jpg', N'Dark Blue', CAST(N'2021-03-03' AS Date), 700, 10, N'Automatic Transmission, Fuel Engine, Led Light', N'3')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Pickup1', N'Ford Ranger', N'2021_ford_ranger_thunder_6_aiew.jpg', N'Black', CAST(N'2020-06-06' AS Date), 500, 17, N'Automatic Transmission, Fuel Engine, Led Light', N'4')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Pickup2', N'Ranger Raptor', N'Raptor.jpg', N'Black', CAST(N'2021-05-05' AS Date), 800, 13, N'Automatic Transmission, Fuel Engine, Led Light', N'4')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Pickup3', N'Mitsubishi Triton', N'Mitsubishi-Triton-1-9888-1573186318.jpg', N'White', CAST(N'2018-02-02' AS Date), 600, 19, N'Automatic Transmission, Fuel Engine, Led Light', N'4')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Pickup4', N'Nissan Navara', N'Nissan-navara.jpg', N'Black', CAST(N'2020-09-09' AS Date), 700, 5, N'Automatic Transmission, Fuel Engine, Led Light', N'4')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Sedan1', N'Mercedes-Benz C200', N'c200-exclusive-1.jpg', N'Black', CAST(N'2021-10-10' AS Date), 1000, 20, N'Automatic Transmission, Fuel Engine, Led Light', N'5')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Sedan2', N'Mercedes-Benz E200', N'W213-Mercedes-Benz-E200-SportStyle-Avantgarde-2.jpg', N'Black', CAST(N'2021-02-02' AS Date), 1200, 15, N'Automatic Transmission, Fuel Engine, Led Light', N'5')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Sedan3', N'Maybach S650', N'Maybach-s650.jpg', N'Black Milk', CAST(N'2021-11-11' AS Date), 2000, 3, N'Automatic Transmission, Fuel Engine, Led Light', N'5')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Sedan4', N'Toyota Camry', N'2020_toyota_camry_sedan_trd_fq_oem_3_1600.jpg', N'White', CAST(N'2019-12-12' AS Date), 900, 10, N'Automatic Transmission, Fuel Engine, Led Light', N'5')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'Sedan5', N'BMW M3', N'2021-bmw-m3-sedan-renderings-by-kolesa-1-15955094021381695182241.jpg', N'White', CAST(N'2021-07-06' AS Date), 1000, 3, N'Automatic Transmission, Diesel Engine, Led Light', N'5')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'SUV1', N'Pajero Sport', N'Mitsubishi-Pajero-Sport-2020-3.jpg', N'White', CAST(N'2020-01-01' AS Date), 300, 10, N'Automatic Transmission, Diesel Engine, Led Light', N'1')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'SUV2', N'Ford Everest', N'Everest.jpg', N'Black', CAST(N'2021-01-01' AS Date), 400, 19, N'Automatic Transmission, Diesel Engine, Led Light', N'1')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'SUV3', N'Toyota Fortuner', N'2015_Toyota_Fortuner_(New_Zealand).jpg', N'White', CAST(N'2019-01-01' AS Date), 350, 12, N'Automatic Transmission, Diesel Engine, Led Light', N'1')
INSERT [dbo].[TblCar] ([carID], [carName], [image], [color], [year], [price], [quantity], [description], [categoryId]) VALUES (N'SUV4', N'Santa FE', N'Hyundai-Santa-Fe-2.2D-AWD-2019.png', N'Black', CAST(N'2021-02-02' AS Date), 400, 20, N'Automatic Transmission, Diesel Engine, Led Light', N'1')
INSERT [dbo].[TblCategory] ([categoryID], [categoryName]) VALUES (N'1', N'SUV')
INSERT [dbo].[TblCategory] ([categoryID], [categoryName]) VALUES (N'2', N'Crossover')
INSERT [dbo].[TblCategory] ([categoryID], [categoryName]) VALUES (N'3', N'MPV')
INSERT [dbo].[TblCategory] ([categoryID], [categoryName]) VALUES (N'4', N'Pickup')
INSERT [dbo].[TblCategory] ([categoryID], [categoryName]) VALUES (N'5', N'Sedan')
INSERT [dbo].[TblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'10', CAST(N'2021-03-01' AS Date), 10)
INSERT [dbo].[TblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'20', CAST(N'2021-03-10' AS Date), 20)
INSERT [dbo].[TblDiscount] ([discountCode], [expiryDate], [discount]) VALUES (N'5', CAST(N'2021-02-01' AS Date), 5)
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210228-103626-2986', CAST(N'2021-03-02' AS Date), CAST(N'2021-03-03' AS Date), N'ACTIVE', 6000, 6000, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210228-105113-0567', CAST(N'2021-03-02' AS Date), CAST(N'2021-03-03' AS Date), N'ACTIVE', 8000, 7200, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210301-051044-1505', CAST(N'2021-03-07' AS Date), CAST(N'2021-03-08' AS Date), N'ACTIVE', 4000, 4000, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210301-051753-8231', CAST(N'2021-03-01' AS Date), CAST(N'2021-03-05' AS Date), N'ACTIVE', 8000, 7200, N'2')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210303-081840-4348', CAST(N'2021-03-06' AS Date), CAST(N'2021-03-06' AS Date), N'ACTIVE', 60000, 60000, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210303-082328-2894', CAST(N'2021-03-04' AS Date), CAST(N'2021-03-04' AS Date), N'ACTIVE', 60000, 60000, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210303-082832-9719', CAST(N'2021-03-15' AS Date), CAST(N'2021-03-18' AS Date), N'ACTIVE', 240000, 240000, N'0')
INSERT [dbo].[TblOrder] ([orderID], [bookingDate], [returnDate], [status], [totalAmount], [totalAfterDiscount], [email]) VALUES (N'20210303-082847-0880', CAST(N'2021-03-22' AS Date), CAST(N'2021-03-23' AS Date), N'ACTIVE', 56000, 56000, N'0')
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Pickup2', N'20210301-051753-8231', 2, NULL, 1600, NULL)
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan1', N'20210228-103626-2986', 1, 5, 1000, N'Great')
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210228-103626-2986', 1, 5, 2000, N'Nice')
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210228-105113-0567', 2, 5, 4000, N'Very Nice')
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210301-051044-1505', 1, 5, 2000, N'Nice')
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210303-081840-4348', 30, NULL, 60000, NULL)
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210303-082328-2894', 30, NULL, 60000, NULL)
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210303-082832-9719', 30, NULL, 60000, NULL)
INSERT [dbo].[TblOrderDetail] ([carID], [orderID], [quantity], [point], [total], [feedback]) VALUES (N'Sedan3', N'20210303-082847-0880', 14, NULL, 28000, NULL)
INSERT [dbo].[TblUsers] ([email], [password], [phone], [name], [address], [dateCreate], [role], [status]) VALUES (N'0', N'0', N'0', N'0', N'0', CAST(N'2021-02-18 00:00:00.000' AS DateTime), N'MEMBER', N'ACTIVE')
INSERT [dbo].[TblUsers] ([email], [password], [phone], [name], [address], [dateCreate], [role], [status]) VALUES (N'1', N'1', N'1', N'1', N'1', CAST(N'2021-02-18 00:00:00.000' AS DateTime), N'ADMIN', N'ACTIVE')
INSERT [dbo].[TblUsers] ([email], [password], [phone], [name], [address], [dateCreate], [role], [status]) VALUES (N'2', N'2', N'2', N'2', N'2', CAST(N'2021-02-19 19:38:37.827' AS DateTime), N'MEMBER', N'ACTIVE')
INSERT [dbo].[TblUsers] ([email], [password], [phone], [name], [address], [dateCreate], [role], [status]) VALUES (N'trnnhtminht1709@gmail.com', N'aBcD', N'1111', N'minh123', N'1111', CAST(N'2021-02-20 20:35:04.827' AS DateTime), N'MEMBER', N'NEW')
ALTER TABLE [dbo].[TblCar]  WITH CHECK ADD FOREIGN KEY([categoryId])
REFERENCES [dbo].[TblCategory] ([categoryID])
GO
ALTER TABLE [dbo].[TblOrder]  WITH CHECK ADD  CONSTRAINT [FK__TblOrder__email__6A30C649] FOREIGN KEY([email])
REFERENCES [dbo].[TblUsers] ([email])
GO
ALTER TABLE [dbo].[TblOrder] CHECK CONSTRAINT [FK__TblOrder__email__6A30C649]
GO
ALTER TABLE [dbo].[TblOrderDetail]  WITH CHECK ADD  CONSTRAINT [FK__TblOrderD__carID__6E01572D] FOREIGN KEY([carID])
REFERENCES [dbo].[TblCar] ([carID])
GO
ALTER TABLE [dbo].[TblOrderDetail] CHECK CONSTRAINT [FK__TblOrderD__carID__6E01572D]
GO
ALTER TABLE [dbo].[TblOrderDetail]  WITH CHECK ADD  CONSTRAINT [FK__TblOrderD__order__6EF57B66] FOREIGN KEY([orderID])
REFERENCES [dbo].[TblOrder] ([orderID])
GO
ALTER TABLE [dbo].[TblOrderDetail] CHECK CONSTRAINT [FK__TblOrderD__order__6EF57B66]
GO
/****** Object:  StoredProcedure [dbo].[spGetBookPriceByBookID]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create Proc [dbo].[spGetBookPriceByBookID](@BookID int, @Price float output ) AS
select @Price =  BookPrice from  Books 
where  BookID = @BookID


GO
/****** Object:  StoredProcedure [dbo].[spReturnBookQuantityByBookID]    Script Date: 05-Mar-21 2:35:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

Create Proc [dbo].[spReturnBookQuantityByBookID]( @BookID int ) AS
declare @BookQuantity float
select @BookQuantity = BookQuantity from Books 
where BookID=@BookID
return @BookQuantity
GO
USE [master]
GO
ALTER DATABASE [J3LP0015_CarRental] SET  READ_WRITE 
GO

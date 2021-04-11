sCREATE DATABASE J3LP0015_CarRental

USE J3LP0015_CarRental

CREATE TABLE TblUsers (
email VARCHAR(256) PRIMARY KEY,
password VARCHAR(50) NOT NULL,
phone VARCHAR(10) NOT NULL,
name NVARCHAR(100) NOT NULL,
address NVARCHAR(200) NOT NULL,
dateCreate DATETIME DEFAULT GETDATE(),
role VARCHAR(10) NOT NULL,
status BIT NOT NULL /*- default status is new | verified email - status is active.*/
)

ALTER TABLE TblUsers
ADD role VARCHAR(10) NOT NULL

ALTER TABLE TblUsers
ALTER COLUMN status VARCHAR(10) NOT NULL

DROP TABLE tblUsers

SELECT email, password, phone, name, address, dateCreate, role, status
FROM TblUsers
WHERE email = '1@1' AND password = '1'

INSERT INTO tblUsers VALUES('1', '1', '1', '1', '1', NULL, 'ADMIN', 1)
INSERT INTO tblUsers VALUES('0', '0', '0', '0', '0', NULL, 'GUEST', 1)
INSERT INTO tblUsers (email, password, phone, name, address, role, status) VALUES ('2', '2', '2', '2', '2', 'GUEST', 1)

UPDATE tblUsers SET status = 'ACTIVE' WHERE email = 'trnnhtminht1709@gmail.com'

CREATE TABLE TblCategory(
    categoryID VARCHAR(20) PRIMARY KEY,
    categoryName NVARCHAR(50) NOT NULL
)

CREATE TABLE TblCar(
    carID VARCHAR(20) PRIMARY KEY,
    carName NVARCHAR(20) NOT NULL,
    image NVARCHAR(250) NOT NULL,
    color NVARCHAR(50) NOT NULL,
    year DATE NOT NULL,
    price REAL NOT NULL,
    quantity INT NOT NULL,
	description NVARCHAR(500) NOT NULL,
    categoryId VARCHAR(20) FOREIGN KEY REFERENCES TblCategory(categoryId)
)

DROP TABLE TblCar

CREATE TABLE TblOrderDetail(
    carID VARCHAR(20) FOREIGN KEY REFERENCES TblCar(carID),
    orderID VARCHAR(20) FOREIGN KEY REFERENCES TblOrder(orderID),
    quantity INT NOT NULL,
	point INT NOT NULL,
    total REAL NOT NULL,
	feedback NVARCHAR(500),
    PRIMARY KEY (carID, orderID)
)

DROP TABLE TblOrderDetail

CREATE TABLE TblOrder(
    orderID VARCHAR(20) primary key,
    bookingDate DATE NOT NULL,
	returnDate DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
	totalAmount REAL NOT NULL,
	totalAfterDiscount REAL NOT NULL,
    email VARCHAR(256) FOREIGN KEY REFERENCES tblUsers(email) NOT NULL,
    discountCode VARCHAR(20) FOREIGN KEY REFERENCES tblDiscount(discountCode) NOT NULL
)

DROP TABLE TblOrder

CREATE TABLE TblDiscount(
    discountCode VARCHAR(20) PRIMARY KEY,
    expiryDate DATE  NOT NULL,
    discount INT  NOT NULL
)

DROP TABLE TblDiscount

SELECT carID, carName, image, color, year, price, quantity, description, ca.categoryName
FROM TblCar c, tblCategory ca
WHERE c.categoryId = ca.categoryID AND quantity > 0 
GROUP BY carID, carName, image, color, year, price, quantity, description, ca.categoryName
ORDER BY year DESC
OFFSET 0 ROWS
FETCH NEXT 20 ROWS ONLY

SELECT count(carID) as totalCar 
FROM tblCar
WHERE quantity > 0

SELECT categoryID, categoryName
FROM tblCategory 

SELECT c.carID, od.quantity
FROM tblCar c, tblOrderDetail od, tblOrder o
WHERE c.carID = od.carID AND od.orderID = o.orderID	AND (((bookingDate BETWEEN '2021-02-12' AND '2021-02-14') OR (returnDate BETWEEN '2021-02-12' AND '2021-02-14')))

SELECT c.carID, SUM(od.quantity) AS quantity
FROM tblCar c, tblOrderDetail od, tblOrder o
WHERE c.carID = od.carID AND od.orderID = o.orderID	AND ((bookingDate BETWEEN '2021-02-12' AND '2021-02-14') OR (returnDate BETWEEN '2021-02-12' AND '2021-02-14'))
GROUP BY c.carID

SELECT carID, image, carName, color, year, price, quantity, ca.categoryName, description 
FROM tblCar c, tblCategory ca
WHERE c.categoryId = ca.categoryId AND carID = 'SUV1'

SELECT u.fullname, feedback, point
FROM TblCar c, TblOrderDetail od, TblOrder o, TblUser u 
WHERE c.carID = od.carID and od.orderID = o.orderID and o.userID = u.email 
AND c.carID = 'eco1'

SELECT discountCode, expiryDate, discount 
FROM TblDiscount
WHERE discountCode = 

INSERT INTO TblOrder(orderID, bookingDate, returnDate, status, totalAmount, totalAfterDiscount, email, discountCode)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)

SELECT orderID, u.email, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount
FROM TblOrder o, TblUsers u
WHERE  o.email = u.email AND o.email = '0'
GROUP BY o.orderID, u.email, bookingDate, returnDate, o.status, totalAmount, totalAfterDiscount
ORDER BY bookingDate DESC

SELECT orderID, email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
FROM TblOrder
GROUP BY orderID, email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
ORDER BY bookingDate DESC

SELECT o.orderID, o.email, bookingDate, returnDate, status, totalAmount, totalAfterDiscount
FROM TblOrder o, TblOrderDetail od,  TblCar c
WHERE o.orderID = od.orderID AND od.carID = c.carID 

SELECT od.carID, carName, image, price, od.quantity 
FROM tblCar c, tblOrderDetail od
WHERE c.carID = od.carID AND orderID = 

UPDATE TblOrderDetail
SET feedback = 'excellent', point = '4'
FROM tblCar c, tblOrderDetail od, tblOrder o 
WHERE c.carID = od.carID AND od.orderID = o.orderID AND od.carID = 'sedan3' and od.orderID = '20210228-103626-2986'
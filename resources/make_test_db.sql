CREATE DATABASE IF NOT EXISTS testts;

GRANT ALL PRIVILEGES ON testts.* TO stock@localhost IDENTIFIED BY 'check';
GRANT ALL PRIVILEGES ON testts.* TO stock@'%' IDENTIFIED BY 'check';

USE testts;

DROP TABLE IF EXISTS Credentials;
DROP TABLE IF EXISTS Employees;

CREATE TABLE Employees
(EmployeeID INT NOT NULL AUTO_INCREMENT
,TimesheetApprover INT
,Supervisor INT
,FirstName VARCHAR(255) NOT NULL
,LastName  VARCHAR(255) NOT NULL
,LabourGrade  VARCHAR(255) NOT NULL
,Status VARCHAR(255) NOT NULL
,PRIMARY KEY(EmployeeID)
,FOREIGN KEY(TimesheetApprover) REFERENCES Employees(EmployeeID)
,FOREIGN KEY(Supervisor) REFERENCES Employees(EmployeeID)
);

INSERT INTO Employees VALUES(0, null, null, "AdminFirst", "AdminLast", "AdminGrade", "AdminStatus");

CREATE TABLE Credentials
(Username VARCHAR(255) NOT NULL
,Password VARCHAR(255) NOT NULL
,EmployeeID INT NOT NULL
,PRIMARY KEY(Username, Password)
,FOREIGN KEY(EmployeeID) REFERENCES Employees(EmployeeID)
);

INSERT INTO Credentials VALUES("Admin", "Admin", 1);
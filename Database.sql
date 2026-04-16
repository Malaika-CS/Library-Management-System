//Malaika Zahoor
//Library Management System

                                       //1. Author Table

CREATE TABLE Author (
    Author_code INT PRIMARY KEY,
    Author_name VARCHAR(100),
    Author_Subject VARCHAR(100),
    Author_qualification VARCHAR(100)
);
                                  //2. Publisher Table

CREATE TABLE Publisher (
    Publisher_code INT PRIMARY KEY,
    Publisher_name VARCHAR(100),
    Publisher_Country VARCHAR(100)
);
                         //3. Vendor Table

CREATE TABLE Vendor (
    Vendor_id INT PRIMARY KEY, -- Assumed since not shown
    Library_name VARCHAR(100),
    Contact_no VARCHAR(15)
);

                     //4. Library Table

CREATE TABLE Library (
    Library_id INT PRIMARY KEY, -- Assumed since not shown
    Library_name VARCHAR(100),
    Library_address VARCHAR(200),
    Contact_no VARCHAR(15)
);

                                //5. Admin Table

CREATE TABLE Admin (
    Admin_Id INT PRIMARY KEY,
    Admin_name VARCHAR(100),
    Contact_no VARCHAR(15)
);
                            //6. Employee Table

CREATE TABLE Employee (
    Emp_Id INT PRIMARY KEY,
    Emp_name VARCHAR(100),
    Designation VARCHAR(50),
    Mobile_no VARCHAR(15),
    Admin_Id INT,
    FOREIGN KEY (Admin_Id) REFERENCES Admin(Admin_Id)
); 

                          //7. Member Table

CREATE TABLE Member (
    Mem_Id INT PRIMARY KEY,
    F_Name VARCHAR(50),
    L_Name VARCHAR(50),
    Name AS (F_Name || ' ' || L_Name) STORED,
    Address VARCHAR(200),
    City VARCHAR(50),
    State VARCHAR(50),
    Pin_code VARCHAR(10),
    Contact_no VARCHAR(15),
    Mem_type VARCHAR(50)
);

                                  //8. Books Table

CREATE TABLE Books (
    Book_Id INT PRIMARY KEY,
    Book_price DECIMAL(10, 2),
    Book_status VARCHAR(50),
    Author_code INT,
    Publisher_code INT,
    Library_id INT,
    FOREIGN KEY (Author_code) REFERENCES Author(Author_code),
    FOREIGN KEY (Publisher_code) REFERENCES Publisher(Publisher_code),
    FOREIGN KEY (Library_id) REFERENCES Library(Library_id)
);

                       //9. Book_Issue_Receive Table (Relationship between Employee and Books)

CREATE TABLE Book_Issue_Receive (
    Book_Id INT,
    Emp_Id INT,
    Issue_Date DATE,
    Return_Date DATE,
    PRIMARY KEY (Book_Id, Emp_Id),
    FOREIGN KEY (Book_Id) REFERENCES Books(Book_Id),
    FOREIGN KEY (Emp_Id) REFERENCES Employee(Emp_Id)
);
                                        //10. Book_Request Table (Relationship between Member and Books)

CREATE TABLE Book_Request (
    Book_Id INT,
    Mem_Id INT,
    Request_Date DATE,
    PRIMARY KEY (Book_Id, Mem_Id),
    FOREIGN KEY (Book_Id) REFERENCES Books(Book_Id),
    FOREIGN KEY (Mem_Id) REFERENCES Member(Mem_Id)
);

                                    //11. Sales Table (Relationship between Books and Vendor)

CREATE TABLE Sales (
    Book_Id INT,
    Vendor_id INT,
    Sale_Date DATE,
    PRIMARY KEY (Book_Id, Vendor_id),
    FOREIGN KEY (Book_Id) REFERENCES Books(Book_Id),
    FOREIGN KEY (Vendor_id) REFERENCES Vendor(Vendor_id)
);
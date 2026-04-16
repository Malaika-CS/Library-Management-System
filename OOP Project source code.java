     //Library Management System                             
  //Database Connection  Code
package librarymanagement.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con = null;

    public static Connection getConnection() {
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Replace with your actual DB info:
            String url = "jdbc:oracle:thin:@10.11.0.22:1521:xe"; // Adjust your port, SID if needed
            String user = "FA24CS072"; // <-- Change this
            String password = "oracle"; // <-- Change this

            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
        return con;
    }
}


              //Login Page

package librarymanagement.login;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryLogin extends JFrame implements ActionListener {

    // GUI components
    JLabel userLabel, passLabel;
    JTextField userText;
    JPasswordField passText;
    JButton loginButton;

    // Constructor
    public LibraryLogin() {
        setTitle("Library Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        userText = new JTextField();
        passText = new JPasswordField();
        loginButton = new JButton("Login");

        add(userLabel);
        add(userText);
        add(passLabel);
        add(passText);
        add(new JLabel()); // Empty cell
        add(loginButton);

        loginButton.addActionListener(this);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // DB Connection method
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load Oracle JDBC Driver
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Oracle JDBC Driver not found.");
            return null;
        }

        // Update with your actual DB values
        String url = "jdbc:oracle:thin:@10.11.0.22:1521:XE"; // Replace XE with your SID
        String user = "FA24CS072";
        String password = "oracle";

        return DriverManager.getConnection(url, user, password);
    }

    // Action when login is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userText.getText();
        String password = new String(passText.getPassword());

        try (Connection conn = getConnection()) {
            if (conn != null) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    
                    Dashboard obj = new Dashboard();
                    
                    
                    
                    // Proceed to main menu/dashboard
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new LibraryLogin();
    }
}

                              //Dashboard

package librarymanagement.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    JButton addStudentBtn, addBookBtn, borrowBookBtn, returnBookBtn, 
            viewBookBtn, viewBorrowBtn, viewReturnBtn, viewAllStudentsBtn,
            printBtn, logoutBtn;

    public Dashboard() {
        setTitle("Library Management Dashboard");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        // Buttons
        addStudentBtn = new JButton("Add Student");
        addBookBtn = new JButton("Add Book");
        borrowBookBtn = new JButton("Borrow Book");
        returnBookBtn = new JButton("Return Book");
        viewBookBtn = new JButton("View Book Record");
        viewBorrowBtn = new JButton("View Borrow Records");
        viewReturnBtn = new JButton("View Return Records");
        viewAllStudentsBtn = new JButton("View All Students");
        printBtn = new JButton("Print/Export Records");
        logoutBtn = new JButton("Logout");

        // Add Action Listeners
        addStudentBtn.addActionListener(this);
        addBookBtn.addActionListener(this);
        borrowBookBtn.addActionListener(this);
        returnBookBtn.addActionListener(this);
        viewBookBtn.addActionListener(this);
        viewBorrowBtn.addActionListener(this);
        viewReturnBtn.addActionListener(this);
        viewAllStudentsBtn.addActionListener(this);
        printBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        // Add buttons to panel
        panel.add(addStudentBtn);
        panel.add(addBookBtn);
        panel.add(borrowBookBtn);
        panel.add(returnBookBtn);
        panel.add(viewBookBtn);
        panel.add(viewBorrowBtn);
        panel.add(viewReturnBtn);
        panel.add(viewAllStudentsBtn);
        panel.add(printBtn);
        panel.add(logoutBtn);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentBtn) {
            new AddStudentForm(); // Open Add Student form
        } else if (e.getSource() == addBookBtn) {
            new AddBookForm(); // Open Add Book form
        } else if (e.getSource() == borrowBookBtn) {
            new BorrowBookForm(); // Open Borrow Book form
        } else if (e.getSource() == returnBookBtn) {
            new ReturnBookForm(); // Open Return Book form
        } else if (e.getSource() == viewBookBtn) {
            new ViewBookForm(); // Open View Book by ID form
        } else if (e.getSource() == viewBorrowBtn) {
            new ViewBorrowRecordsForm(); // Open Borrow Records form
        } else if (e.getSource() == viewReturnBtn) {
            new ViewReturnRecordsForm(); // Open Return Records form
        } else if (e.getSource() == viewAllStudentsBtn) {
            new ViewAllStudentsForm(); // Open All Student Records form
        } else if (e.getSource() == printBtn) {
            new PrintForm(); // Open Print/Export form
        } else if (e.getSource() == logoutBtn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?");
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close Dashboard
                System.exit(0); // Or redirect to login
            }
        }
    }

    // You can uncomment below to run this class directly for testing
    /*
    public static void main(String[] args) {
        new Dashboard();
    }
    */
}


                    //AddBookForm
package librarymanagement.login;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBookForm extends JFrame {
    JTextField bookIdField, titleField, authorField;
    JButton submitBtn;

    public AddBookForm() {
        setTitle("Add Book");
        setSize(350, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        submitBtn = new JButton("Submit");
        add(submitBtn);
        submitBtn.addActionListener(e -> insertBook());

        setVisible(true);
    }

    private void insertBook() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO books (book_id, title, author) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, bookIdField.getText());
            ps.setString(2, titleField.getText());
            ps.setString(3, authorField.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
                     //AddStudentForm
package librarymanagement.login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStudentForm extends JFrame {
    JTextField idField, nameField, deptField;
    JButton submitBtn;

    public AddStudentForm() {
        setTitle("Add Student");
        setSize(350, 250);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Student ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        submitBtn = new JButton("Submit");
        add(submitBtn);
        submitBtn.addActionListener(e -> insertStudent());

        setVisible(true);
    }

    private void insertStudent() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO students (student_id, name, department) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, idField.getText());
            ps.setString(2, nameField.getText());
            ps.setString(3, deptField.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student added successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

                                               //BookEntryForm
package librarymanagement.login;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BookEntryForm extends JFrame {
    JTextField bookIdField, priceField, statusField, authorCodeField, publisherCodeField, libraryNameField;
    JButton insertButton;

    public BookEntryForm() {
        setTitle("Book Entry Form");
        setSize(400, 400);
        setLayout(null);

        JLabel bookIdLabel = new JLabel("Book ID:");
        JLabel priceLabel = new JLabel("Book Price:");
        JLabel statusLabel = new JLabel("Book Status:");
        JLabel authorCodeLabel = new JLabel("Author Code:");
        JLabel publisherCodeLabel = new JLabel("Publisher Code:");
        JLabel libraryNameLabel = new JLabel("Library Name:");

        bookIdField = new JTextField();
        priceField = new JTextField();
        statusField = new JTextField();
        authorCodeField = new JTextField();
        publisherCodeField = new JTextField();
        libraryNameField = new JTextField();

        insertButton = new JButton("Insert Book");

        // Positioning
        int y = 30;
        int height = 30;

        bookIdLabel.setBounds(30, y, 120, height);
        bookIdField.setBounds(160, y, 180, height); y += 40;

        priceLabel.setBounds(30, y, 120, height);
        priceField.setBounds(160, y, 180, height); y += 40;

        statusLabel.setBounds(30, y, 120, height);
        statusField.setBounds(160, y, 180, height); y += 40;

        authorCodeLabel.setBounds(30, y, 120, height);
        authorCodeField.setBounds(160, y, 180, height); y += 40;

        publisherCodeLabel.setBounds(30, y, 120, height);
        publisherCodeField.setBounds(160, y, 180, height); y += 40;

        libraryNameLabel.setBounds(30, y, 120, height);
        libraryNameField.setBounds(160, y, 180, height); y += 40;

        insertButton.setBounds(130, y + 20, 120, 40);

        add(bookIdLabel); add(bookIdField);
        add(priceLabel); add(priceField);
        add(statusLabel); add(statusField);
        add(authorCodeLabel); add(authorCodeField);
        add(publisherCodeLabel); add(publisherCodeField);
        add(libraryNameLabel); add(libraryNameField);
        add(insertButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertBook();
            }
        });
    }

    public void insertBook() {
        int bookId = Integer.parseInt(bookIdField.getText());
        double price = Double.parseDouble(priceField.getText());
        String status = statusField.getText();
        int authorCode = Integer.parseInt(authorCodeField.getText());
        int publisherCode = Integer.parseInt(publisherCodeField.getText());
        String libraryName = libraryNameField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Books (Book_Id, Book_price, Book_status, Author_code, Publisher_code, Library_name) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.setDouble(2, price);
            stmt.setString(3, status);
            stmt.setInt(4, authorCode);
            stmt.setInt(5, publisherCode);
            stmt.setString(6, libraryName);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book inserted successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookEntryForm();
    }
}

                              //BorrowBookForm
package librarymanagement.login;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BorrowBookForm extends JFrame {
    JTextField studentIdField, bookIdField;
    JButton submitBtn;

    public BorrowBookForm() {
        setTitle("Borrow Book");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        add(studentIdField);

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        submitBtn = new JButton("Submit");
        add(submitBtn);
        submitBtn.addActionListener(e -> borrowBook());

        setVisible(true);
    }

    private void borrowBook() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO borrowings (student_id, book_id, borrow_date) VALUES (?, ?, SYSDATE)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentIdField.getText());
            ps.setString(2, bookIdField.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
                                         //ReturnBookForm
package librarymanagement.login;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class ReturnBookForm extends JFrame {
    JTextField studentIdField, bookIdField;
    JButton submitBtn;

    public ReturnBookForm() {
        setTitle("Return Book");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        add(studentIdField);

        add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        add(bookIdField);

        submitBtn = new JButton("Submit");
        add(submitBtn);
        submitBtn.addActionListener(e -> returnBook());

        setVisible(true);
    }

    private void returnBook() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE borrowings SET return_date = SYSDATE WHERE student_id = ? AND book_id = ? AND return_date IS NULL";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, studentIdField.getText());
            ps.setString(2, bookIdField.getText());
            int updated = ps.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No matching record found.");
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

                                     //ViewAllStudentsForm
package librarymanagement.login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAllStudentsForm extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ViewAllStudentsForm() {
        setTitle("All Student Records");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Course");
        model.addColumn("Branch");

        fetchAllStudents();

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void fetchAllStudents() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM STUDENTS";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("course"),
                    rs.getString("branch")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
        }
    }
}

                                           //ViewBookForm
package librarymanagement.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewBookForm extends JFrame {
    private JTextField txtBookID;
    private JTextArea txtAreaDetails;

    public ViewBookForm() {
        setTitle("View Book Records");
        setSize(400, 400);
        setLayout(new BorderLayout());

        JLabel lblBookID = new JLabel("Enter Book ID:");
        txtBookID = new JTextField(20);
        JButton btnSearch = new JButton("Search");
        txtAreaDetails = new JTextArea(10, 30);
        txtAreaDetails.setEditable(false);

        JPanel topPanel = new JPanel();
        topPanel.add(lblBookID);
        topPanel.add(txtBookID);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(txtAreaDetails), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> fetchBook());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchBook() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM books WHERE book_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtBookID.getText().trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtAreaDetails.setText("Book ID: " + rs.getString("book_id") +
                        "\nTitle: " + rs.getString("title") +
                        "\nAuthor: " + rs.getString("author") +
                        "\nPublisher: " + rs.getString("publisher") +
                        "\nYear: " + rs.getString("year"));
            } else {
                txtAreaDetails.setText("No book found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

                                     //ViewBorrowRecordsForm
package librarymanagement.login;

import javax.swing.*;
import java.sql.*;

public class ViewBorrowRecordsForm extends JFrame {
    private JTextArea textArea;

    public ViewBorrowRecordsForm() {
        setTitle("View Borrow Records");
        setSize(500, 400);
        textArea = new JTextArea();
        add(new JScrollPane(textArea));
        fetchRecords();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchRecords() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM borrowed_books");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Borrow ID: ").append(rs.getInt("borrow_id"))
                  .append(", Student ID: ").append(rs.getString("student_id"))
                  .append(", Book ID: ").append(rs.getString("book_id"))
                  .append(", Borrow Date: ").append(rs.getDate("borrow_date"))
                  .append("\n");
            }
            textArea.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

                                         //ViewReturnRecordsForm
package librarymanagement.login;

import javax.swing.*;
import java.sql.*;

public class ViewReturnRecordsForm extends JFrame {
    private JTextArea textArea;

    public ViewReturnRecordsForm() {
        setTitle("View Return Records");
        setSize(500, 400);
        textArea = new JTextArea();
        add(new JScrollPane(textArea));
        fetchRecords();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchRecords() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM returned_books");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Return ID: ").append(rs.getInt("return_id"))
                  .append(", Student ID: ").append(rs.getString("student_id"))
                  .append(", Book ID: ").append(rs.getString("book_id"))
                  .append(", Return Date: ").append(rs.getDate("return_date"))
                  .append("\n");
            }
            textArea.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

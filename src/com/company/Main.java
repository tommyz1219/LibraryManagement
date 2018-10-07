//package com.sqlsamples;
package com.company;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void createGUI() {


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 7); // Adding 5 days

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Database URL
        String DB_URL = "jdbc:mysql://localhost:3306/testdb";

        //  Database credentials
        String USER = "root";
        String PASS = "SDPTEAM27";

        //main menu window
        JFrame guiFrame = new JFrame();
        //task confirmation screen
        JFrame done = new JFrame();
        //main menu panel for buttons
        JPanel p = new JPanel();
        //confirmation screen panel for button
        JPanel panel = new JPanel();

        JButton back = new JButton("Back");
        back.setBounds(50, 180, 110, 40);
        JButton submit = new JButton("Submit");
        submit.setBounds(220, 180, 110, 40);

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("System");
        guiFrame.setSize(400, 370);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Main menu buttons
        JButton register = new JButton("New Member");
        register.setBounds(50, 60, 110, 40);

        JButton find = new JButton("Find Member");
        find.setBounds(50, 160, 110, 40);

        JButton lookup = new JButton("Find Book");
        lookup.setBounds(220, 60, 110, 40);

        JButton checkout = new JButton("Borrow Book");
        checkout.setBounds(220, 160, 110, 40);

        JButton returnBook = new JButton("Return Book");
        returnBook.setBounds(135, 240, 100, 40);

        JButton admin = new JButton("Admin Login");
        admin.setBounds(290, 0, 95, 20);
        admin.setFont(new Font("Arial", Font.PLAIN, 10));

        //side menu buttons
        JButton ok = new JButton("Done");
        ok.setBounds(55, 40, 70, 30);

        //add to panel, which is then added onto frame
        p.setLayout(null);
        p.add(register);
        p.add(find);
        p.add(lookup);
        p.add(checkout);
        p.add(returnBook);
        p.add(admin);
        guiFrame.add(p);

        //button actions
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(register);
                p.remove(find);
                p.remove(lookup);
                p.remove(checkout);
                p.remove(returnBook);
                p.remove(admin);
                p.repaint();

                JLabel name = new JLabel("Name:");
                JTextField name1 = new JTextField(10);

                JLabel address = new JLabel("Address:");
                JTextField address1 = new JTextField(10);

                JLabel idnum = new JLabel("ID Number:");
                JTextField idnum1 = new JTextField(10);

                p.setLayout(null);
                name.setBounds(90, 20, 50, 50);
                name1.setBounds(170, 35, 120, 25);
                name1.setLayout(new BorderLayout());

                address.setBounds(90, 70, 60, 50);
                address1.setBounds(170, 85, 120, 25);
                address1.setLayout(new BorderLayout());

                idnum.setBounds(90, 115, 80, 50);
                idnum1.setBounds(170, 130, 120, 25);
                idnum1.setLayout(new BorderLayout());

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        //Statement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");

                            String sql = "INSERT INTO members (Name, Address, IDnum, Joined)"
                                    + " values (?, ?, ?, ?)";

                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, name1.getText());
                            stmt.setString(2, address1.getText());
                            stmt.setString(3, idnum1.getText());
                            stmt.setString(4, dtf.format(LocalDateTime.now()).toString());
                            stmt.executeUpdate();

                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                }
                            });

                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                        name1.setText("");
                        address1.setText("");
                        idnum1.setText("");
                    }
                });
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGUI();
                        guiFrame.dispose();
                    }
                });

                p.add(back);
                p.add(name);
                p.add(name1);
                p.add(address);
                p.add(address1);
                p.add(idnum);
                p.add(idnum1);
                p.add(submit);

                guiFrame.add(p);
            }
        });

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(register);
                p.remove(find);
                p.remove(lookup);
                p.remove(checkout);
                p.remove(returnBook);
                p.remove(admin);
                p.repaint();

                JLabel searchCriteria = new JLabel("Enter Full Name:");
                JTextField searchField = new JTextField(10);
                searchCriteria.setBounds(70, 20, 100, 50);
                searchField.setBounds(175, 33, 140, 25);

                JLabel results = new JLabel("abc");
                results.setBounds(90, 110, 200, 25);
                results.setLayout(new BorderLayout());

                p.add(back);
                p.add(submit);
                p.add(searchCriteria);
                p.add(searchField);


                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p.repaint();
                        Connection conn = null;
                        //PreparedStatement stmt = null;
                        Statement stmt = null;
                        ResultSet rs = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");

                            String sql = "select * from members where Name = '" + searchField.getText() + "'";

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery(sql);

                            //search results
                            String info = "";
                            while (rs.next()) {
                                for(int i = 1 ; i <= 3; i++){
                                    info = info + "     " + rs.getString(i);

                                }
                            }
                            results.setText(info);
                            p.add(results);

                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGUI();
                        guiFrame.dispose();
                    }
                });
            }
        });
        lookup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(register);
                p.remove(find);
                p.remove(lookup);
                p.remove(checkout);
                p.remove(returnBook);
                p.remove(admin);
                p.repaint();

                JLabel searchCriteria = new JLabel("Enter Title:");
                JTextField searchField = new JTextField(10);
                searchCriteria.setBounds(70, 20, 100, 50);
                searchField.setBounds(175, 33, 140, 25);

                JLabel results = new JLabel("abc");
                results.setBounds(40, 110, 300, 25);
                results.setLayout(new BorderLayout());

                p.add(back);
                p.add(submit);
                p.add(searchCriteria);
                p.add(searchField);


                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        p.repaint();
                        Connection conn = null;
                        //PreparedStatement stmt = null;
                        Statement stmt = null;
                        ResultSet rs = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");

                            String sql = "select * from bookinfo where Title = '" + searchField.getText() + "'";

                            stmt = conn.createStatement();
                            System.out.println(sql);

                            rs = stmt.executeQuery(sql);
                            //search results
                            String info = "";
                            while (rs.next()) {
                                for(int i = 1 ; i <= 3; i++){
                                    info = info + "     " + rs.getString(i);

                                }
                            }
                            results.setText(info);
                            p.add(results);

                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGUI();
                        guiFrame.dispose();
                    }
                });
            }
        });
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(register);
                p.remove(find);
                p.remove(lookup);
                p.remove(checkout);
                p.remove(returnBook);
                p.remove(admin);
                p.repaint();

                JLabel user = new JLabel("User ID:");
                JTextField user1 = new JTextField(10);

                JLabel title = new JLabel("Title:");
                JTextField title1 = new JTextField(10);

                JLabel author = new JLabel("Author:");
                JTextField author1 = new JTextField(10);

                p.setLayout(null);
                user.setBounds(90, 20, 50, 50);
                user1.setBounds(170, 35, 120, 25);
                user1.setLayout(new BorderLayout());

                title.setBounds(90, 70, 60, 50);
                title1.setBounds(170, 85, 120, 25);
                title1.setLayout(new BorderLayout());

                author.setBounds(90, 115, 80, 50);
                author1.setBounds(170, 130, 120, 25);
                author1.setLayout(new BorderLayout());

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet rs = null;
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        //Statement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");
                            String sql = "select * from bookinfo where Title = '" + title1.getText() + "' and Author = '" + author1.getText() + " '";

                            stmt = conn.prepareStatement(sql);
                            System.out.println(sql);

                            rs = stmt.executeQuery(sql);
                            //search results
                            String info = "0";
                            int copies = 0;
                            while (rs.next()) {
                                info = rs.getString(2);
                                copies = Integer.parseInt(rs.getString(3));
                            }
                            System.out.println(info);
                            System.out.println(copies);
                            System.out.println(author1.getText());
                            if (info.equals(author1.getText())) {
                                //cast copies from database as int and increment since book record exists
                                copies--;
                                //update exising record
                                sql = "UPDATE bookinfo" + " SET Copies = ? " + "WHERE Title = '" + title1.getText() + "' AND " + "Author = '" + author1.getText() + "'";

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, Integer.toString(copies));
                                stmt.executeUpdate();

                                sql = "INSERT INTO borrowed (IDNum, Title, Author, DateOut, DueDate)"
                                        + " values (?, ?, ?, ?, ?)";

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, user1.getText());
                                stmt.setString(2, title1.getText());
                                stmt.setString(3, author1.getText());
                                stmt.setString(4, dtf.format(LocalDateTime.now()).toString());
                                stmt.setString(5, sdf.format(c.getTime()));
                                stmt.executeUpdate();
                            }
                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                }
                            });
                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                        user1.setText("");
                        title1.setText("");
                        author1.setText("");
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGUI();
                        guiFrame.dispose();
                    }
                });

                p.add(back);
                p.add(submit);
                p.add(user);
                p.add(user1);
                p.add(title);
                p.add(title1);
                p.add(author);
                p.add(author1);
            }
        });
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFrame.dispose();
                createAdminGUI();
            }
        });
        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(register);
                p.remove(find);
                p.remove(lookup);
                p.remove(checkout);
                p.remove(returnBook);
                p.remove(admin);
                p.repaint();

                JLabel user = new JLabel("User ID:");
                JTextField user1 = new JTextField(10);

                JLabel title = new JLabel("Title:");
                JTextField title1 = new JTextField(10);

                JLabel author = new JLabel("Author:");
                JTextField author1 = new JTextField(10);

                p.setLayout(null);
                user.setBounds(90, 20, 50, 50);
                user1.setBounds(170, 35, 120, 25);
                user1.setLayout(new BorderLayout());

                title.setBounds(90, 70, 60, 50);
                title1.setBounds(170, 85, 120, 25);
                title1.setLayout(new BorderLayout());

                author.setBounds(90, 115, 80, 50);
                author1.setBounds(170, 130, 120, 25);
                author1.setLayout(new BorderLayout());

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet rs = null;
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");

                            String sql = "select * from bookinfo where Title = '" + title1.getText() + "' and Author = '" + author1.getText() + "'";

                            stmt = conn.prepareStatement(sql);
                            rs = stmt.executeQuery(sql);

                            //search results
                            String info = "0";
                            int copies = 0;
                            while (rs.next()) {
                                info = rs.getString(2);
                                copies = Integer.parseInt(rs.getString(3));
                            }
                            System.out.println(info);
                            System.out.println(copies);
                            System.out.println(author1.getText());
                            if (info.equals(author1.getText())) {
                                //cast copies from database as int and increment since book record exists
                                copies++;
                                //update exising record
                                sql = "UPDATE bookinfo" + " SET Copies = ? " + "WHERE Title = '" + title1.getText() + "' AND " + "Author = '" + author1.getText() + "'";
                                System.out.println(sql);

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, Integer.toString(copies));
                                stmt.executeUpdate();

                                sql = "DELETE FROM borrowed WHERE" + " IDNum = '" + user1.getText() + "' AND Title = '" + title1.getText() + "' AND Author = '" + author1.getText() + "'";
                                System.out.println(sql);

                                stmt = conn.prepareStatement(sql);
                                stmt.executeUpdate();
                                //System.out.println("new book");
                            }
                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                }
                            });
                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                        user1.setText("");
                        title1.setText("");
                        author1.setText("");
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        createGUI();
                        guiFrame.dispose();
                    }
                });

                p.add(back);
                p.add(submit);
                p.add(user);
                p.add(user1);
                p.add(title);
                p.add(title1);
                p.add(author);
                p.add(author1);
            }
        });

        //Shows GUI
        guiFrame.setVisible(true);
    }

    public static void createAdminGUI(){

        //window
        JFrame adminLogin = new JFrame();
        //make sure the program exits when the frame closes
        adminLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminLogin.setTitle("Administrator");
        adminLogin.setSize(350, 250);
        //This will center the JFrame in the middle of the screen
        adminLogin.setLocationRelativeTo(null);
        //Shows GUI
        adminLogin.setVisible(true);
        //main menu panel for buttons
        JPanel p = new JPanel();
        p.setLayout(null);

        //login
        JLabel username = new JLabel("Username: ");
        JTextField username1 = new JTextField(10);
        JLabel password = new JLabel("Password: ");
        JPasswordField password1 = new JPasswordField(10);
        JButton login = new JButton("Login");
        JButton back = new JButton("Back");

        username.setBounds(70, 40, 90, 30);
        username1.setBounds(140, 45, 110, 25);
        password.setBounds(70, 90, 90, 30);
        password1.setBounds(140, 95, 110, 25);
        login.setBounds(160, 140, 80, 25);
        back.setBounds(75, 140, 80, 25);

        p.add(username);
        p.add(username1);
        p.add(password);
        p.add(password1);
        p.add(login);
        p.add(back);

        adminLogin.add(p);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check login credentials
                if (username1.getText().equals("admin") && (new String(password1.getPassword())).equals("password")){
                    //admin privileges
                    adminLogin.dispose();
                    createAdminFrame();
                }
                //error screen and have user try again
                else{
                    JFrame error = new JFrame("Error");
                    error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    error.setTitle("Administrator");
                    error.setSize(250, 150);
                    //This will center the JFrame in the middle of the screen
                    error.setLocationRelativeTo(null);
                    //Shows GUI
                    error.setVisible(true);
                    //main menu panel for buttons
                    JPanel p = new JPanel();
                    p.setLayout(null);

                    JLabel error1 = new JLabel("Incorrect username or password.");
                    JLabel error2 = new JLabel("Please try again");
                    JButton ok = new JButton("Retry");

                    error1.setBounds(15, 20, 200, 25);
                    error2.setBounds(75, 35, 100, 25);
                    error.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    ok.setBounds(75, 70, 70, 30);

                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            error.dispose();
                            username1.setText("");
                            password1.setText("");
                        }
                    });

                    p.add(ok);
                    p.add(error1);
                    p.add(error2);

                    error.add(p);
                }
            }
        });
    }

    public static void createAdminFrame(){
        JFrame loginscreen = new JFrame("Administrator");
        //make sure the program exits when the frame closes
        loginscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginscreen.setTitle("Administrator");
        loginscreen.setSize(450, 200);
        //This will center the JFrame in the middle of the screen
        loginscreen.setLocationRelativeTo(null);
        //Shows GUI
        loginscreen.setVisible(true);
        //main menu panel for buttons
        JPanel p = new JPanel();
        JPanel panel = new JPanel();
        JFrame done = new JFrame();

        p.setLayout(null);

        JButton addBook = new JButton("New Book");
        JButton deleteBook = new JButton("Delete Book");
        JButton deleteMember = new JButton("Delete Member");
        JButton mainBack = new JButton("Back");

        JButton ok = new JButton("Done");
        ok.setBounds(55, 40, 70, 30);

        addBook.setBounds(40, 45, 110, 30);
        deleteBook.setBounds(150, 45, 110, 30);
        deleteMember.setBounds(260, 45, 110, 30);
        mainBack.setBounds(150, 80, 110, 30);

        p.add(addBook);
        p.add(deleteBook);
        p.add(deleteMember);
        p.add(mainBack);

        loginscreen.add(p);

        JButton submit = new JButton("Submit");
        submit.setBounds(335, 20, 70, 40);
        submit.setFont(new Font("Arial", Font.PLAIN, 10));

        JButton back = new JButton("Back");
        back.setBounds(335, 95, 70, 40);
        back.setFont(new Font("Arial", Font.PLAIN, 10));

        // Database URL
        String DB_URL = "jdbc:mysql://localhost:3306/testdb";

        //  Database credentials
        String USER = "root";
        String PASS = "SDPTEAM27";

        addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(addBook);
                p.remove(deleteBook);
                p.remove(deleteMember);
                p.remove(mainBack);
                p.repaint();

                JLabel title = new JLabel("Title:");
                JTextField title1 = new JTextField(10);

                JLabel author = new JLabel("Author:");
                JTextField author1 = new JTextField(10);

                JLabel price = new JLabel("Price:");
                JTextField price1 = new JTextField(10);

                p.setLayout(null);
                title.setBounds(80, 10, 50, 50);
                title1.setBounds(160, 25, 120, 25);
                title1.setLayout(new BorderLayout());

                author.setBounds(80, 50, 60, 50);
                author1.setBounds(160, 65, 120, 25);
                author1.setLayout(new BorderLayout());

                price.setBounds(80, 85, 80, 50);
                price1.setBounds(160, 105, 120, 25);
                price1.setLayout(new BorderLayout());

                Connection conn = null;
                PreparedStatement stmt = null;
                //Statement stmt = null;
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet rs = null;
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        //Statement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");
                            //stmt = conn.createStatement();
                            String sql = "select * from bookinfo where Title = '" + title1.getText() + "'";

                            stmt = conn.prepareStatement(sql);

                            rs = stmt.executeQuery(sql);
                            //search results
                            String info = "0";
                            int copies;
                            while (rs.next()) {
                                info = rs.getString(3);
                            }
                            //if book exists, increment copy counter
                            if (info != null || !info.isEmpty() || info != "0") {
                                //cast copies from database as int and increment since book record exists
                                copies = Integer.parseInt(info);
                                copies++;
                                //update exising record
                                sql = "UPDATE bookinfo" + " SET Copies = ? " + "WHERE Title = '" + title1.getText() + "'";

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, Integer.toString(copies));
                                stmt.executeUpdate();
                            }
                            //no book exists, create row
                            if(info == "0"){
                                sql = "INSERT INTO bookinfo (Title, Author, Copies, Price)"
                                        + " values (?, ?, ?, ?)";

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, title1.getText());
                                stmt.setString(2, author1.getText());
                                stmt.setString(3, "1");
                                stmt.setString(4, price1.getText());
                                stmt.executeUpdate();
                            }
                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                    loginscreen.dispose();
                                    createAdminFrame();
                                }
                            });
                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginscreen.dispose();
                        createAdminFrame();
                    }
                });
                p.add(title);
                p.add(title1);
                p.add(author);
                p.add(author1);
                p.add(price);
                p.add(price1);
                p.add(submit);
                p.add(back);

                loginscreen.add(p);
            }
        });

        deleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(addBook);
                p.remove(deleteBook);
                p.remove(deleteMember);
                p.remove(mainBack);
                p.repaint();

                JLabel title = new JLabel("Title:");
                JTextField title1 = new JTextField(10);

                JLabel author = new JLabel("Author:");
                JTextField author1 = new JTextField(10);

                p.setLayout(null);
                title.setBounds(80, 10, 50, 50);
                title1.setBounds(160, 25, 120, 25);
                title1.setLayout(new BorderLayout());

                author.setBounds(80, 50, 60, 50);
                author1.setBounds(160, 65, 120, 25);
                author1.setLayout(new BorderLayout());

                Connection conn = null;
                PreparedStatement stmt = null;
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet rs = null;
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        //Statement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);

                            //STEP 4: Execute a query
                            System.out.println("");
                            String sql = "select * from bookinfo where Title = '" + title1.getText() + "' AND Author = '" + author1.getText() + "'";

                            stmt = conn.prepareStatement(sql);
                            System.out.println(sql);

                            rs = stmt.executeQuery(sql);
                            //search results
                            String info = "0";
                            int copies = 0;
                            while (rs.next()) {
                                info = rs.getString(2);
                                copies = Integer.parseInt(rs.getString(3));
                            }
                            if (copies > 0) {
                                //cast copies from database as int and increment since book record exists
                                copies = Integer.parseInt(info);
                                copies--;
                                //update exising record
                                sql = "UPDATE bookinfo" + " SET Copies = ? " + "WHERE Title = '" + title1.getText() + "'";

                                stmt = conn.prepareStatement(sql);
                                stmt.setString(1, Integer.toString(copies));
                                stmt.executeUpdate();
                            }
                            else if(copies == 0){
                                sql = "DELETE FROM bookinfo WHERE" + " Title = '" + title1.getText() + "' AND Author = '" + author1.getText() + "'";

                                stmt = conn.prepareStatement(sql);
                                stmt.executeUpdate();

                            }
                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                    loginscreen.dispose();
                                    createAdminFrame();
                                }
                            });
                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginscreen.dispose();
                        createAdminFrame();
                    }
                });
                p.add(title);
                p.add(title1);
                p.add(author);
                p.add(author1);
                p.add(submit);
                p.add(back);

                loginscreen.add(p);
            }
        });

        deleteMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(addBook);
                p.remove(deleteBook);
                p.remove(deleteMember);
                p.remove(mainBack);
                p.repaint();

                JLabel name = new JLabel("Name:");
                JTextField name1 = new JTextField(10);

                JLabel address = new JLabel("Address:");
                JTextField address1 = new JTextField(10);

                JLabel idnum = new JLabel("IDNum:");
                JTextField idnum1 = new JTextField(10);

                p.setLayout(null);
                name.setBounds(80, 10, 50, 50);
                name1.setBounds(160, 25, 120, 25);
                name1.setLayout(new BorderLayout());

                address.setBounds(80, 50, 60, 50);
                address1.setBounds(160, 65, 120, 25);
                address1.setLayout(new BorderLayout());

                idnum.setBounds(80, 85, 80, 50);
                idnum1.setBounds(160, 105, 120, 25);
                idnum1.setLayout(new BorderLayout());

                Connection conn = null;
                PreparedStatement stmt = null;
                //Statement stmt = null;
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ResultSet rs = null;
                        Connection conn = null;
                        PreparedStatement stmt = null;
                        //Statement stmt = null;
                        try{
                            //Register JDBC driver
                            Class.forName("com.mysql.jdbc.Driver");

                            //Open a connection
                            System.out.println("Connecting to testdb");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            System.out.println("Connected");

                            //STEP 4: Execute a query
                            System.out.println("");
                            //stmt = conn.createStatement();

                            String sql = "DELETE FROM members WHERE" + " Name = '" + name1.getText() + "' AND Address = '" + address1.getText() + "' AND IDNum = '" + idnum1.getText() + "'";

                            stmt = conn.prepareStatement(sql);
                            stmt.executeUpdate();

                            done.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            done.setTitle("Success");
                            done.setSize(200, 150);

                            //This will center the JFrame in the middle of the screen
                            done.setLocationRelativeTo(null);
                            panel.setLayout(null);
                            panel.add(ok);
                            done.add(panel);
                            done.setVisible(true);
                            ok.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    done.dispose();
                                    loginscreen.dispose();
                                    createAdminFrame();
                                }
                            });
                        }catch(SQLException se){
                            //Handle errors for JDBC
                            se.printStackTrace();
                        }catch(Exception d){
                            //Handle errors for Class.forName
                            d.printStackTrace();
                        }finally{
                            //finally block used to close resources
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException se){
                            }// do nothing
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }//end finally try
                        }//end try
                        System.out.println("Goodbye!");
                    }
                });

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginscreen.dispose();
                        createAdminFrame();
                    }
                });
                p.add(name);
                p.add(name1);
                p.add(address);
                p.add(address1);
                p.add(idnum);
                p.add(idnum1);
                p.add(submit);
                p.add(back);

                loginscreen.add(p);
            }
        });

        mainBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginscreen.dispose();
                createGUI();
            }
        });
    }

    public static void main(String[] args)  {
        createGUI();
    }//end main
}
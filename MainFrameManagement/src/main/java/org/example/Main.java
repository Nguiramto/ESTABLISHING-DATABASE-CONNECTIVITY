package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JFrame {
    private static Connection conn;

    public Main() {
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Initialize Database Connection
        conn = getDatabaseConnection();

        ActivityLogger logger = new ActivityLogger();
        OrderController controller = new OrderController(logger, conn); // Pass DB connection to controllers

        JTabbedPane tabbedPane = new JTabbedPane();

        MenuPanel menuPanel = new MenuPanel(controller);
        OrderPanel orderPanel = new OrderPanel(controller);
        TransactionPanel transactionPanel = new TransactionPanel(controller);
        ActivityPanel activityPanel = new ActivityPanel(logger);
        RevenuePanel revenuePanel = new RevenuePanel(controller);

        tabbedPane.addTab("Menu", menuPanel);
        tabbedPane.addTab("Orders", orderPanel);
        tabbedPane.addTab("Transaction", transactionPanel);
        tabbedPane.addTab("Activities", activityPanel);
        tabbedPane.addTab("Revenue", revenuePanel);

        // Refresh respective panels when selected
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 1) orderPanel.refreshTable();
            if (selectedIndex == 2) transactionPanel.refreshTransactionTable();
            if (selectedIndex == 3) activityPanel.refreshActivityLog();
            if (selectedIndex == 4) revenuePanel.refreshRevenueData();
        });

        add(tabbedPane);
    }

    // Establish Database Connection
    private static Connection getDatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/restaurantmanagement";
        String user = "root";
        String password = "#FOcus2710#";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

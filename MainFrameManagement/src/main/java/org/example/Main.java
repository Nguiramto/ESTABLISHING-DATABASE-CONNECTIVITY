package org.example;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        ActivityLogger logger = new ActivityLogger();
        OrderController controller = new OrderController(logger);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

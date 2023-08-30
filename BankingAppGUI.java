// All necesaary imports
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// Class to contain all code
public class BankingAppGUI {
    // Main method
    public static void main(String[] args) {
        // Create a new JFrame instance
        ImageIcon originalIcon = new ImageIcon("bank.jpg");

        // Scale the image to fit the entire JLayeredPane
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        
        // Repeat for welcome page
        ImageIcon welcomeIcon = new ImageIcon("front1.jpg");
        Image welcomeImage = welcomeIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);

        // Create the main panel with the scaled image
        ImagePanel mainPanel = new ImagePanel(scaledImage);
        
        // Setup Jframe
        JFrame frame = new JFrame("Personal Banking App");

        // Create the welcome panel
        ImagePanel welcomePanel = new ImagePanel(welcomeImage);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); 

        // Add welcome message and customize
        JLabel welcomeLabel = new JLabel("Welcome to your personalized banking app");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add other message and customize
        JLabel clickToContinueLabel = new JLabel("(Click anywhere to continue)");
        clickToContinueLabel.setForeground(Color.WHITE);
        clickToContinueLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        clickToContinueLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        // Add labels to welcome panel
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 160)));
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 205)));
        welcomePanel.add(clickToContinueLabel);
        welcomePanel.add(Box.createVerticalGlue());

        // Add a mouse listener to welcome panel
        welcomePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Switch to the main panel when the welcome panel is clicked
                frame.setContentPane(mainPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        
        // Create a set to store account numbers
        HashSet<String> accountNumbers = new HashSet<>();

        // Create a map to store balances for each account number
        HashMap<String, Double> accountBalances = new HashMap<>();

        // Map to store transactions
        HashMap<String, List<String>> transactionHistory = new HashMap<>();

        // Create JComboBox
        String[] items = {"Checking", "Savings"};
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setLocation(100, 70); // x, y
        comboBox.setSize(comboBox.getPreferredSize());

        // Create JLabels
        JLabel aclabel = new JLabel("Account Number:");
        aclabel.setLocation(5, 9);
        aclabel.setSize(aclabel.getPreferredSize());
        JLabel amlabel = new JLabel("Amount:");
        amlabel.setLocation(5, 40);
        amlabel.setSize(amlabel.getPreferredSize());
        JLabel atlabel = new JLabel("Account Type:");
        atlabel.setLocation(5, 74);
        atlabel.setSize(atlabel.getPreferredSize());
        JLabel allabel = new JLabel("Account List:");
        allabel.setLocation(5, 320);
        allabel.setSize(allabel.getPreferredSize());

        // Set color for Jlabels
        aclabel.setForeground(Color.WHITE);
        amlabel.setForeground(Color.WHITE);
        atlabel.setForeground(Color.WHITE);
        allabel.setForeground(Color.WHITE);


        // Create input modifier for account ID
        JTextField actextField = new JTextField(5);
        int defaultHeight = actextField.getPreferredSize().height;
        actextField.setBounds(123, 5, 50, defaultHeight);

        // Restrict JTextField to 4 characters
        ((AbstractDocument) actextField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= 4 && string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            // Restrict JTextField to integers only
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() - length + text.length() <= 4 && text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        
        // Create input modifier for amount field for integers and one decimal point only
        JTextField amtextField = new JTextField(5);
        amtextField.setBounds(65, 35, 100, defaultHeight);
        amtextField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String text = amtextField.getText();
        int decimalIndex = text.indexOf('.');
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != '.') {
            e.consume();  
        }
        // Ignore additional decimal points
        if (c == '.' && text.contains(".")) {
            e.consume();  
        }
        // Ignore if more than two digits after the decimal point
        if (decimalIndex != -1 && Character.isDigit(c) && (text.length() - decimalIndex) > 2) {
            e.consume();  
        }
        }
        });

        // Create JButtons
        JButton depositButton = new JButton("Deposit");
        depositButton.setLocation(55, 105); 
        depositButton.setSize(depositButton.getPreferredSize());
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setLocation(50, 140); 
        withdrawButton.setSize(withdrawButton.getPreferredSize());
        JButton createButton = new JButton("Create Account");
        createButton.setLocation(35, 175); 
        createButton.setSize(createButton.getPreferredSize());
        JButton viewButton = new JButton("View Balance");
        viewButton.setLocation(40, 210); 
        viewButton.setSize(viewButton.getPreferredSize());
        JButton stateButton = new JButton("Statement History");
        stateButton.setLocation(25, 245); 
        stateButton.setSize(stateButton.getPreferredSize());
        JButton delButton = new JButton("Delete Account");
        delButton.setLocation(30, 280); 
        delButton.setSize(delButton.getPreferredSize());
        JButton gobButton = new JButton("Go Back");
        gobButton.setLocation(750, 10); 
        gobButton.setSize(gobButton.getPreferredSize());

        // Set x position to the panel using width minus the button width and a margin for go back button
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = mainPanel.getWidth();
                int buttonWidth = gobButton.getWidth();
                int newXPosition = panelWidth - buttonWidth - 5;
                gobButton.setLocation(newXPosition, gobButton.getY());
            }
        });

        // Goes back to welcome screen
        gobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the welcome panel
                frame.setContentPane(welcomePanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Create a vertical box
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setBounds(95, 320, 200, 300);

        // Add components to the mainPanel
        mainPanel.add(aclabel, new Integer(1));
        mainPanel.add(actextField, new Integer(1));
        mainPanel.add(amlabel, new Integer(1));
        mainPanel.add(amtextField, new Integer(1));
        mainPanel.add(atlabel, new Integer(1));
        mainPanel.add(comboBox, new Integer(1));
        mainPanel.add(depositButton, new Integer(1));
        mainPanel.add(withdrawButton, new Integer(1));
        mainPanel.add(createButton, new Integer(1));
        mainPanel.add(viewButton, new Integer(1));
        mainPanel.add(stateButton, new Integer(1));
        mainPanel.add(allabel, new Integer(1));
        mainPanel.add(delButton, new Integer(1));
        mainPanel.add(verticalBox, new Integer(1));
        mainPanel.add(gobButton, new Integer(1));

        // Button for creating new account
        createButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String accountType = (String) comboBox.getSelectedItem();
        String accountId = actextField.getText();
        // Check if the account number is in use
        if (accountNumbers.contains(accountId)) {
            JOptionPane.showMessageDialog(frame, "Account number in use. Please choose another.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  
        }
        // Check if user has entered a 4-digit account number and selected account type
        if (accountId.length() != 4 || (accountType != "Checking" && accountType != "Savings")) {
            JOptionPane.showMessageDialog(frame, "Please enter both a valid 4 digit account number and select either checking or savings.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Create new JLabel with account information and add to verticalBox
            JLabel accountLabel = new JLabel(accountId + " (" + accountType + ")");
            accountLabel.setForeground(Color.WHITE);
            verticalBox.add(accountLabel);

            // Refresh the verticalBox to display the new label
            verticalBox.revalidate();  

            // Clear the actextField and comboBox after adding the account
            actextField.setText("");
            comboBox.setSelectedIndex(0);  
            accountNumbers.add(accountId);
            accountBalances.put(accountId, 0.0);
        }
        }
        });

        // Button for deleting accounts
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountIdToDelete = actextField.getText();

                // Check if actextField is empty
                if (accountIdToDelete.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid account number for deletion.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number is not valid (less than 4 digits)
                if (accountIdToDelete.length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Account number not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if account number exists
                if (accountNumbers.contains(accountIdToDelete)) {

                    // Remove the account number from the set
                    accountNumbers.remove(accountIdToDelete);

                    // Search for corresponding JLabel in the verticalBox and remove it
                    for (Component component : verticalBox.getComponents()) {
                        if (component instanceof JLabel) {
                            JLabel label = (JLabel) component;
                            if (label.getText().startsWith(accountIdToDelete)) {
                                verticalBox.remove(label);
                                break;
                            }
                        }
                    }

                    // Refresh verticalBox to reflect changes
                    verticalBox.revalidate();
                    verticalBox.repaint();
                    // Clear actextField after deleting account
                    actextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Account number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Button for despoting a balance
        depositButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String accountId = actextField.getText();
        String amountStr = amtextField.getText();

        // Check if both fields are empty
        if (accountId.isEmpty() && amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both a valid account number and amount for deposit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Check if account number field is empty or not valid
        if (accountId.isEmpty() || accountId.length() < 4 || !accountNumbers.contains(accountId)) {
            if (accountId.isEmpty() || accountId.length() < 4) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid account number.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Account number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // Check if amount field is empty
        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an amount for deposit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check the account number exists and amount is valid
        if (accountNumbers.contains(accountId) && !amountStr.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                double currentBalance = accountBalances.get(accountId);
                accountBalances.put(accountId, currentBalance + amount);
                transactionHistory.computeIfAbsent(accountId, k -> new ArrayList<>()).add("+ $" + String.format("%.2f", amount));
                String formattedBalance = String.format("%.2f", accountBalances.get(accountId)); // Format the balance to always show two decimal places
                JOptionPane.showMessageDialog(frame, "Deposit successful. New balance: $" + formattedBalance, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear the amount field
                amtextField.setText("");  
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid account number and amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
        });

        // Button for withdrawing a balance
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = actextField.getText();
                String amountStr = amtextField.getText();

                // Check if both fields are empty
                if (accountId.isEmpty() && amountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both a valid account number and amount for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number field is empty
                if (accountId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid account number for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number is less than 4 digits
                if (accountId.length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Account number not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number exists
                if (!accountNumbers.contains(accountId)) {
                    JOptionPane.showMessageDialog(frame, "Account number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if amount field is empty
                if (amountStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter an amount for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number exists and amount is valid
                if (accountNumbers.contains(accountId) && !amountStr.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        double currentBalance = accountBalances.get(accountId);
                        if (currentBalance < amount) {
                            JOptionPane.showMessageDialog(frame, "Not enough balance left to withdraw. Current balance: $" + String.format("%.2f", currentBalance) + ".", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        accountBalances.put(accountId, currentBalance - amount);
                        transactionHistory.computeIfAbsent(accountId, k -> new ArrayList<>()).add("- $" + String.format("%.2f", amount));
                        JOptionPane.showMessageDialog(frame, "Withdrawal Successful. New balance: $" + String.format("%.2f", accountBalances.get(accountId)), "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Clear amount field
                        amtextField.setText("");  
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid account number and amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Button to view balance
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountIdToView = actextField.getText();

                // Check if actextField is empty
                if (accountIdToView.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid account number to view balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number is not valid (less than 4 digits)
                if (accountIdToView.length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Account number not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number exists
                if (accountNumbers.contains(accountIdToView)) {
                    double balance = accountBalances.get(accountIdToView);
                    String accountType = "Checking"; 
                    for (Component component : verticalBox.getComponents()) {
                        if (component instanceof JLabel) {
                            JLabel label = (JLabel) component;
                            if (label.getText().startsWith(accountIdToView)) {
                                if (label.getText().contains("Savings")) {
                                    accountType = "Savings";
                                }
                                break;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(frame, accountType + " Account Balance: $" + String.format("%.2f", balance), "Balance", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Account number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Button to view statement history
        stateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountIdToView = actextField.getText();

                // Check if actextField is empty
                if (accountIdToView.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid account number for viewing.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if account number is not valid (less than 4 digits)
                if (accountIdToView.length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Account number not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if account number exists
                if (accountNumbers.contains(accountIdToView)) {
                    List<String> transactions = transactionHistory.getOrDefault(accountIdToView, new ArrayList<>());
                    double currentBalance = accountBalances.get(accountIdToView);
                    StringBuilder displayMessage = new StringBuilder();
                    if (transactions.isEmpty()) {
                        displayMessage.append("No statements available for this account\n");
                    } else {
                        displayMessage.append(String.join("\n", transactions)).append("\n");
                    }
                    displayMessage.append("Current Balance: $").append(String.format("%.2f", currentBalance));
                    JOptionPane.showMessageDialog(frame, displayMessage.toString(), "Statement History", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Account number does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add the panel to the frame
        frame.setContentPane(welcomePanel);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(800, 600);

        // Center the frame on the screen (this is redundant when maximizing, but kept for clarity)
        frame.setLocationRelativeTo(null);
        
        // Make the frame visible
        frame.setVisible(true);
    }
}
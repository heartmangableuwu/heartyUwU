package heartasset;

import javax.swing.*;
import java.awt.*;   
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class AssetFrame extends JFrame {
    
    // Declare UI components
    private JTextField txtAssetID, txtAssetName, txtAssetType, txtPurchaseDate, txtLocationID, txtAssetDescription, txtAssetValue;
    private JComboBox<String> cmbStatus;
    private JButton btnAddAsset, btnViewAvailableAssets, btnUpdateAsset, btnDeleteAsset;
    private JTable assetTable;
    private DefaultTableModel tableModel;

    // Constructor to set up the UI layout
    public AssetFrame() {
        setTitle("Asset Tracking System");
        setSize(800, 1000);  // Adjust window size for additional fields
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font labelFont = new Font("Arial", Font.BOLD, 14);  // Set font for labels
        Font inputFont = new Font("Arial", Font.PLAIN, 14); // Set font for text fields
        Insets insets = new Insets(10, 10, 10, 10);  // Set padding around components

        // Initialize text fields and combo box for status
        txtAssetID = createTextField(inputFont);
        txtAssetName = createTextField(inputFont);
        txtAssetType = createTextField(inputFont);
        txtPurchaseDate = createTextField(inputFont);
        txtLocationID = createTextField(inputFont);
        txtAssetDescription = createTextField(inputFont);  // New field for Asset Description
        txtAssetValue = createTextField(inputFont);        // New field for Asset Value
        cmbStatus = new JComboBox<>(new String[]{"Available", "In Use", "Under Maintenance"});
        cmbStatus.setFont(inputFont);

        // Initialize buttons
        btnAddAsset = createButton("Add Asset");
        btnViewAvailableAssets = createButton("View Assets");
        btnUpdateAsset = createButton("Update Asset");
        btnDeleteAsset = createButton("Delete Asset");

        // Set up table for displaying asset information
        tableModel = new DefaultTableModel(new Object[]{"Asset ID", "Asset Name", "Asset Type", "Purchase Date", "Location ID", "Description", "Value", "Status"}, 0);
        assetTable = new JTable(tableModel);
        assetTable.setFont(inputFont);
        assetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Single selection mode for the table

        JScrollPane tableScrollPane = new JScrollPane(assetTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 300));

        // Layout the components using GridBagConstraints
        gbc.insets = insets;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Existing fields layout
        gbc.gridx = 0; gbc.gridy = 0; add(createLabel("Asset ID:", labelFont), gbc); gbc.gridx = 1; add(txtAssetID, gbc);
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Asset Name:", labelFont), gbc); gbc.gridx = 1; add(txtAssetName, gbc);
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Asset Type:", labelFont), gbc); gbc.gridx = 1; add(txtAssetType, gbc);
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Purchase Date (yyyy-MM-dd):", labelFont), gbc); gbc.gridx = 1; add(txtPurchaseDate, gbc);
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Location ID:", labelFont), gbc); gbc.gridx = 1; add(txtLocationID, gbc);
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Asset Description:", labelFont), gbc); gbc.gridx = 1; add(txtAssetDescription, gbc);  // New field for Description
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Asset Value:", labelFont), gbc); gbc.gridx = 1; add(txtAssetValue, gbc);  // New field for Value
        gbc.gridx = 0; gbc.gridy++; add(createLabel("Status:", labelFont), gbc); gbc.gridx = 1; add(cmbStatus, gbc);
        
        // Add buttons for user actions
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; add(btnAddAsset, gbc);
        gbc.gridy++; add(btnUpdateAsset, gbc);
        gbc.gridy++; add(btnDeleteAsset, gbc);
        gbc.gridy++; add(btnViewAvailableAssets, gbc);

        // Add table to display assets
        gbc.gridy++; gbc.fill = GridBagConstraints.BOTH; add(tableScrollPane, gbc);

        // Add action listeners to buttons
        btnAddAsset.addActionListener(e -> addAsset());
        btnViewAvailableAssets.addActionListener(e -> viewAllAssets());
        btnUpdateAsset.addActionListener(e -> updateAsset());
        btnDeleteAsset.addActionListener(e -> deleteAsset());

        setLocationRelativeTo(null);  // Center the frame on the screen
    }

    // Helper method to create labels
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    // Helper method to create text fields
    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField(20);
        textField.setFont(font);
        return textField;
    }

    // Helper method to create buttons
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    // Method to clear input fields after actions
    public void clear() {
        txtAssetID.setText("");
        txtAssetName.setText("");
        txtAssetType.setText("");
        txtPurchaseDate.setText("");
        txtLocationID.setText("");
        txtAssetDescription.setText("");  // Clear the new fields
        txtAssetValue.setText("");        // Clear the new fields
    }

    // Method to add a new asset to the database
    private void addAsset() {
        try {
            String assetName = txtAssetName.getText().trim();
            String assetType = txtAssetType.getText().trim();
            String purchaseDate = txtPurchaseDate.getText().trim();
            String status = cmbStatus.getSelectedItem() != null ? cmbStatus.getSelectedItem().toString() : "";
            String assetDescription = txtAssetDescription.getText().trim();  // New field
            String assetValue = txtAssetValue.getText().trim();  // New field
            int locationID;

            // Validation for required fields
            if (assetName.isEmpty() || assetType.isEmpty() || purchaseDate.isEmpty() || status.isEmpty() || assetDescription.isEmpty() || assetValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Location ID
            try {
                locationID = Integer.parseInt(txtLocationID.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Location ID must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Asset Value as a number
            double value = 0.0;
            try {
                value = Double.parseDouble(assetValue);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Asset Value must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AssetDao assetDao = new AssetDao();  // Access database to add asset
            assetDao.addAsset(assetName, assetType, purchaseDate, status, locationID, assetDescription, value);  // Modified method call to include new fields
            JOptionPane.showMessageDialog(this, "Asset added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clear();
            viewAllAssets();  // Refresh asset list
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update an existing asset
    private void updateAsset() {
        try {
            int assetId = Integer.parseInt(txtAssetID.getText().trim());
            String assetName = txtAssetName.getText().trim();
            String assetType = txtAssetType.getText().trim();
            String purchaseDate = txtPurchaseDate.getText().trim();
            String status = cmbStatus.getSelectedItem() != null ? cmbStatus.getSelectedItem().toString() : "";
            String assetDescription = txtAssetDescription.getText().trim();
            String assetValue = txtAssetValue.getText().trim();
            int locationID = Integer.parseInt(txtLocationID.getText().trim());

                        double value = 0.0;
            // Validate Asset Value as a number
            try {
                value = Double.parseDouble(assetValue);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Asset Value must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Access the DAO to update the asset in the database
            AssetDao assetDao = new AssetDao();
            assetDao.updateAsset(assetId, assetName, assetType, purchaseDate, status, locationID, assetDescription, value);  // Modified method call to include new fields
            JOptionPane.showMessageDialog(this, "Asset updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clear();  // Clear the form
            viewAllAssets();  // Refresh the asset list
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete an asset
    private void deleteAsset() {
        try {
            int assetId = Integer.parseInt(txtAssetID.getText().trim());
            AssetDao assetDao = new AssetDao();
            assetDao.deleteAsset(assetId);  // Call the DAO method to delete the asset
            JOptionPane.showMessageDialog(this, "Asset deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clear();  // Clear the form
            viewAllAssets();  // Refresh the asset list
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to view all assets from the database
    private void viewAllAssets() {
        try {
            AssetDao assetDao = new AssetDao();
            List<Asset> assets = assetDao.getAllAssets();  // Fetch all assets from the database

            // Clear the existing rows in the table
            tableModel.setRowCount(0);

            // Populate the table with updated asset information
            for (Asset asset : assets) {
                tableModel.addRow(new Object[]{
                        asset.getAssetID(),
                        asset.getAssetName(),
                        asset.getAssetType(),
                        asset.getPurchaseDate(),
                        asset.getLocationID(),
                        asset.getAssetDescription(),  // Display asset description
                        asset.getAssetValue(),        // Display asset value
                        asset.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> {
            new AssetFrame().setVisible(true);  // Show the main application window
        });
    }
}

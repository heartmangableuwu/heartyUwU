package heartasset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssetDao {

    // Method to add a new asset to the database, including assetDescription and assetValue
    public void addAsset(String assetName, String assetType, String purchaseDate, String status, int locationID, String assetDescription, double assetValue) throws Exception {
        String sql = "INSERT INTO assettbl (AssetName, AssetType, PurchaseDate, Status, LocationID, AssetDescription, AssetValue) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convert purchaseDate string to SQL Date format
            Date sqlDate;
            try {
                sqlDate = Date.valueOf(purchaseDate);
            } catch (IllegalArgumentException e) {
                throw new Exception("Invalid date format. Use yyyy-MM-dd.");
            }

            // Set parameters for the SQL query
            stmt.setString(1, assetName);
            stmt.setString(2, assetType);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, status);
            stmt.setInt(5, locationID);
            stmt.setString(6, assetDescription);  // Set asset description
            stmt.setDouble(7, assetValue);        // Set asset value

            stmt.executeUpdate(); // Execute the insert query
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage()); // Handle SQL errors
        }
    }

    // Method to retrieve all assets from the database, including the new fields
    public List<Asset> getAllAssets() throws Exception {
        List<Asset> assets = new ArrayList<>();
        String sql = "SELECT * FROM assettbl";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Iterate over the result set and create Asset objects
            while (rs.next()) {
                int assetID = rs.getInt("AssetID");
                String assetName = rs.getString("AssetName");
                String assetType = rs.getString("AssetType");
                String purchaseDate = rs.getString("PurchaseDate");
                int locationID = rs.getInt("LocationID");
                String status = rs.getString("Status");
                String assetDescription = rs.getString("AssetDescription");  // Retrieve asset description
                double assetValue = rs.getDouble("AssetValue");  // Retrieve asset value

                // Create an Asset object and add it to the list
                Asset asset = new Asset(assetID, assetName, assetType, purchaseDate, locationID, status, assetDescription, assetValue);
                assets.add(asset);
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage()); // Handle SQL errors
        }
        return assets; // Return the list of assets
    }

    // Method to update an existing asset's details in the database, including assetDescription and assetValue
    public void updateAsset(int assetId, String assetName, String assetType, String purchaseDate, String status, int locationID, String assetDescription, double assetValue) throws Exception {
        String sql = "UPDATE assettbl SET AssetName = ?, AssetType = ?, PurchaseDate = ?, Status = ?, LocationID = ?, AssetDescription = ?, AssetValue = ? WHERE AssetID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convert purchaseDate string to SQL Date format
            Date sqlDate;
            try {
                sqlDate = Date.valueOf(purchaseDate);
            } catch (IllegalArgumentException e) {
                throw new Exception("Invalid date format. Use yyyy-MM-dd.");
            }

            // Set parameters for the SQL query
            stmt.setString(1, assetName);
            stmt.setString(2, assetType);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, status);
            stmt.setInt(5, locationID);
            stmt.setString(6, assetDescription);  // Set new asset description
            stmt.setDouble(7, assetValue);        // Set new asset value
            stmt.setInt(8, assetId);

            // Execute the update query and check if rows were affected
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Asset not found for update."); // If no rows updated, throw an exception
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage()); // Handle SQL errors
        }
    }

    // Method to delete an asset from the database
    public void deleteAsset(int assetId) throws Exception {
        String sql = "DELETE FROM assettbl WHERE AssetID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assetId);

            // Execute the delete query and check if rows were affected
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Asset not found for deletion."); // If no rows deleted, throw an exception
            }
        } catch (SQLException e) {
            throw new Exception("Database error: " + e.getMessage()); // Handle SQL errors
        }
    }
}

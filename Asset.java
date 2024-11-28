package heartasset;

public class Asset {

    private int assetID;      // Unique identifier for the asset
    private String assetName; // Name of the asset
    private String assetType; // Type of the asset (e.g., equipment, vehicle, etc.)
    private String purchaseDate; // Date when the asset was purchased
    private int locationID;   // ID of the location where the asset is stored
    private String status;    // Current status of the asset (e.g., active, retired, etc.)
    private String assetDescription; // Description of the asset
    private double assetValue; // Value of the asset

    // Constructor to initialize the Asset object with the new fields
    public Asset(int assetID, String assetName, String assetType, String purchaseDate, int locationID, String status, String assetDescription, double assetValue) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.assetType = assetType;
        this.purchaseDate = purchaseDate;
        this.locationID = locationID;
        this.status = status;
        this.assetDescription = assetDescription;
        this.assetValue = assetValue;
    }

    // Getter method for assetID
    public int getAssetID() {
        return assetID;
    }

    // Getter method for assetName
    public String getAssetName() {
        return assetName;
    }

    // Getter method for assetType
    public String getAssetType() {
        return assetType;
    }

    // Getter method for purchaseDate
    public String getPurchaseDate() {
        return purchaseDate;
    }

    // Getter method for locationID
    public int getLocationID() {
        return locationID;
    }

    // Getter method for status
    public String getStatus() {
        return status;
    }

    // Getter method for assetDescription
    public String getAssetDescription() {
        return assetDescription;
    }

    // Getter method for assetValue
    public double getAssetValue() {
        return assetValue;
    }

    // Setter method for assetID
    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    // Setter method for assetName
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    // Setter method for assetType
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    // Setter method for purchaseDate
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // Setter method for locationID
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    // Setter method for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Setter method for assetDescription
    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    // Setter method for assetValue
    public void setAssetValue(double assetValue) {
        this.assetValue = assetValue;
    }
}

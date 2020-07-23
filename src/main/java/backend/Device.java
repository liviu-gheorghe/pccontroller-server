package backend;

public class Device {

    public String fullName="Unknown";
    public String brand="Unknown";
    public String model="Unknown";
    public String osLevel="Unknown";
    public String ipAddress="Unknown";
    public int connectionID = -1;
    public String description = "";


    public static class DeviceBuilder {

        private final Device device;

        public DeviceBuilder() {
            device = new Device();
        }

        public Device setFullName(String fullName) {
            device.fullName = fullName;
            return device;
        }

        public Device setBrand(String brand) {
            device.brand = brand;
            return device;
        }

        public Device setModel(String model) {
            device.model = model;
            return device;
        }

        public Device setOsLevel(String osLevel) {
            device.osLevel = osLevel;
            return device;
        }

        public Device setIpAddress(String ipAddress) {
            device.ipAddress = ipAddress;
            return device;
        }

        public Device setConnectionID(int connectionID) {
            device.connectionID = connectionID;
            return device;
        }

        public Device setDescription(String description) {
            device.description = description;
            return device;
        }
    }
}
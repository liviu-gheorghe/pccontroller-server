package backend;

public class Device {

    public String fullName="Unknown";
    public String brand="Unknown";
    public String model="Unknown";
    public String osLevel="Unknown";
    public String ipAddress="Unknown";
    public String connectionID;
    public String description = "";


    public static class DeviceBuilder {

        private final Device device;

        public DeviceBuilder() {
            device = new Device();
        }

        public DeviceBuilder setFullName(String fullName) {
            device.fullName = fullName;
            return this;
        }

        public DeviceBuilder setBrand(String brand) {
            device.brand = brand;
            return this;
        }

        public DeviceBuilder setModel(String model) {
            device.model = model;
            return this;
        }

        public DeviceBuilder setOsLevel(String osLevel) {
            device.osLevel = osLevel;
            return this;
        }

        public DeviceBuilder setIpAddress(String ipAddress) {
            device.ipAddress = ipAddress;
            return this;
        }

        public DeviceBuilder setConnectionID(String connectionID) {
            device.connectionID = connectionID;
            return this;
        }

        public DeviceBuilder setDescription(String description) {
            device.description = description;
            return this;
        }
        public Device build() {
            return device;
        }
    }
}
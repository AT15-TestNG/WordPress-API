package constants;

public class DomainAppEnums {
    public enum UserRole {
        SUBSCRIBER("subscriber"),
        ADMINISTRATOR("administrator");

        private final String userRole;

        private UserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUserRole() {
            return userRole;
        }

        @Override
        public String toString() {
            return userRole;
        }
    }
}
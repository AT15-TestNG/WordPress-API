package constants;

public class DomainAppEnums {
    public enum UserRole {
        SUBSCRIBER("subscriber"),
        ADMINISTRATOR("administrator"),
        ADMINISTRATOR2("administrator2");
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
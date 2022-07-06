package constants;

public class DomainAppEnums {
    public enum UserRole {
        ADMIN("administrator"),
        SUBS("subscriber");

        private final String userRole;

        UserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getUserRole() {
            return userRole;
        }
    }
}

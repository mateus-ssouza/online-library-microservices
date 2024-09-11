package online.library.account.utils;

public abstract class Strings {

    public static final class AUTH {
        public static final String ERROR_CREDENTIALS = "invalid login or password.";
    }

    public static final class USER {
        public static final String ERROR_FIND_ALL_LIST = "Error while retrieving the list of users.";
        public static final String NOT_FOUND = "User not found.";
        public static final String CONFLICT = "User's CPF, email, or login already in use.";
        public static final String ERROR_FIND_BY_ID = "Error while trying to retrieve a user by ID.";
        public static final String ERROR_CREATE = "Error while trying to create a user.";
        public static final String ERROR_UPDATE = "Error while trying to update a user.";
        public static final String ERROR_DELETE = "Error while trying to delete a user.";
    }

    public static final class ADMIN {
        public static final String ERROR_FIND_ALL_LIST = "Error while retrieving the list of admins.";
        public static final String NOT_FOUND = "Admin not found.";
        public static final String CONFLICT = "Admin's CPF, email, or login already in use.";
        public static final String ERROR_FIND_BY_ID = "Error while trying to retrieve an admin by ID.";
        public static final String ERROR_CREATE = "Error while trying to create an admin.";
        public static final String ERROR_UPDATE = "Error while trying to update an admin.";
        public static final String ERROR_DELETE = "Error while trying to delete an admin.";
    }

    public static final class ERROR {
        public static final String GENERATE_TOKEN = "Error while generating token.";
        public static final String INVALID_TOKEN = "Invalid token.";
        public static final String INVALID_TOKEN_FORMAT = "Invalid token format.";
    }
}

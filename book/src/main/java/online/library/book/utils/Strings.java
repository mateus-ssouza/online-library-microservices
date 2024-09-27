package online.library.book.utils;

public abstract class Strings {

    public static final class BOOK {
        public static final String ERROR_FIND_ALL_LIST = "Error while retrieving the list of books.";
        public static final String NOT_FOUND = "Book not found.";
        public static final String CONFLICT = "Book's title or isbn already in use.";
        public static final String ERROR_FIND_BY_ID = "Error while trying to retrieve a book by ID.";
        public static final String ERROR_CREATE = "Error while trying to create a book.";
        public static final String ERROR_UPDATE = "Error while trying to update a book.";
        public static final String ERROR_DELETE = "Error while trying to delete a book.";
    }

    public static final class COPY {
        public static final String ERROR_FIND_ALL_LIST = "Error while retrieving the list of copies.";
        public static final String NOT_FOUND = "Copy not found.";
        public static final String CONFLICT = "Copy's copy code already in use.";
        public static final String ERROR_FIND_BY_ID = "Error while trying to retrieve a copy by ID.";
        public static final String ERROR_CREATE = "Error while trying to create a copy.";
        public static final String ERROR_DELETE = "Error while trying to delete a copy.";
    }
}

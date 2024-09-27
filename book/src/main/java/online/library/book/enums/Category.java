package online.library.book.enums;

import lombok.Getter;

@Getter
public enum Category {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    BIOGRAPHY("Biography"),
    SELF_HELP("Self Help"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    ADVENTURE("Adventure");

    private String category;

    Category(String category) {
        this.category = category;
    }
}

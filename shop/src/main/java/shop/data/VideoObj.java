package shop.data;

/**
 * Immutable Data Class for video objects.
 * Comprises a triple: title, year, director.
 *
 * @objecttype Immutable Data Class
 * @see Data
 */
public record VideoObj(String title, int year, String director) implements Video {

    // Note: I overwrote the constructor since we need to trim the title and director and check for illegal arguments.

    /**
     * Initialize all object attributes.
     * Title and director are "trimmed" to remove leading and final space.
     * @throws IllegalArgumentException if any object invariant is violated.
     */
    public VideoObj(String title, int year, String director) {
        if (  (title == null)
        || (director == null)
        || (year <= 1800)
        || (year >= 5000)) {
        System.out.println("Video Error: Invalid year");
        throw new IllegalArgumentException();
        }
        this.title = title.trim();
        this.director = director.trim();
        this.year = year;
        if (  ("".equals(this.title))
        || ("".equals(this.director))) {
        System.out.println("Video Error: Empty title or director");
        throw new IllegalArgumentException();
        }
    }

    /**
     * Compares the attributes of this object with those of thatObject, in
     * the following order: title, year, director.
     * @param that the VideoObj2 to be compared.
     * @return a negative integer, zero, or a positive integer as this
     *  object is less than, equal to, or greater than that object.
     */
    public int compareTo(Object that) {
        if (that == null) {
          throw new NullPointerException();
        }
        if (!(that instanceof Video)) {
          throw new ClassCastException();
        }
        Video thatVideo = (Video) that;

        int titleDiff = title.compareTo(thatVideo.title());
        if (titleDiff != 0) {
        return titleDiff;
        }
        int yearDiff = year - thatVideo.year();
        if (yearDiff != 0) {
        return yearDiff;
        }
        int directorDiff = director.compareTo(thatVideo.director());
        if (directorDiff != 0) {
        return directorDiff;
        }
        return 0;
    }
} 

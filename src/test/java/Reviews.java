import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * Created by darshan on 6/29/18.
 */
public class Reviews {

    @JsonProperty("results")
    private List<Review> reviews = new ArrayList();

    public Reviews() {
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void sortByTitleLength() {
        Collections.sort(getReviews(), new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                if (o1.getTitle().length() < o2.getTitle().length()) {
                    return -1;
                } else if (o1.getTitle().length() == o2.getTitle().length()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "reviews=" + reviews +
                '}';
    }
}

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return "Reviews{" +
                "reviews=" + reviews +
                '}';
    }
}

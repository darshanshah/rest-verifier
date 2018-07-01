import org.apache.http.client.methods.CloseableHttpResponse;
/**
 * Created by darshan on 6/29/18.
 */
public class Response {
    Reviews reviews;
    CloseableHttpResponse httpResponse;

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public CloseableHttpResponse getCloseableHttpResponse() {
        return httpResponse;
    }

    public void setCloseableHttpResponse(CloseableHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public String toString() {
        return "Response{" +
                "reviews=" + reviews +
                ", httpResponse=" + httpResponse +
                '}';
    }

}

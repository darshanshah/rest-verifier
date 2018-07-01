import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by darshan on 6/29/18.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class BusinessRequirement {

    private SplunkResponse splunkResponse;

    @Before
    public void before() throws IOException, URISyntaxException {
        splunkResponse = getAllResponses("");
    }


    public SplunkResponse getAllResponses(String movie) throws URISyntaxException, IOException {
        SplunkResponse splunkResponse;
        RestRequest main = new RestRequest();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies").addParameter("q", movie);
        splunkResponse = main.getRequest(uriBuilder);

        return splunkResponse;
    }

    public SplunkResponse getSpecificMovieWithOrWithoutCount(String movie, int count)
            throws URISyntaxException, IOException {
        SplunkResponse splunkResponse;
        RestRequest main = new RestRequest();
        URIBuilder uriBuilder = new URIBuilder();
        if (count > 0) {
            uriBuilder.setHost("splunk.mocklab.io").setScheme("https").
                    setPath("/movies").addParameter("q", movie).
                    addParameter("count", Integer.toString(count));
        } else {
            uriBuilder.setHost("splunk.mocklab.io").setScheme("https").
                    setPath("/movies").addParameter("q", movie);
        }

        splunkResponse = main.getRequest(uriBuilder);
        Assert.assertEquals(splunkResponse.httpResponse.getStatusLine().getStatusCode(), 200);

        return splunkResponse;
    }


    @Test
    public void testToGetSpecificMovie() throws URISyntaxException, IOException {
        SplunkResponse splunkResponse = getSpecificMovieWithOrWithoutCount("spidey", -1);
        Assert.assertEquals(splunkResponse.httpResponse.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(splunkResponse.reviews.getReviews().get(0).getTitle(), "spidey");
    }

    @Test
    public void testToGetSpecificMovieWithCount() throws URISyntaxException, IOException {
        SplunkResponse splunkResponse = getSpecificMovieWithOrWithoutCount("spidey", 2);
        Assert.assertEquals(splunkResponse.httpResponse.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(splunkResponse.reviews.getReviews().size(), 2);
    }

    @Test
    public void testNoMoviesShouldHaveSameImage() {
        Assert.assertEquals("Failed to verify that No movie should have Same image", Util.noMoviesShouldHaveSameImage(splunkResponse.reviews), true);

    }

    @Test
    public void testCheckTitleHasPalindrome() {
        try {
            Assert.assertEquals("Failed to verify that Title has Palindrome", Util.checkTitleHasPalindrome(splunkResponse.reviews), true);
        } catch (Exception ex) {
            System.out.println("Exception from testCheckTitleHasPalindrome()" + ex.getMessage());
        }
    }

    @Test
    public void testSumOfGenIdMaxSeven() {
        Assert.assertEquals("Failed to verify that Title has Palindrome", Util.sumOfGenIdMaxSeven(splunkResponse.reviews), true);
    }


    @Test
    public void testRestStatus() {
        Assert.assertEquals(splunkResponse.httpResponse.getStatusLine().getStatusCode(), 200);
    }


    @Test
    public void testPostMethod() throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        RestRequest restRequest = new RestRequest();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies");
        Movie movie = new Movie("spidey", "spider man description");
        String jsonBody = objectMapper.writeValueAsString(movie);
        SplunkResponse splunkResponse = restRequest.postRequest(uriBuilder, jsonBody);
        Assert.assertEquals(splunkResponse.httpResponse.getStatusLine().getStatusCode(), 200);
    }
}

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;

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
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());

        return splunkResponse;
    }


    @Test
    public void testToGetSpecificMovie() throws URISyntaxException, IOException {
        String movieString = "batman";
        SplunkResponse splunkResponse = getSpecificMovieWithOrWithoutCount(movieString, -1);
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());
        Assert.assertEquals("Failed to Verify Test With Specific Movie",
                movieString, splunkResponse.reviews.getReviews().get(0).getTitle());
    }

    @Test
    public void testToGetSpecificMovieWithCount() throws URISyntaxException, IOException {
        String movieString = "batman";
        SplunkResponse splunkResponse = getSpecificMovieWithOrWithoutCount(movieString, 2);
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());
        Assert.assertTrue("Failed To verify Get Specific movie with count",
                splunkResponse.reviews.getReviews().size() == 2);
    }

    @Test
    public void testSortingRequirement() {
        Assert.assertEquals("Failed to Verify Sorting requirement", true,
                Util.checkSortingRequirement(splunkResponse.reviews));
    }

    @Test
    public void testNoMoviesShouldHaveSameImage() {
        Assert.assertEquals("Failed to verify that No two movies should have the same image", true,
                Util.noMoviesShouldHaveSameImage(splunkResponse.reviews));

    }

    @Test
    public void testCheckTitleHasPalindrome() {
        Assert.assertEquals("Failed to verify that There is at least one movie in the database whose" +
                        " title has a palindrome in it.", true,
                Util.checkTitleHasPalindrome(splunkResponse.reviews)
        );
    }

    @Test
    public void testSumOfGenIdMaxSeven() {
        int counter = Util.sumOfGenId(splunkResponse.reviews);
        Assert.assertTrue("Failed to verify that The number of movies whose sum of genre_ids >400 " +
                "should be no more than 7.", counter <= 7);
    }


    @Test
    public void testRestStatus() {
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testTitleContainTitle() {
        int counter = Util.checkTitleContainTitle(splunkResponse.reviews);
        System.out.println(counter);
        Assert.assertTrue(counter >= 2);
    }

    @Test
    public void testPostStatus() throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        RestRequest restRequest = new RestRequest();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies");
        Movie movie = new Movie("spidey", "spider man description");
        String jsonBody = objectMapper.writeValueAsString(movie);
        SplunkResponse splunkResponse = restRequest.postRequest(uriBuilder, jsonBody);
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testPostRequestInGet() throws URISyntaxException, IOException {
        String movieString = "spidey";
        URIBuilder uriBuilder = new URIBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        RestRequest restRequest = new RestRequest();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies");
        Movie movie = new Movie(movieString, "spider man description");
        String jsonBody = objectMapper.writeValueAsString(movie);
        SplunkResponse splunkResponse = restRequest.postRequest(uriBuilder, jsonBody);
        Assert.assertEquals(200, splunkResponse.httpResponse.getStatusLine().getStatusCode());


        SplunkResponse splunkGetResponse = getSpecificMovieWithOrWithoutCount(movieString, 1);
        Assert.assertEquals(200, splunkGetResponse.httpResponse.getStatusLine().getStatusCode());
        Assert.assertEquals("Failed to Verified Post request", movieString, splunkGetResponse.reviews.getReviews().get(0).getTitle());


    }
}

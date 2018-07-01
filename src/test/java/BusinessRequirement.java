import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by darshan on 6/29/18.
 */

public class BusinessRequirement {

    public Response getAllResponses(String movie) throws URISyntaxException, IOException {
        Response response;
        RestRequest main = new RestRequest();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies").addParameter("q", movie);
        response = main.getRequest(uriBuilder);

        return response;
    }

    public Response getSpecificMovieWithOrWithoutCount(String movie, int count)
            throws URISyntaxException, IOException {
        Response response;
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

        response = main.getRequest(uriBuilder);
        Assert.assertEquals(response.httpResponse.getStatusLine().getStatusCode(), 200);

        return response;
    }

    public boolean noMoviesShouldHaveSameImage(Reviews reviews) {
        HashSet<String> uniqueImagePath = new HashSet<>();
        Boolean isContainUniquePath = true;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            if (review.getPoster_path() != null) {
                if (uniqueImagePath.contains(review.getPoster_path())) {
                    isContainUniquePath = false;
                    break;
                } else {
                    uniqueImagePath.add(review.getPoster_path());
                }
            }
        }
        return isContainUniquePath;
    }

    public boolean isPalindrome(String word) {
        for (int i = 0, j = word.length() - 1; i < j; i++, j--) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public boolean sumOfGenIdMaxSeven(Reviews reviews) {
        boolean result = true;
        int count = 0;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            List<Integer> listOfGenId = review.getGenre_ids();
            int total = 0;
            for (int j = 0; j < listOfGenId.size(); j++) {
                total += listOfGenId.get(j);
                if (total > 400) {
                    count++;
                    break;
                }
            }
        }

        if (count > 7) {
            result = false;
        }
        return result;
    }

    public boolean checkTitleHasPalindrome(Reviews reviews) {
        int count = 0;
        boolean result = true;
        for (int i = 0; i < reviews.getReviews().size(); i++) {
            Review review = reviews.getReviews().get(i);
            String[] listOFWord = review.getTitle().split(" ");
            for (int j = 0; j < listOFWord.length; j++) {
                if (isPalindrome(listOFWord[j])) {
                    count++;
                    break;
                }
            }
        }
        if (count < 1) {
            result = false;
        }
        return result;
    }

    @Test
    public void testToGetSpecificMovie() throws URISyntaxException, IOException {
        Response response = getSpecificMovieWithOrWithoutCount("spidey", -1);
        Assert.assertEquals(response.httpResponse.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(response.reviews.getReviews().get(0).getTitle(), "spidey");
    }

    @Test
    public void testToGetSpecificMovieWithCount() throws URISyntaxException, IOException {
        Response response = getSpecificMovieWithOrWithoutCount("spidey", 2);
        Assert.assertEquals(response.httpResponse.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(response.reviews.getReviews().size(), 2);
    }


    public void testNoMoviesShouldHaveSameImage(Response response) {
        try {
            Assert.assertEquals(noMoviesShouldHaveSameImage(response.reviews), true);
        } catch (Exception ex) {
            System.out.println("Exception from testNoMoviesShouldHaveSameImage() " + ex.getMessage());
        }
    }


    public void testCheckTitleHasPalindrome(Response response) {
        try {
            Assert.assertEquals(checkTitleHasPalindrome(response.reviews), true);
        } catch (Exception ex) {
            System.out.println("Exception from testCheckTitleHasPalindrome()" + ex.getMessage());
        }
    }


    public void testSumOfGenIdMaxSeven(Response response) {
        try {
            Assert.assertEquals(sumOfGenIdMaxSeven(response.reviews), true);
        } catch (Exception ex) {
            System.out.println("Exception from testSumOfGenIdMaxSeven()" + ex.getMessage());
        }
    }


    @Test
    public void testBusinessRequirement() throws URISyntaxException, IOException {
        Response response = getAllResponses("");
        Assert.assertEquals(response.httpResponse.getStatusLine().getStatusCode(), 200);
        testNoMoviesShouldHaveSameImage(response);
        testCheckTitleHasPalindrome(response);
        testSumOfGenIdMaxSeven(response);
    }

    @Test
    public void testPostMethod() throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        RestRequest restRequest = new RestRequest();
        uriBuilder.setHost("splunk.mocklab.io").setScheme("https").setPath("/movies");
        Movie movie = new Movie("spidey", "spider man description");
        String jsonBody = objectMapper.writeValueAsString(movie);
        Response response = restRequest.postRequest(uriBuilder, jsonBody);
        Assert.assertEquals(response.httpResponse.getStatusLine().getStatusCode(), 200);
    }
}

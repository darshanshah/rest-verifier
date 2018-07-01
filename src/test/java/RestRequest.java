
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by darshan on 6/29/18.
 */

public class RestRequest {

    /**
     * curl -X GET https://splunk.mocklab.io/movies?q=batman -H Accept: "application/json";
     */
    public Response getRequest(URIBuilder uriBuilder) throws URISyntaxException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest request = RequestBuilder.get().setUri(uriBuilder.build())
                .addHeader(new BasicHeader(HttpHeaders.ACCEPT, "application/json")).build();

        CloseableHttpResponse httpResponse = httpClient.execute(request);

        ObjectMapper objectMapper = new ObjectMapper();

        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
        String theString = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8.toString());
        Reviews reviews = objectMapper.readValue(theString, Reviews.class);
        System.out.println(reviews.getReviews().size());
        Response response = new Response();
        response.reviews = reviews;
        response.httpResponse = httpResponse;
        return response;
    }

    /**
     * curl -d '{"name":"superman", "description":"the best movie ever made"}' -H "Content-Type: application/json" -X POST https://splunk.mocklab.io/movies
     *
     * @throws URISyntaxException
     * @throws IOException
     */

    public Response postRequest(URIBuilder uriBuilder,String jsonBody) throws URISyntaxException, IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpUriRequest request = RequestBuilder.post().setUri(uriBuilder.build())
                .addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"))
                .setEntity(new StringEntity(jsonBody)).build();

        CloseableHttpResponse httpResponse = httpClient.execute(request);

        Response response = new Response();
        response.httpResponse = httpResponse;
        System.out.println(httpResponse);
        return response;
    }
}


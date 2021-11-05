package Unify;

/* Apache HTTPComponents ver 4.5.13 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/* orgjson ver 20210307 */
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static String apiKey = "29ace529-6a3e-4963-84ca-05a92b721ecd";
    private String username;
    private String password;
    private String spendingPassword;
    private final String mnemonicPhrase;
    private int accountTotal;


    public User(String username, String password) {
        if (username.equals(null) && password.equals(null)){

        }
        mnemonicPhrase = null;
    }

    public void createAccount(){

    }

    
    public void currentADAMarketPrice(){

        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";

        /* CoinMarketCap api query parameters */
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("symbol","ADA"));
        parameters.add(new BasicNameValuePair("convert","USD"));

        try {
            String result = makeAPICall(uri, parameters);
            System.out.println(result);

            /* Serialize the result of the api call to a JSON object and get the current market price */
            String jsonString = result;
            JSONObject obj = new JSONObject(jsonString);
            Double price = obj.getJSONObject("data").getJSONObject("ADA").getJSONObject("quote").getJSONObject("USD").getDouble("price");
            System.out.println(price);
        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
    }


    /**
     * Builds the API call and returns a String with the response
     * @param uri               the absolute server address for the get requests from the api
     * @param parameters        the CoinMarketCap api query parameters
     * @return response_content a string in JSON format detailing the api call
     * @throws URISyntaxException
     * @throws IOException
     */
    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException{

        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }
}

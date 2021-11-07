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
import java.util.Random;

public class User {

    private static String apiKey = "29ace529-6a3e-4963-84ca-05a92b721ecd";
    private String accountID;
    private String username;
    private String password;
    private String spendingPassword;
    private String mnemonicPhrase;
    private double accountTotal;
    private String address;


    public User(String accountID, String username, String password, String spendingPassword, double accountTotal) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.spendingPassword = spendingPassword;
        this.accountTotal = accountTotal;
    }

    public void createAccount(){

    }

    public String getUsername() { return username; }

    public double getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(double accountTotal) {
        this.accountTotal = accountTotal;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAddress() {
        address = generateAddress();
    }

    public String getAddress(){
        return address;
    }

    public void setMnemonicPhrase(){
        mnemonicPhrase = generateMnemonicPhrase();
    }

    public String generateMnemonicPhrase(){
        String phrase = "";
        char[] alphabet = new char[26];
        int character = 65;
        for (int i = 0; i < 26; i++){
            alphabet[i] = (char) character;
        }
        Random random = new Random();
        for (int i = 0; i < 5; i++){
            phrase = phrase + alphabet[random.nextInt(0,26)];
        }

        return phrase;
    }


    public String generateAddress(){
        Random random = new Random();
        int random1 = random.nextInt(1000, 5001);
        int random2 = random.nextInt(1000, 5001);
        String address = getAccountID()+'.'+String.valueOf(random1)+'.'+String.valueOf(random2);
        return address;
    }


    public double currentADAMarketPrice(){

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

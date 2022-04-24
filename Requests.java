import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requests {
    private final int CONNECTION_TIMEOUT = 5000;
    private final String url = "http://127.0.0.1:5000";

    private String readInputStream(InputStream inputStream){
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
    private HttpURLConnection connection(String u, String req) throws IOException{
        final URL url = new URL(u);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (req.equals("POST")) con.setDoOutput(true);
        con.setRequestMethod(req);
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);
        return con;
    }

    /*
    * %Y-%m-%d format date
    * */
    public JSONObject register(String firstname, String lastname, String sex, String date, String email, String password) throws IOException{
        String result = "";

        HttpURLConnection con = connection(this.url + "/register", "POST");

        JSONObject obj = new JSONObject();
        obj.put("firstname", firstname);
        obj.put("lastname", lastname);
        obj.put("sex", sex);
        obj.put("date", date);
        obj.put("email", email);
        obj.put("password", password);


        OutputStream out = con.getOutputStream();
        out.write(obj.toString().getBytes());
        out.flush();

        result = readInputStream(con.getInputStream());

        return (JSONObject) JSONValue.parse(result);
    }
    public JSONObject login(String email, String password) throws IOException
    {
        String result = "";
        final HttpURLConnection con = connection(this.url + "/login", "POST");

        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("password", password);


        OutputStream out = con.getOutputStream();
        out.write(obj.toString().getBytes());
        out.flush();

        result = readInputStream(con.getInputStream());

        return (JSONObject) JSONValue.parse(result);

    }
    public void get() throws IOException{
        String result = "";
        final HttpURLConnection con = connection(this.url + "/", "GET");

        result = readInputStream(con.getInputStream());

        System.out.println(result);
    }

}

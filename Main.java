import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;


public class Main {
    public static void main(String[] args) throws IOException
    {
        JSONObject object;
        Requests requests = new Requests();
        object = requests.register("Egor", "Letov", "M", "1965-10-10", "letov@mail.ru", "123456");
        System.out.println(object);
        System.out.println(object.get("result"));
        object = requests.login("sada@adsad.ru", "123456");
        System.out.println(object);


    }



}

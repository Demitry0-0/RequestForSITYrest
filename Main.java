import java.io.IOException;

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

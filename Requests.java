package com.example.myapp;

import android.os.AsyncTask;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Requests {
    private final int CONNECTION_TIMEOUT = 7000;
    private final String url = "https://e448-77-220-52-74.ngrok.io";//"http://127.0.0.1:5000";
    private Run empty = new Run(null);

    public JSONObject result = new JSONObject(); //null


    private class Run extends AsyncTask {
        private JSONObject result;

        Run(JSONObject res) {
            result = res;
        }

        String readInputStream(InputStream inputStream) {
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

        HttpURLConnection connection(String u, String req) throws IOException {
            final URL url = new URL(u);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (req.equals("POST")) con.setDoOutput(true);
            con.setRequestMethod(req);
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(CONNECTION_TIMEOUT);
            con.setReadTimeout(CONNECTION_TIMEOUT);
            return con;
        }


        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                work();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public void work() throws Exception {
        }

    }

    public void waiting() {
        try {
            if (empty.getStatus() == AsyncTask.Status.RUNNING)
                empty.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getResult() {
        waiting();
        return result;
    }

    /*
     * %Y-%m-%d format date
     * */
    public void register(String firstname, String lastname, String sex, String date, String email, String password) {
        empty = new Run(result) {
            @Override
            public void work() throws Exception {
                HttpURLConnection con = connection(url + "/register", "POST");

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

                result = (JSONObject) JSONValue.parse(readInputStream(con.getInputStream()));
            }
        };
        empty.execute();
    }

    public void login(String email, String password) {
        empty = new Run(result) {
            @Override
            public void work() throws IOException {
                final HttpURLConnection con = connection(url + "/login", "POST");

                JSONObject obj = new JSONObject();
                obj.put("email", email);
                obj.put("password", password);


                OutputStream out = con.getOutputStream();
                out.write(obj.toString().getBytes());
                out.flush();

                result = (JSONObject) JSONValue.parse(readInputStream(con.getInputStream()));

                // System.out.println("from login " + result);
            }
        };
        empty.execute();
    }

    //"title", "geoX", "geoY", "start", "organizerid", "maxCountMembers"
    public void create_event(String title, double geoX, double geoY, String start, long id, long maxCountMembers) {
        empty = new Run(result) {
            @Override
            public void work() throws IOException {
                HttpURLConnection con = connection(url + "/register", "POST");

                JSONObject obj = new JSONObject();
                obj.put("title", title);
                obj.put("geoX", geoX);
                obj.put("geoY", geoY);
                obj.put("start", start);
                obj.put("id", id);
                obj.put("maxCountMembers", maxCountMembers);


                OutputStream out = con.getOutputStream();
                out.write(obj.toString().getBytes());
                out.flush();

                result = (JSONObject) JSONValue.parse(readInputStream(con.getInputStream()));

            }
        };
        empty.execute();
    }

    public void hello() {
        empty = new Run(result) {
            @Override
            public void work() throws IOException {
                final HttpURLConnection con = connection(url + "/", "GET");

                System.out.println("RESULT: " + readInputStream(con.getInputStream()));

            }
        };
        empty.execute();
    }

}

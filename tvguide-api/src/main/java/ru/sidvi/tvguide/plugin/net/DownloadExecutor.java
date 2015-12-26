package ru.sidvi.tvguide.plugin.net;

import ru.sidvi.tvguide.plugin.Event;

import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sidvi on 26.12.2015.
 */
public class DownloadExecutor {

    public static final String DEFAULT_METHOD = "GET";
    private String url;
    private String method;
    private Map<String, String> headers;
    private List<Event> downloaded = new ArrayList<Event>();

    public DownloadExecutor(String url, Map<String, String> headers) {
        this(url, DEFAULT_METHOD, headers);
    }

    public DownloadExecutor(String url, String method, Map<String, String> headers) {
        this.url = url;
        this.method = method;
        this.headers = headers;
    }

    public void execute(DownloadAction action) {
        try {
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
            c.setRequestMethod(method);
            for (Map.Entry<String, String> e : headers.entrySet()) {
                c.setRequestProperty(e.getKey(), e.getValue());
            }

            int responseCode = c.getResponseCode();

            InputStream is = null;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = c.getInputStream();
                downloaded = action.download(is);
            }else{
                System.out.println("Can't connect to " + url);
                System.out.println("Status code is " + responseCode);
            }

            if (is != null) {
                is.close();
            }
            c.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Event> getDownloaded() {
        return downloaded;
    }
}

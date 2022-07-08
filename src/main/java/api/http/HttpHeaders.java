package api.http;

import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.List;

public class HttpHeaders {
    private final List<Header> headers = new ArrayList<>();

    public void addHeader(Header header) {
        headers.add(header);
    }

    public Headers getHeaders() {
        return new Headers(headers);
    }
}

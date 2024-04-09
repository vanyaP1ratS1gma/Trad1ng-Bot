package org.adapter.model;

import org.adapter.model.utils.KeyResponseParser;
import org.adapter.view.UserInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IndicatorsService {
    private static URI taApi = URI.create("https://api.taapi.io/rsi?secret=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbHVlIjoiNjU2ZWRkMmUxNDBjZmQ3MjNkYTM1MjcwIiwiaWF0IjoxNzAxNzY0NTIwLCJleHAiOjMzMjA2MjI4NTIwfQ.V7EKcplwelDHKb3kYG3WCEFlTOpco8qVuJUag5k4INM&exchange=binance&symbol=SOL/USDT&interval=1m&period=5");

    //exchange=binance symbol=SOL/USDT interval=1m period=5
    public static int reqRSI() {
        int rsi = 0;
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(taApi).GET().build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String value = KeyResponseParser.parseInfo(response, "value");

            rsi = (int) Double.parseDouble(value);
        } catch (IOException | InterruptedException ignored) {

        }
        return rsi;
    }
}

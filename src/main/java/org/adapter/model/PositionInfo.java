package org.adapter.model;

import org.adapter.model.enums.EndPoints;
import org.adapter.model.enums.RequestHeaders;
import org.adapter.model.enums.RequestType;
import org.adapter.model.enums.Ticker;
import org.adapter.model.utils.KeyResponseParser;
import org.adapter.model.utils.Signature;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PositionInfo {
    private User user;

    public PositionInfo(User user) {
        this.user = user;
    }

    private HttpResponse requestPositionsInfo() {
        String data = "";
        long expires = Signature.createExpires();
        HttpResponse<String> response = null;

        try {
            String signature = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.GET), EndPoints.POSITION.path, expires, data);
            HttpRequest requestToGetPosition = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.POSITION))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signature)
                    .GET()
                    .build();

            response = user.getClient().send(requestToGetPosition, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {

        }
        return response;
    }

    public String getAvgEntryPriceByTicker(Ticker ticker) {
        HttpResponse response = requestPositionsInfo();
        if (response != null && response.statusCode() == 200) {
            return KeyResponseParser.parseInfoByTicker(response, "avgEntryPrice", ticker);
        } else {
            return "0";
        }
    }

    public String getCurrentPnlByTicker(Ticker ticker) {
        HttpResponse response = requestPositionsInfo();
        if (response != null && response.statusCode() == 200) {
            return KeyResponseParser.parseInfoByTicker(response, "unrealisedPnl", ticker);
        } else {
            return "0";
        }
    }

    public String getPosCommByTicker(Ticker ticker) {
        HttpResponse response = requestPositionsInfo();
        if (response != null && response.statusCode() == 200) {
            return KeyResponseParser.parseInfoByTicker(response, "posComm", ticker);
        } else {
            return "0";
        }
    }
}

package org.adapter.controller;

import org.adapter.model.enums.*;
import org.adapter.view.UserInterface;
import org.adapter.model.*;
import org.adapter.model.utils.RequestData;
import org.adapter.model.utils.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {

    public static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private User user;

    private PositionInfo positionInfo;

    public Controller(User user) {
        this.user = user;
        positionInfo = new PositionInfo(user);
    }

    public void createMarketOrder(Ticker symbol, OrderSide side, double quantity) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(symbol));
        data.addValues(Parameters.side, String.valueOf(side));
        data.addValues(Parameters.orderQty, String.valueOf(quantity));
        long expires = Signature.createExpires();
        try {
            String signPost = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToBuy = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signPost)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToBuy, HttpResponse.BodyHandlers.ofString());
            logger.debug("Market Order Created, data: " + data);
//            UserInterface.showMessage(String.valueOf(response));
//            System.out.println(response.body());
        } catch (Exception e) {
            UserInterface.showMessage("Exception: " + e.getMessage());
        }
    }

    public void createLimitOrder(Ticker symbol, OrderSide side, double price, int quantity) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(symbol));
        data.addValues(Parameters.side, String.valueOf(side));
        data.addValues(Parameters.ordType, "Limit");
        data.addValues(Parameters.price, String.valueOf(price));
        data.addValues(Parameters.orderQty, String.valueOf(quantity));
        long expires = Signature.createExpires();
        try {
            String signPost = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToBuy = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signPost)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToBuy, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
            //UserInterface.showMessage(String.valueOf(response));
            logger.debug("Limit Order Created, data: " + data);
        } catch (Exception e) {
            UserInterface.showMessage("Exception: " + e.getMessage());
        }
    }

    public void closePositionByTicker(Ticker ticker) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(ticker));
        data.addValues(Parameters.execInst, "Close");

        long expires = Signature.createExpires();
        try {
            String signature = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToClose = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signature)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToClose, HttpResponse.BodyHandlers.ofString());

            //UserInterface.showMessage(String.valueOf(response.body()));

            logger.debug("Position close for ticker: " + ticker);
        } catch (Exception e) {
            UserInterface.showMessage("Exception: " + e.getMessage());
        }
    }

    public void canselAllOrders() {
        String data = "";
        long expires = Signature.createExpires();
        String signature = null;
        try {
            signature = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.DELETE), EndPoints.CANSEL_ALL_ORDERS.path, expires, data);
        } catch (Exception e) {
            UserInterface.showMessage("Exception: " + e.getMessage());
        }
        HttpRequest requestToGetPosition = HttpRequest.newBuilder()
                .uri(URI.create(user.getBaseUrl() + EndPoints.CANSEL_ALL_ORDERS))
                .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                .header((RequestHeaders.KEY.toString()), user.getKey())
                .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                .header((RequestHeaders.SIGNATURE.toString()), signature)
                .DELETE()
                .build();
        HttpResponse<String> response = null;
        try {
            response = user.getClient().send(requestToGetPosition, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            UserInterface.showMessage("Exception: " + e.getMessage());
        }
    }

    public PositionInfo getInfo() {
        return positionInfo;
    }
}
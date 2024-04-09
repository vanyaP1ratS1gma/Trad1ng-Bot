package org.adapter.strategy;

import org.adapter.controller.Controller;
import org.adapter.model.IndicatorsService;
import org.adapter.model.enums.OrderSide;
import org.adapter.model.enums.Ticker;
import org.adapter.view.UserInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BuyLowRsi implements Runnable {
    private Controller controller;

    public BuyLowRsi(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        int value;
        int orderQuantity = 3000;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        while (true) {
            try {
                value = IndicatorsService.reqRSI();
                System.out.println(dateFormat.format(new Date()) + " / rsi = " + value);
                if (value <= 20) {
                    boolean innerFlag = true;
                    while (innerFlag) {
                        Thread.sleep(20000);
                        value = IndicatorsService.reqRSI();
                        System.out.println(dateFormat.format(new Date()) + " / in2Loop / rsi = " + value);
                        if (value > 20) {
                            UserInterface.showMessage(dateFormat.format(new Date()) + " / BUY!");
                            controller.createMarketOrder(Ticker.SOLUSDT, OrderSide.BUY, orderQuantity);
                            Thread.sleep(1000);
                            controller.canselAllOrders();
                            String avgEntryPriceSS = controller.getInfo().getAvgEntryPriceByTicker(Ticker.SOLUSDT);
                            double avgEntryPrice = Double.parseDouble(avgEntryPriceSS);
                            for (int i = 0; i < 20; i++) {
                                avgEntryPrice *= 0.993;
                                String d = String.valueOf(avgEntryPrice).substring(0, 5);
                                avgEntryPrice = Double.parseDouble(d);
                                controller.createLimitOrder(Ticker.SOLUSDT, OrderSide.BUY, avgEntryPrice, orderQuantity);
                            }
                            Thread.sleep(TimeUnit.MINUTES.toMillis(15));
                            innerFlag = false;
                        }
                    }
                }
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                UserInterface.showMessage("Exception: " + e.getMessage());
            }
        }
    }
}
package org.adapter.strategy;

import org.adapter.controller.Controller;
import org.adapter.model.enums.Ticker;
import org.adapter.view.UserInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TakeProfit implements Runnable {
    private Controller controller;

    public TakeProfit(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        double pnl = 0;
        double paidComm = 0;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        while (true) {
            try {
                pnl = Double.parseDouble(controller.getInfo().getCurrentPnlByTicker(Ticker.SOLUSDT));
                paidComm = Double.parseDouble(controller.getInfo().getPosCommByTicker(Ticker.SOLUSDT)) + 1;

            } catch (Exception e) {

            }
            if (pnl != 0) {
                UserInterface.showMessage(dateFormat.format(new Date()) + " / pnl = " + pnl + " / paidComm = " + paidComm + " / targetPnl = " + (paidComm * 6));
            }
            if (pnl >= paidComm * 6) {
                controller.closePositionByTicker(Ticker.SOLUSDT);
                controller.canselAllOrders();
                UserInterface.showMessage(dateFormat.format(new Date()) + " / Position closed!");
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {

            }

        }
    }
}

package org.adapter;

import org.adapter.controller.Controller;
import org.adapter.model.User;
import org.adapter.model.utils.UserInfoLoader;
import org.adapter.strategy.ToTheMoon;


public class App {

    public static void main(String[] args) {
        User user = new User(UserInfoLoader.readProps());
        Controller controller = new Controller(user);
        new Thread(new ToTheMoon(controller)).start();
//        double pnl = Double.parseDouble(controller.getInfo().getCurrentPnlByTicker(Ticker.SOLUSDT));
//        double paidComm = Double.parseDouble(controller.getInfo().getPosCommByTicker(Ticker.SOLUSDT)) + 1;
//        if (pnl != 0) {
//            UserInterface.showMessage("pnl = " + pnl + " / paidComm = " + paidComm + " / targetPnl = " + (paidComm * 6));
//        }
//        controller.createMarketOrder(Ticker.SOLUSDT, OrderSide.BUY, 1000);
//        double pnl = Double.parseDouble(controller.getInfo().getCurrentPnlByTicker(Ticker.SOLUSDT));
//        double paidComm = Double.parseDouble(controller.getInfo().getPosCommByTicker(Ticker.SOLUSDT)) + 1;
//        String avgEntryPriceSS = controller.getInfo().getAvgEntryPriceByTicker(Ticker.SOLUSDT);
//        System.out.println(pnl + " " + paidComm + " " + avgEntryPriceSS);
//        controller.closePositionByTicker(Ticker.SOLUSDT);
//        controller.canselAllOrders();
//        Toolkit.getDefaultToolkit().beep();
//        Add Test Line
    }
}
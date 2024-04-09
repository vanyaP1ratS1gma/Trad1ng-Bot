package org.adapter.strategy;

import org.adapter.controller.Controller;

public class ToTheMoon implements Runnable{
    private Controller controller;

    public ToTheMoon(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        new Thread(new BuyLowRsi(controller)).start();
        new Thread(new TakeProfit(controller)).start();
    }
}

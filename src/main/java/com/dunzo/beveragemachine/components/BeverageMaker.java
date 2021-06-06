package com.dunzo.beveragemachine.components;
/*
Defines the work that needs to be done while making the beverage
 */
public class BeverageMaker implements Runnable {

    String beverageName;
    ContentManager contentManager;

    public BeverageMaker(String name, ContentManager contentManager) {
        this.beverageName = name;
        this.contentManager = contentManager;
    }

    public String makeBeverage() {
        //get all contents
        System.out.println("Preparing " + beverageName);
        String result = contentManager.getBeverageContents(beverageName);

        //brew and prepare beverage

        try {
            Thread.sleep(2000); /// to simulate preparing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //serve beverage
        System.out.println(result);
        return result;


    }


    @Override
    public void run() {
        makeBeverage();
    }
}

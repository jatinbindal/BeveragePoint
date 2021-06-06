package com.dunzo.beveragemachine.components;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BeverageMakerWorkers {

    private final ExecutorService workers;

    public BeverageMakerWorkers(int n) {
        this.workers = Executors.newFixedThreadPool(n);
    }

    public void makeBeverage(String beverageName, ContentManager contentManager) {
        workers.execute(new BeverageMaker(beverageName, contentManager));
    }
}

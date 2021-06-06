package com.dunzo.beveragemachine.main;

import com.dunzo.beveragemachine.components.BeverageMakerWorkers;
import com.dunzo.beveragemachine.components.Machine;
import com.dunzo.beveragemachine.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class Demo {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        Machine machine = objectMapper.readValue(Utils.fileContentToString("src/main/resources/input.txt"), Machine.class);
        BeverageMakerWorkers workers = machine.getBeverageMakerThreadPool();
        workers.makeBeverage("hot_tea", machine.getContentManager());
        workers.makeBeverage("hot_coffee", machine.getContentManager());
        workers.makeBeverage("black_tea", machine.getContentManager());
        workers.makeBeverage("green_tea", machine.getContentManager());
        machine.getContentManager().addContent("green_mixture", 100);
        machine.getContentManager().addContent("sugar_syrup", 100);
        workers.makeBeverage("green_tea", machine.getContentManager());
    }

}

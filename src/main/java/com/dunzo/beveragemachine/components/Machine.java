package com.dunzo.beveragemachine.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName(value = "machine")
public class Machine {
    @JsonProperty("outlets")
    private final int numberOfOutlets;
    private final ContentManager contentManager = new ContentManager();
    private final BeverageMakerWorkers beverageMakerThreadPool;

    public Machine(@JsonProperty("outlets") Map<String, Integer> outlet,
                   @JsonProperty("total_items_quantity") Map<String, Integer> items,
                   @JsonProperty("beverages") Map<String, Map<String, Integer>> beverages) {
        this.numberOfOutlets = outlet.get("count_n");
        this.contentManager.addBeverages(beverages);
        this.contentManager.addContents(items);
        this.beverageMakerThreadPool = new BeverageMakerWorkers(numberOfOutlets);
    }

    public int getNumberOfOutlets() {
        return numberOfOutlets;
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    public BeverageMakerWorkers getBeverageMakerThreadPool() {
        return beverageMakerThreadPool;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "numberOfOutlets=" + numberOfOutlets +
                ", contentManager=" + contentManager +
                '}';
    }
}

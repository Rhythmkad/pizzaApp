package com.rhythm.mypizzaapp.getterAndSetterClasses;

import java.io.Serializable;

public class OrderHistoryBeanClass implements Serializable {
    private String pizzaName, pizzaDetails, pizzaSubTotal, pizzaCustomizeItems;
    boolean isOrderStatusCompleted;
    private int pizzaIv, pizzaLabelTagIv;

    public OrderHistoryBeanClass(String mPizzaName, String mPizzaDetails, int mPizzaIv, int mPizzaLabelTagIv, String subTotal, String customizeItems, boolean orderStatus) {
        this.pizzaName = mPizzaName;
        this.pizzaDetails = mPizzaDetails;
        this.pizzaIv = mPizzaIv;
        this.pizzaLabelTagIv = mPizzaLabelTagIv;
        pizzaSubTotal = subTotal;
        pizzaCustomizeItems = customizeItems;
        isOrderStatusCompleted = orderStatus;
    }

    public String getPizzaPriceTotal() {
        return pizzaSubTotal;
    }

    public void setPizzaSubTotal(String pizzaSubTotal) {
        this.pizzaSubTotal = pizzaSubTotal;
    }

    public String getPizzaCustomizeItems() {
        return pizzaCustomizeItems;
    }

    public void setPizzaCustomizeItems(String pizzaCustomizeItems) {
        this.pizzaCustomizeItems = pizzaCustomizeItems;
    }

    public boolean getPizzaOrderStatus() {
        return isOrderStatusCompleted;
    }

    public void setPizzaOrderStatus(boolean pizzaOrderStatus) {
        this.isOrderStatusCompleted = pizzaOrderStatus;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getPizzaDetails() {
        return pizzaDetails;
    }

    public void setPizzaDetails(String pizzaDetails) {
        this.pizzaDetails = pizzaDetails;
    }

    public int getPizzaIv() {
        return pizzaIv;
    }

    public void setPizzaIv(int pizzaIv) {
        this.pizzaIv = pizzaIv;
    }

    public int getPizzaLabelTagIv() {
        return pizzaLabelTagIv;
    }

    public void setPizzaLabelTagIv(int pizzaLabelTagIv) {
        this.pizzaLabelTagIv = pizzaLabelTagIv;
    }
}

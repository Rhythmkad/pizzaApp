package com.rhythm.mypizzaapp.getterAndSetterClasses;

import java.io.Serializable;

public class PizzaMenuBeanClass implements Serializable{
    private String pizzaName, pizzaDetails;
    private  int pizzaIv, pizzaLabelTagIv;

    public PizzaMenuBeanClass(String mPizzaName, String mPizzaDetails, int mPizzaIv, int mPizzaLabelTagIv) {
        this.pizzaName = mPizzaName;
        this.pizzaDetails = mPizzaDetails;
        this.pizzaIv = mPizzaIv;
        this.pizzaLabelTagIv = mPizzaLabelTagIv;
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

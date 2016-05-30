package es.upm.dit.gsi.jsanchez.stocksentimentglass.model;

import java.io.Serializable;

/**
 * Created by jsanchez on 18/02/16.
 */
public class EnterpriseModel implements Serializable {
    private String name;
    private String sentimentIndex, stockValue;
    private String image;


    public EnterpriseModel(String name, String sentimentIndex, String stockValue, String image) {
        this.name = name;
        this.sentimentIndex = sentimentIndex;
        this.stockValue = stockValue;
        this.image = image;
    }


}
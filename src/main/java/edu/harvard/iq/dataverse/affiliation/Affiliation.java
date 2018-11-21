/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.iq.dataverse.affiliation;

/**
 *
 * @author manikons
 */
public class Affiliation {
    private String id;
    private String name;
    private String abbreviatedName;
    private String commaDelimitedIp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    public String getCommaDelimitedIp() {
        return commaDelimitedIp;
    }

    public void setCommaDelimitedIp(String commaDelimitedIp) {
        this.commaDelimitedIp = commaDelimitedIp;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

/**
 *
 * @author kerbrase
 */
public class Template {

    private Stroke stroke;
    private String name;
    
    public Template(Stroke stroke, String name){
        this.stroke = stroke;
        this.name = name;
    }

    /**
     * @return the stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}

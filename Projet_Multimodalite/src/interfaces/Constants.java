/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author kerbrase
 */
public interface Constants {

    public enum Shape {

        RECTANGLE("Rectangle"),
        ELLIPSE("Ellipse"),
        OBJET("Objet");
        private final String name;

        private Shape(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    public enum Gesture {

    }
}

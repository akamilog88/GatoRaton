/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_raton;

import java.util.List;

/**
 *
 * @author hriverol
 */
public abstract class Ficha {

    protected int x;
    protected int y;
    protected String nombre;
    protected boolean isSelect;

    public Ficha(int x, int y, boolean selected) {
        this.x = x;
        this.y = y;
       isSelect = selected;
    }

    //Metodo que comprueba si la pieza puede jugar)
    public abstract boolean Puedejugar(Ficha[][] p, int x, int y, int x1, int y1);

    public abstract List<Coordenada> PosiblesJugadas(Ficha[][] p);

    public boolean isIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

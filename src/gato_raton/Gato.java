/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_raton;

/**
 *
 * @author hriverol
 */
import java.util.LinkedList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Gato extends Ficha {

    private Icon icon;

    public Gato(int x, int y, String nombre) {
        super(x, y, false);

    }

    public Icon getIcon() {
        if (isSelect) {
            icon = new ImageIcon("src/imagenes/garfield.jpg");
            isSelect = false;
        } else {
            icon = new ImageIcon("src/imagenes/garfield.png");
        }

        return icon;
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    public boolean Puedejugar(Ficha[][] p, int x, int y, int destx1, int desty1) {
        boolean flag = false;
        if (destx1 < 0 || destx1 > 7 || desty1 < 0 || desty1 > 7) {
            return false;
        }
        if (p[destx1][desty1] == null && (x + 1 == destx1 && y + 1 == desty1)) {
            flag = true;
        }
        if (p[destx1][desty1] == null && (x - 1 == destx1 && y + 1 == desty1)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Coordenada> PosiblesJugadas(Ficha[][] p) {
        List<Coordenada> lc = new LinkedList<Coordenada>();

        if (Puedejugar(p, x, y, x - 1, y + 1)) {
            lc.add(new Coordenada(x - 1, y + 1));
        }
        if (Puedejugar(p, x, y, x + 1, y + 1)) {
            lc.add(new Coordenada(x + 1, y + 1));
        }
        return lc;
    }
}

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

public class Raton extends Ficha {

    private Icon icon;

    public Raton(int x, int y, String nombre) {
        super(x, y, false);
        icon = new ImageIcon("src/imagenes/raton.png");
    }

    @Override
    public int getX() {
        return super.getX();
    }

    public Icon getIcon() {
        if (isSelect) {
            icon = new ImageIcon("src/imagenes/raton.jpg");
            isSelect = false;
        } else {
            icon = new ImageIcon("src/imagenes/raton.png");
        }
        return icon;
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
    public boolean Puedejugar(Ficha[][] p, int x, int y, int x1, int y1) {
        if (x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7) {
            return false;
        }
        if ((x + 1 < 8 && y + 1 < 8) && (p[x + 1][y + 1] == null) && x1 == x + 1 && y1 == y + 1) {
            return true;
        }
        if ((x + 1 < 8 && y - 1 > -1) && (p[x + 1][y - 1] == null) && x1 == x + 1 && y1 == y - 1) {
            return true;
        }


        if ((x - 1 > -1 && y - 1 > -1) && (p[x - 1][y - 1] == null) && x1 == x - 1 && y1 == y - 1) {
            return true;
        }
        if ((x - 1 > -1 && y + 1 < 8) && (p[x - 1][y + 1] == null) && x1 == x - 1 && y1 == y + 1) {
            return true;
        }

        return false;
    }

    @Override
    public List<Coordenada> PosiblesJugadas(Ficha[][] p) {
        List<Coordenada> lc = new LinkedList<Coordenada>();
        if (Puedejugar(p, x, y, x - 1, y - 1)) {
            lc.add(new Coordenada(x - 1, y - 1));
        }
        if (Puedejugar(p, x, y, x + 1, y - 1)) {
            lc.add(new Coordenada(x + 1, y - 1));
        }
        if (Puedejugar(p, x, y, x + 1, y + 1)) {
            lc.add(new Coordenada(x + 1, y + 1));
        }

        if (Puedejugar(p, x, y, x - 1, y + 1)) {
            lc.add(new Coordenada(x - 1, y + 1));
        }
        return lc;
    }

    public int TotalJugadas(Ficha[][] p) {
        int cantTotal = 0;

        if (PosiblesCasillas(p, x, y, x + 1, y + 1)) {
            cantTotal++;
        }
        if (PosiblesCasillas(p, x, y, x - 1, y - 1)) {
            cantTotal++;
        }
        if (PosiblesCasillas(p, x, y, x + 1, y - 1)) {
            cantTotal++;
        }
        if (PosiblesCasillas(p, x, y, x - 1, y + 1)) {
            cantTotal++;
        }
        return cantTotal;
    }

    public boolean PosiblesCasillas(Ficha[][] p, int x, int y, int x1, int y1) {
        if (x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7) {
            return false;
        }
        if ((x + 1 < 8 && y + 1 < 8) && x1 == x + 1 && y1 == y + 1) {
            return true;
        }
        if ((x + 1 < 8 && y - 1 > -1) && x1 == x + 1 && y1 == y - 1) {
            return true;
        }


        if ((x - 1 > -1 && y - 1 > -1) && (p[x - 1][y - 1] == null) && x1 == x - 1 && y1 == y - 1) {
            return true;
        }
        if ((x - 1 > -1 && y + 1 < 8) && (p[x - 1][y + 1] == null) && x1 == x - 1 && y1 == y + 1) {
            return true;
        }

        return false;
    }

    public boolean pierdeRaton(Ficha[][] p) {
        return (PosiblesJugadas(p)).isEmpty();
    }

    public boolean GanaRaton() {
        return this.y == 0;
    }
}

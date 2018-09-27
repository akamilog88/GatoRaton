/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;
//import imagenes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import gato_raton.*;
import javax.swing.JFrame;

/**
 *
 * @author harold
 */
public class Tablero extends JPanel implements MouseListener {

    private JLabel fondo;
    private boolean comenzado;
    private Icon imgf;
    private Gato_Raton controler;

    public Tablero(Gato_Raton controler) {
        this.controler = controler;
        this.setSize(480, 480);
        this.setLayout(null);
        setVisible(true);
        setOpaque(false);
        comenzado = false;
        fondo = new JLabel();
        imgf = new ImageIcon("src/imagenes/Tablero.jpg");
        //   imgf = new ImageIcon("imagenes/Tablero.jpg");
        fondo.setIcon(imgf);
        fondo.setVisible(true);

        this.add(fondo);
        fondo.setBounds(0, 0, 480, 480);
        
        paint();
        repaint();
       


    }

   
    public void paint() {

        Gato[] cat = controler.getGatos();
        Raton rat = controler.getRaton();
        removeAll();
        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel(cat[i].getIcon());

            //  System.out.println("coord gato "+i+cat[i].getX()+" ; "+cat[i].getY() );
            label.setBounds((cat[i].getX()) * 60, (cat[i].getY()) * 60, 60, 60);
            this.add(label);
        }
        JLabel label = new JLabel(rat.getIcon());

        this.add(label);
        label.setBounds((rat.getX()) * 60, (rat.getY()) * 60, 60, 60);

        add(fondo);


    }
       public void CrearPartida() {
        Object[] modo = {"Raton", "Gato"};
        Object select = JOptionPane.showInputDialog(this, "Seleccione con quien quiere jugar", null, WIDTH, null, modo, true);
        this.getRootPane().setRequestFocusEnabled(true);
        if (select.equals("Raton")) {
            controler.setModoRata(false);
            controler.generarTablero();
        } else {
            controler.setModoRata(true);
            controler.generarTablero();
           
                controler.GenerarArbolDeJugadas();
            
        }
        paint();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (controler.isModoRata()) {
            controler.consumirTurnoRata(me.getX(), me.getY());
        } else {
            controler.consumirTurnoGato(me.getX(), me.getY());
        }
        Raton rat = controler.getRaton();
        //     Gato[] g = controler.getGatos();
//        for (int i = 0; i < g.length; i++) {
//            System.out.println("coord " + i + " " + g[i].getX() + "; " + g[i].getY());
//
//        }
        if (controler.getSelFicha() == null) {
            // controler.GenerarArbolDeJugadas();
        }
        //      g = controler.getGatos();
//        for (int i = 0; i < g.length; i++) {
//            System.out.println("coord " + i + " " + g[i].getX() + "; " + g[i].getY());
//
//        }

        paint();
        repaint();

        if (rat.GanaRaton()) {
            JOptionPane.showMessageDialog(this, "Gana el Raton");
            CrearPartida();
           

            paint();
            repaint();
        }
        if (rat.pierdeRaton(controler.getTablero())) {
            JOptionPane.showMessageDialog(this, "Gana el Gato");
            CrearPartida();
            
            paint();
            repaint();
        }
//        System.out.println("*****************************************");
//        System.out.println("*****************************************");
//        System.out.println("*****************************************");

    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }
}

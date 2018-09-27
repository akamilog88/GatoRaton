/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_raton;

/**
 *
 * @author harold
 */
public class Gato_Raton {

    private Ficha tablero[][];
    private boolean turnoRata;
    private Ficha selFicha;
    private Arbol arbolJugadas;
    private boolean modoRata;

    public Gato_Raton(Ficha[][] tablero, boolean turnoRata, Ficha selFicha) {
        this.tablero = tablero;
        this.turnoRata = true;
        this.selFicha = selFicha;
        
    }

    public Gato_Raton() {
        modoRata = true;
        this.turnoRata = false;
        generarTablero();
    }

    public boolean isModoRata() {
        return modoRata;
    }

    public void setModoRata(boolean modoRata) {
        this.modoRata = modoRata;
    }

    public void generarTablero() {

        tablero = new Ficha[8][8];
        turnoRata = true;
        selFicha = null;
        for (int i = 1; i < 8; i += 2) {
            tablero[i][0] = new Gato(i, 0, null);
        }
        tablero[4][7] = new Raton(4, 7, null);
    }

    public Gato[] getGatos() {
        int cont = 0;
        Gato cat[] = new Gato[4];
        Gato gatotemp = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] != null && tablero[i][j] instanceof Gato) {
                    if (cont == 4) {
                        return cat;
                    }
                    gatotemp = (Gato) tablero[i][j];
                    gatotemp.setX(i);
                    gatotemp.setY(j);
                    cat[cont++] = gatotemp;
                }
            }
        }
        return cat;
    }

    public Raton getRaton() {
        Raton ratatemp = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] != null && tablero[i][j] instanceof Raton) {
                    ratatemp = (Raton) tablero[i][j];
                    ratatemp.setX(i);
                    ratatemp.setY(j);
                    return ratatemp;
                }
            }
        }
        return null;
    }

    public boolean consumirTurnoGato(int x, int y) {
        x = x / 60;
        y = y / 60;
        Ficha f = tablero[x][y];

        if (f != null) {
            if (f instanceof Raton && turnoRata) {
                selFicha = f;
                f.setIsSelect(true);
            } else if (f instanceof Gato && !turnoRata) {
                selFicha = f;
                f.setIsSelect(true);
            } else {
                return false;
            }
        } else {
            if (turnoRata && selFicha instanceof Raton
                    && selFicha.Puedejugar(tablero, selFicha.getX(), selFicha.getY(), x, y)) {
                Cambiar(selFicha.getX(), selFicha.getY(), x, y);

                turnoRata = !turnoRata;
                GenerarArbolDeJugadas();
                selFicha = null;
            }
//            else if (!turnoRata && selFicha instanceof Gato
//                    && selFicha.Puedejugar(tablero, selFicha.getX(), selFicha.getY(), x, y)) {
//                Cambiar(selFicha.getX(), selFicha.getY(), x, y);
//                selFicha = null;
//                turnoRata = !turnoRata;
//            }
            return true;
        }
        return false;
    }

    public boolean consumirTurnoRata(int x, int y) {
        x = x / 60;
        y = y / 60;
        Ficha f = tablero[x][y];

        if (f != null) {
           if (f instanceof Gato && turnoRata == false) {
                selFicha = f;
                f.setIsSelect(true);
            } else {
                return false;
            }
        } else {
//            if (turnoRata && selFicha instanceof Raton
//                    && selFicha.Puedejugar(tablero, selFicha.getX(), selFicha.getY(), x, y)) {
//                Cambiar(selFicha.getX(), selFicha.getY(), x, y);
//
//                turnoRata = !turnoRata;
//               
//            }
            if (!turnoRata && selFicha instanceof Gato
                    && selFicha.Puedejugar(tablero, selFicha.getX(), selFicha.getY(), x, y)) {
                Cambiar(selFicha.getX(), selFicha.getY(), x, y);
                selFicha = null;
                turnoRata = !turnoRata;
                GenerarArbolDeJugadas();
                selFicha = null;
            }
            return true;
        }
        return false;
    }

    public void Cambiar(int x, int y, int destx, int desty) {
//        tablero[x][y].setX(destx);
//        tablero[x][y].setY(desty);
        tablero[destx][desty] = tablero[x][y];
        tablero[x][y] = null;
    }

    public void ConstruirMatrizFichas(Ficha[][] origen, Ficha[][] destino) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                destino[i][j] = origen[i][j];
            }
        }



    }

    public void GenerarArbolDeJugadas() {
        Ficha[][] nuevoTablero = new Ficha[8][8];
        ConstruirMatrizFichas(tablero, nuevoTablero);
        Raton rata = getRaton();
        if (rata.GanaRaton() || rata.pierdeRaton(tablero)) {
            return;
        }
        arbolJugadas = new Arbol(nuevoTablero, 0);
// for (int i = 0; i < g.length; i++) {
//            System.out.println("coord " + i + " " + g[i].getX() + "; " + g[i].getY());
//
//        }
        if (modoRata) {
            arbolJugadas.ConstruirRaton();
            turnoRata = false;
        } else {
            arbolJugadas.ConstruirGato();
             turnoRata = true;
        }
//        imprimirMatriz(tablero, " tablero original");
        ConstruirMatrizFichas(arbolJugadas.getMejorhijo(), tablero);
//        imprimirMatriz(tablero, " mejor jugada");

       
//        for (int i = 0; i < g.length; i++) {
//            System.out.println("coord " + i + " " + g[i].getX() + "; " + g[i].getY());
//
//        }
    }

    public void imprimirMatriz(Ficha[][] fichas, String a) {
        System.out.println(a);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fichas[j][i] != null) {
                    if (fichas[j][i] instanceof Gato) {

                        System.out.print("1 ");
                    } else {
                        System.out.print("2 ");
                    }
                } else {
                    System.out.print("0 ");
                }

            }
            System.out.println();
        }
    }

    public Ficha getSelFicha() {
        return selFicha;
    }

    public void setSelFicha(Ficha selFicha) {
        this.selFicha = selFicha;
    }

    public Ficha[][] getTablero() {
        return tablero;
    }

    public void setTablero(Ficha[][] tablero) {
        this.tablero = tablero;
    }

    public boolean isTurnoRata() {
        return turnoRata;
    }

    public void setTurnoRata(boolean turnoRata) {
        this.turnoRata = turnoRata;
    }
}

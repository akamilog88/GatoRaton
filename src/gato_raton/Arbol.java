/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gato_raton;

/**
 *
 * @author harold
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Arbol {

//    private int[][] raiz;
    private Ficha[][] raiz;
    private int nivel;
    private List<Arbol> hijos;
    private Ficha[][] mejorhijo;
    private int total;
    private Random randObj;
//    private Gato[] gatos;

    public Arbol(Ficha[][] tab, int niv) {
        this.hijos = new LinkedList<Arbol>();
        this.raiz = tab;
        this.nivel = niv;
        randObj = new Random();
    }

    public List<Arbol> getHijos() {
        return hijos;
    }

    public void setHijos(List<Arbol> hijos) {
        this.hijos = hijos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Ficha[][] getRaiz() {
        return raiz;
    }

    public void setRaiz(Ficha[][] raiz) {
        this.raiz = raiz;
    }

// public Arbol(Gato[] tab, int niv) {
//        this.hijos = new LinkedList<Arbol>();
//        gatos = tab;
//        this.nivel = niv;
//    }
    public LinkedList<Gato> buscarGatos() {
        LinkedList<Gato> gatos2 = new LinkedList<Gato>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (raiz[i][j] instanceof Gato) {
                    Gato tmp = (Gato) raiz[i][j];
                    tmp.setX(i);
                    tmp.setY(j);
                    gatos2.add(tmp);
                }
            }
        }
        return gatos2;

    }

    public Raton buscarRaton() {
        Raton tmp = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (raiz[i][j] instanceof Raton) {
                    tmp = (Raton) raiz[i][j];
                    tmp.setX(i);
                    tmp.setY(j);
                }
            }
        }
        return tmp;

    }

    public void ConstruirMatrizFichas(Ficha[][] fichas, Ficha[][] destino, Coordenada coord, Ficha fichavieja) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                destino[i][j] = fichas[i][j];
            }
        }
        destino[coord.getX()][coord.getY()] = fichavieja;
        destino[fichavieja.getX()][fichavieja.getY()] = null;


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

    public float ConstruirGato() {


        if (nivel < 8) {

            if (nivel % 2 == 0) {
                LinkedList<Gato> gatos2 = new LinkedList<Gato>();
                gatos2 = buscarGatos();

                for (int i = 0; i < 4; i++) {
                    List<Coordenada> c = gatos2.get(i).PosiblesJugadas(raiz);
                    for (int j = 0; j < c.size(); j++) {
                        Ficha[][] nuevaRaiz = new Ficha[8][8];
//                        imprimirMatriz(raiz, "raiz: ");
                        ConstruirMatrizFichas(raiz, nuevaRaiz, c.get(j), gatos2.get(i));

                        Arbol nuevoArbol = new Arbol(nuevaRaiz, nivel + 1);
//                        imprimirMatriz(nuevoArbol.getRaiz(), "nueva raiz: ");
                        hijos.add(nuevoArbol);
                    }
                }
                if (hijos.isEmpty()) {
                    return Eval();
                }
                //   System.out.println("canthijos nivel " + nivel + " " + hijos.size());
            } else {
                Raton raton = buscarRaton();


                List<Coordenada> c = raton.PosiblesJugadas(raiz);
                // System.out.println("jug "+c.size()+" nivel "+nivel);

                for (int j = 0; j < c.size(); j++) {
                    Ficha[][] nuevaRaiz = new Ficha[8][8];
                    ConstruirMatrizFichas(raiz, nuevaRaiz, c.get(j), raton);
                    Arbol nuevoArbol = new Arbol(nuevaRaiz, nivel + 1);
                    hijos.add(nuevoArbol);
                }
                if (hijos.isEmpty()) {
                    return Eval();
                }

//                System.out.println("canthijos nivel " + nivel + " " + hijos.size());
            }

            if (!hijos.isEmpty()) {
                float minmax[] = new float[hijos.size()];


                for (int i = 0; i < hijos.size(); i++) {
                    float heuristic = hijos.get(i).ConstruirGato();
                    minmax[i] = heuristic;
                }
//                System.out.println("nivel" + nivel);
//                for (int i = 0; i < minmax.length; i++) {
//                    System.out.print(minmax[i] + " ");
//                }
//                System.out.println("");
                int posSol = 0;
                if (nivel % 2 == 0) {
                    posSol = Max(minmax);
                } else {
                    posSol = Min(minmax);
                }
                mejorhijo = hijos.get(posSol).getRaiz();

                return minmax[posSol];
            }
            return 0;

        } else {
            return Eval();

        }
    }

    public float ConstruirRaton() {


        if (nivel < 8) {

            if (nivel % 2 == 1) {
                LinkedList<Gato> gatos2 = new LinkedList<Gato>();
                gatos2 = buscarGatos();

                for (int i = 0; i < 4; i++) {
                    List<Coordenada> c = gatos2.get(i).PosiblesJugadas(raiz);
                    for (int j = 0; j < c.size(); j++) {
                        Ficha[][] nuevaRaiz = new Ficha[8][8];
//                        imprimirMatriz(raiz, "raiz: ");
                        ConstruirMatrizFichas(raiz, nuevaRaiz, c.get(j), gatos2.get(i));

                        Arbol nuevoArbol = new Arbol(nuevaRaiz, nivel + 1);
//                        imprimirMatriz(nuevoArbol.getRaiz(), "nueva raiz: ");
                        hijos.add(nuevoArbol);
                    }
                }
                if (hijos.isEmpty()) {
                    return EvalRata();
                }
                //   System.out.println("canthijos nivel " + nivel + " " + hijos.size());
            } else {
                Raton raton = buscarRaton();


                List<Coordenada> c = raton.PosiblesJugadas(raiz);
                // System.out.println("jug "+c.size()+" nivel "+nivel);

                for (int j = 0; j < c.size(); j++) {
                    Ficha[][] nuevaRaiz = new Ficha[8][8];
                    ConstruirMatrizFichas(raiz, nuevaRaiz, c.get(j), raton);
                    Arbol nuevoArbol = new Arbol(nuevaRaiz, nivel + 1);
                    hijos.add(nuevoArbol);
                }
                if (hijos.isEmpty()) {
                    return EvalRata();
                }

//                System.out.println("canthijos nivel " + nivel + " " + hijos.size());
            }

            if (!hijos.isEmpty()) {
                float minmax[] = new float[hijos.size()];


                for (int i = 0; i < hijos.size(); i++) {
                    float heuristic = hijos.get(i).ConstruirRaton();
                    minmax[i] = heuristic;
                }
//                System.out.println("nivel" + nivel);
//                for (int i = 0; i < minmax.length; i++) {
//                    System.out.print(minmax[i] + " ");
//                }
//                System.out.println("");
                int posSol = 0;
                if (nivel % 2 == 1) {
                    posSol = Max(minmax);
                } else {
                    posSol = Min(minmax);
                }
                mejorhijo = hijos.get(posSol).getRaiz();
                if (nivel == 0) {
//                    System.out.println("mejor heuristica  " + minmax[posSol]);
                }

                return minmax[posSol];
            }
            return 0;

        } else {
            return EvalRata();

        }
    }

    public Ficha[][] getMejorhijo() {
        return mejorhijo;
    }

    public int Max(float[] heuristic) {
        float max = 0;
        int pos = 0;
        for (int i = 0; i < heuristic.length; i++) {
            if (heuristic[i] > max) {
                max = heuristic[i];
                pos = i;
            }

        }
        if (nivel == 0) {
            // System.out.println("heuristica final " + max);
        }
        //System.out.println("max " + max + " ");

        return pos;
    }

    public int Min(float[] heuristic) {
        if (heuristic != null) {
            float min = heuristic[0];
            int pos = 0;
            for (int i = 0; i < heuristic.length; i++) {
                if (heuristic[i] < min) {
                    min = heuristic[i];
                    pos = i;
                }

            }
//            System.out.println("min " + min + " ");
            return pos;
        }
        return 0;
    }

    public float Eval() {
        float heuristica = 0;

        // heuristica = FactorEscape() + FactorEncarcelamiento() + FactorAlineamiento();
        // imprimirMatriz(raiz, "arbol nivel "+nivel );
        heuristica += FactorCantJugadas();
        heuristica *= FactorDispersion();
        heuristica += FactorEncarcelamiento();
        heuristica *= FactorEscape();
        heuristica += FactorAlineamiento();
        //  heuristica += (Math.random() / 2);
        // heuristica += randObj.nextInt(10 - 0 + 1) + 0;
        //System.out.println(" la heuristica es " +  heuristica);

        return heuristica;
    }

    public float EvalRata() {
        float heuristica = 0;
        heuristica += FactorEscape();
        heuristica += FactorEncarcelamiento() * 2;
        heuristica += FactorCantJugadas();
        heuristica += randObj.nextInt(10 - 0 + 1) + 0;
        //imprimirMatriz(raiz, "heuristica "+heuristica+"   nivel "+nivel);
        return heuristica;
    }

    public double FactorEncarcelamiento() {

        Raton rata = buscarRaton();

        if (rata.pierdeRaton(raiz)) {
            // System.out.println("factor encarcelamiento: " + 100);
            return 100;
        }

        int cantReal = rata.PosiblesJugadas(raiz).size();
        int cantTotal = rata.TotalJugadas(raiz);
        // System.out.println("factor encarcelamiento: " + cantReal);
        return  cantReal;
    }

    public double FactorDispersion() {

        double suma = 0;
        double media = 0;
        double varianza = 0;
        double sumacuadrados = 0;
        List<Gato> misgatos = buscarGatos();

        for (int i = 0; i < 4; i++) {

            suma += misgatos.get(i).getY();
//            System.out.print(" y:" + misgatos.get(i).getY());
        }
//        System.out.println(" suma: " + suma);
        media = suma / 4;
//        System.out.println(" promedio: " + media);
        for (int i = 0; i < 4; i++) {
            sumacuadrados += misgatos.get(i).getY() * misgatos.get(i).getY();




        }
        varianza = sumacuadrados / 4 - media * media;
//        System.out.println("factor dispersion: " + varianza);
        //   System.out.println("suma: " + suma);


//        if (dispersion > 0.5) {
//            return 1;
//        } else {
//            return 0;
//        }
        if (1 / varianza > 30) {
            return 30;
        }
        return 1 / varianza;
    }

    public int FactorEscape() {

        Raton rata = buscarRaton();
        List<Gato> misgatos = buscarGatos();
        int cantGatosAbajo = 0;
        if (rata.getY() == 0) {
            //  imprimirMatriz(raiz, "se escapó ");
            //   System.out.println("se escapo la rata");
            // System.out.println("Factor escape: " + (-50));
            return -100;
        }
        for (int i = 0; i < misgatos.size(); i++) {
            if (rata.getY() < misgatos.get(i).getY()) {
                cantGatosAbajo++;
            }
            if (cantGatosAbajo == 2) {
                //      imprimirMatriz(raiz, "se escapó ");

                //       System.out.println("se escapo la rata");
                //   System.out.println("Factor escape: " + (-50));
                return -100;
            }
        }

        return 1;
    }

    public int FactorCantJugadas() {
        int cantJG = 0;
        int cantJR = 0;
        Raton rata = buscarRaton();
        List<Gato> misgatos = buscarGatos();
        for (int i = 0; i < misgatos.size(); i++) {
            cantJG += misgatos.get(i).PosiblesJugadas(raiz).size();
        }
        //  System.out.println(" FactorCantJugadas: " + (cantJG - cantJR));

        return (cantJG - cantJR);
    }

    public double FactorAlineamiento() {
        Gato gatoActI;
        Gato gatoActJ;
        int cont = 0;
        List<Gato> misgatos = buscarGatos();

        for (int i = 0; i < misgatos.size(); i++) {
            gatoActI = misgatos.get(i);
            for (int j = 0; j < misgatos.size(); j++) {
                gatoActJ = misgatos.get(j);
                if (i != j) {
                    if (gatoActI.getX() + 1 == gatoActJ.getX() && gatoActI.getY() + 1 == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() - 1 == gatoActJ.getX() && gatoActI.getY() + 1 == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() - 1 == gatoActJ.getX() && gatoActI.getY() - 1 == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() + 1 == gatoActJ.getX() && gatoActI.getY() - 1 == gatoActJ.getY()) {
                        cont++;
                    }
                    //////************************///////////////

                    if (gatoActI.getX() + 2 == gatoActJ.getX() && gatoActI.getY() == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() - 2 == gatoActJ.getX() && gatoActI.getY() == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() == gatoActJ.getX() && gatoActI.getY() + 2 == gatoActJ.getY()) {
                        cont++;
                    }
                    if (gatoActI.getX() == gatoActJ.getX() && gatoActI.getY() - 2 == gatoActJ.getY()) {
                        cont++;
                    }


                }

            }

        }
        //   System.out.println(" FactorAlineamiento " + cont);

        return cont;
    }

    public LinkedList<Coordenada> BuscarJugadas(Gato gato) {
        LinkedList<Coordenada> coordenadas = new LinkedList<Coordenada>();
        int posx = gato.getX();
        int posy = gato.getY();
        //  Ficha fichas[][] = .getBoton();

        if ((posx + 1 < 8 && posy + 1 < 8) && raiz[posx + 1][posy + 1] == null) {
            coordenadas.add(new Coordenada(posx + 1, posy + 1));
        }
        if ((posx - 1 > 0 && posy + 1 < 8) && raiz[posx - 1][posy + 1] == null) {
            coordenadas.add(new Coordenada(posx - 1, posy + 1));
        }

        return coordenadas;

    }

    public LinkedList<Coordenada> BuscarJugadas(Raton raton) {
        LinkedList<Coordenada> coordenadas = new LinkedList<Coordenada>();
        int posx = raton.getX();
        int posy = raton.getY();
        //  Ficha fichas[][] = .getBoton();

        if ((posx + 1 < 8 && posy - 1 > 0) && raiz[posx + 1][posy - 1] == null) {
            coordenadas.add(new Coordenada(posx + 1, posy - 1));
        }
        if ((posx - 1 > 0 && posy - 1 > 0) && raiz[posx - 1][posy - 1] == null) {
            coordenadas.add(new Coordenada(posx - 1, posy - 1));
        }
        if ((posx + 1 < 8 && posy + 1 < 8) && raiz[posx + 1][posy + 1] == null) {
            coordenadas.add(new Coordenada(posx + 1, posy + 1));
        }
        if ((posx - 1 > 0 && posy + 1 < 8) && raiz[posx - 1][posy + 1] == null) {
            coordenadas.add(new Coordenada(posx - 1, posy + 1));
        }

        return coordenadas;

    }
}

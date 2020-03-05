/*
Dette er et program som oppretter klassen "TestLegemiddel" med main-metode som gjør enhetstester av instanser
av klassene "Narkotisk", "Vanedannende" og "Vanlig".
Klassen tester alle egenskapene til hver instans. 
*/

class TestLegemiddel {
  public static void main(String[] args) {
    Narkotisk narko = new Narkotisk("Morfin", 255.90, 15, 5);
    Vanedannende vanedan = new Vanedannende("Zopiklon", 185, 10, 2);
    Vanlig van = new Vanlig("Paracet", 49.90, 510);

    System.out.println(narko + "\n");
    System.out.println("Forventet id: " + 0);
    System.out.println(narko.hentId());
    System.out.println("Forventet navn: Morfin");
    System.out.println(narko.hentNavn());
    System.out.println("Forventet pris: " + 255.90);
    System.out.println(narko.hentPris());
    System.out.println("Forventet virkestoff: " + 15);
    System.out.println(narko.hentVirkestoff());
    narko.settNyPris(280);
    System.out.println("Forventet pris etter ny pris: " + 280);
    System.out.println(narko.hentPris());
    System.out.println("Forventet styrke: " + 10);
    System.out.println(narko.hentNarkotiskStyrke()+"\n");

    System.out.println(vanedan + "\n");
    System.out.println("Forventet id: " + 1);
    System.out.println(vanedan.hentId());
    System.out.println("Forventet navn: Zopiklon");
    System.out.println(vanedan.hentNavn());
    System.out.println("Forventet pris: " + 185);
    System.out.println(vanedan.hentPris());
    System.out.println("Forventet virkestoff: " + 10);
    System.out.println(vanedan.hentVirkestoff());
    vanedan.settNyPris(200);
    System.out.println("Forventet pris etter ny pris: " + 200);
    System.out.println(vanedan.hentPris());
    System.out.println("Forventet styrke: " + 5);
    System.out.println(vanedan.hentVanedannedeStyrke()+"\n");

    System.out.println(van);
    System.out.println("Forventet id: " + 2);
    System.out.println(van.hentId());
    System.out.println("Forventet navn: Paracet");
    System.out.println(van.hentNavn());
    System.out.println("Forventet pris: " + 49.90);
    System.out.println(van.hentPris());
    System.out.println("Forventet virkestoff: " + 510);
    System.out.println(van.hentVirkestoff());
    van.settNyPris(100);
    System.out.println("Forventet pris etter ny pris: " + 100);
    System.out.println(van.hentPris() + "\n");

  }
}

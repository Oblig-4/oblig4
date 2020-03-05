/*
Dette er et program som oppretter klassen "TestResepter" med main-metode som gjør enhetstester av instanser
av klassene "HvitResept", "BlaaResept", "MilitaerResept" og "PResept".
Klassen tester kun egenskapene som er implementert forskjellig fra reseptens foreldreklasse.
*/

class TestResepter {
  public static void main(String[] args) {
    Narkotisk narko = new Narkotisk("Morfin", 255.90, 15, 10);
    Vanedannende vanedan = new Vanedannende("Zopiklon", 185, 10, 5);
    Vanlig van = new Vanlig("Paracet", 49.90, 510);
    Vanlig van2 = new Vanlig("Ibux", 120, 200);
    Lege lege = new Lege("Dr. Phil");

    HvitResept hvit = new HvitResept(narko, lege, 6, 4);
    BlaaResept blaa = new BlaaResept(vanedan, lege, 20, 2);
    MilitaerResept militaer = new MilitaerResept(van, lege, 15, 3);
    PResept presept = new PResept(van2, lege, 10);

    System.out.println(hvit + "\n");
    System.out.println("Forventet farge: hvit");
    System.out.println(hvit.farge());
    System.out.println("Forventet pris: " + 255.90);
    System.out.println(hvit.prisAaBetale() + "\n");

    System.out.println(blaa + "\n");
    System.out.println("Forventet farge: blaa");
    System.out.println(blaa.farge());
    System.out.println("Forventet pris: " + 46.25);
    System.out.println(blaa.prisAaBetale() + "\n");

    System.out.println(militaer + "\n");
    System.out.println("Forventet farge: hvit");
    System.out.println(militaer.farge());
    System.out.println("Forventet pris: " + 0);
    System.out.println(militaer.prisAaBetale() + "\n");

    System.out.println(presept + "\n");
    System.out.println("Forventet farge: hvit");
    System.out.println(presept.farge());
    System.out.println("Forventet pris: " + 12);
    System.out.println(presept.prisAaBetale());

  }
}

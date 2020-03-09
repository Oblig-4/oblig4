/*
Dette er et program som oppretter klassen "Integrasjonstest" med main-metode som gjør enhetstester av instanser
av alle klassene i systemet, altså alle legemidlene, reseptene og legene.
Klassen oppretter minst en instans av hver klasse og skriver ut informasjon om hvert objekt ved å kalle på toString() metodene.
*/

class Integrasjonstest {
  public static void main(String[] args) {
    Narkotisk narko = new Narkotisk("Morfin", 255.90, 15, 5);
    Vanedannende vanedan = new Vanedannende("Zopiklon", 185, 10, 2);
    Vanlig van = new Vanlig("Paracet", 49.90, 510);
    Vanlig van2 = new Vanlig("Ibux", 120, 200);
    
    Pasient pasient = new Pasient("Nini", "12345678911");
    Pasient pasient2 = new Pasient("T. Nguyen", "12345678911");

    Lege lege = new Lege("Dr. Phil");
    Spesialist spesialist = new Spesialist("Dr. Peters", 12345);

    HvitResept hvit = new HvitResept(narko, spesialist, pasient, 4);
    BlaaResept blaa = new BlaaResept(vanedan, lege, pasient, 2);
    MilitaerResept militaer = new MilitaerResept(van, lege, pasient2, 5);
    PResept presept = new PResept(van2, lege, pasient2);

    System.out.println(narko + "\n");
    System.out.println(vanedan + "\n");
    System.out.println(van + "\n");
    System.out.println(van2 + "\n");
    System.out.println(lege + "\n");
    System.out.println(spesialist + "\n");
    System.out.println(hvit + "\n");
    System.out.println(blaa + "\n");
    System.out.println(militaer + "\n");
    System.out.println(presept + "\n");


  }
}

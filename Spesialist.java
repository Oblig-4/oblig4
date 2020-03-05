/*
Dette er et program som lager en subklasse "Spesialist" av superklassen "Lege" for representasjon av spesialister.
Spesialister har fått godkjenningsfritak til å skrive ut resept på narkotiske legemidler.
Godkjenningsfritak implementeres som et grensesnitt (interface).
*/

class Spesialist extends Lege implements Godkjenningsfritak {
  protected int kontrollID;

  // "Spesialist" arver egenskaper fra "Lege", kaller derfor på superklassens konstruktør vha. super med parameteren "navn".
  // @param kontrollID hentes ut for å sjekke at godkjenningsfritaket ikke blir misbrukt. Variabelen "kontrollID" arves ikke fra superklassen, og initialiseres derfor direkte i denne konstruktøren.
  public Spesialist(String navn, int kontrollID) {
    super(navn);
    this.kontrollID = kontrollID;
  }

  // Implementerer hentKontrollID() metoden fra Godkjenningsfritak-grensesnittet.
  @Override
  public int hentKontrollID() {
    return kontrollID;
  }

  @Override
  public String toString() {
    return "Spesialist \n" + "Navn: " + navn + "\nKontroll ID: " + kontrollID;
  }
}

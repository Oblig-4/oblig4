/*
Dette er et program som lager en subklasse "HvitResept" av superklassen "Resept",
for representasjon av hvite resepter.
*/

class HvitResept extends Resept {

  // "HvitResept" arver egenskaper fra "Resept" (uten tilleggsegenskaper) , kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  @Override
  public String farge() {
    return "hvit";
  }

  // Returnerer prisen pasienten må betale. Prisen på legemiddelet hentes ved å kalle på hentPris() metoden på legemiddel-objektet.
  @Override
  public double prisAaBetale() {
    double pris = legemiddel.hentPris();
    return pris;
  }

  // Overskriver toString() metoden til å returnere all tilgjengelig informasjon om objektet.
  // Ved å returnere "legemiddel" objektet kalles det på toString() metoden i klassen "Legemiddel" slik at all informasjon om reseptens legemiddel bli skrevet ut.
  @Override
  public String toString() {
    return "Hvit resept\n" + "Resept id: " + id + "\nLegemiddel type: " + legemiddel + "\nLege: " + utskrivendeLege.hentNavn() +
          "Pasient info: " + "\n" + pasient + "\n" + "\nReit: " + reit + "\nFarge: " + this.farge() + "\nPris aa betale: " + this.prisAaBetale();
  }
}

/*
Dette er et program som lager en subklasse "MilitaerResept" av superklassen "HvitResept",
for representasjon av militærresepter.
*/

class MilitaerResept extends HvitResept {

  // "MilitaerResept" arver egenskaper fra "HvitResept", som igjen arver fra "Resept" (uten tilleggsegenskaper),
  // kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
    super(legemiddel, utskrivendeLege, pasient, reit);
  }

  // Returnerer prisen pasienten må betale. Militærresept gir 100% rabatt, og metoden returnerer derfor 0.
  @Override
  public double prisAaBetale() {
    double pris = 0;
    return pris;
  }

  // Overskriver toString() metoden til å returnere all tilgjengelig informasjon om objektet.
  @Override
  public String toString() {
    return "Militaerresept\n" + "Resept id: " + id + "\nLegemiddel type: " + legemiddel + "\nLege: " + utskrivendeLege.hentNavn() +
          ""Pasient info: " + "\n" + pasient + "\n" + "\nReit: " + reit + "\nFarge: " + this.farge() + "\nPris aa betale: " + this.prisAaBetale();
  }
}

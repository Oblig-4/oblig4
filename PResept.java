/*
Dette er et program som lager en subklasse "PResept" av superklassen "HvitResept",
for representasjon av P-resepter.
*/

class PResept extends HvitResept {

  // "PResept" arver egenskaper fra "HvitResept", som igjen arver fra "Resept" (uten tilleggsegenskaper),
  // kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  // Reit tas ikke inn i konstruktøren til PResept, men vil konstant være lik 3 for P-resepter. Derfor sender vi inn 3 som verdi for reit inn i superklassens konstruktør.
  public PResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId) {
    super(legemiddel, utskrivendeLege, pasientId, 3);
  }

  // Returnerer prisen pasienten må betale. Pasienten betaler 108 kroner mindre for legemiddelet med P-resept.
  // Benytter if-sjekk for å sjekke om prisen etter rabatten er mindre enn 0, dersom dette er tilfelle returneres 0, ellers returneres prisen.
  @Override
  public double prisAaBetale() {
    double pris = legemiddel.hentPris();
    double nyPris = pris - 108;
    if (nyPris < 0) {
      return 0;
    }
    return nyPris;
  }

  // Overskriver toString() metoden til å returnere all tilgjengelig informasjon om objektet.
  @Override
  public String toString() {
    return "P-resept\n" + "Resept id: " + id + "\nLegemiddel type: " + legemiddel + "\nLege: " + utskrivendeLege.hentNavn() +
          "\nPasient Id: " + pasientId + "\nReit: " + reit + "\nFarge: " + this.farge() + "\nPris aa betale: " + this.prisAaBetale();
  }

}

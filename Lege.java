/*Dette er et program som lager en klasse "Lege" for representasjon av leger.*/
//Klaasen implementerer grensesnittet "Comparable<Lege>, og metoden "compareTo".

class Lege implements Comparable<Lege> {
  protected Lenkeliste<Resept> utskrevendeResepter;
  protected String navn;

  // @param navn er navnet på legen.

  public Lege(String navn) {
    this.navn = navn;
    utskrevendeResepter = new Lenkeliste<Resept>();
  }



  public String hentNavn() {
    return navn;
  }

//Leger sorteres alfabetisk etter navn.
  public int compareTo(Lege annen){
    return navn.compareTo(annen.hentNavn());
  }

//Metoden gjør at det er mulig å hente ut en liste av resepter, som legen har skrevet.
  //Dette gjøres ved å ta opprette en lenkeliste av resepter.
  public Lenkeliste<Resept> hentUtResepter(){
    return utskrevendeResepter;
  }



  @Override
  public String toString() {
    return "Lege\n" + "Navn: " + navn;
  }
// Leger har metoden for å opprette instanser av de fire Reseptklassene (Hvit resept. p-resept, militærresept og blå resept).
  //Når et reseptobjekt opprettes, skal det legges inn i listen over legens utskrevne resepter (hentUtResepter), før de returneres.
  //Om en vanlig lege prøver å skrive ut et narkotisk legemideel, blir unntaket "UlovligUtskrift" kastet.
  public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }
    HvitResept h = new HvitResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(h);
    return h;
  }

  public MillitaerResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    MilitaerResept m = new MillitaerResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(m);
    return m;
  }

  public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    PResept p = new PResept(legemiddel, this, pasient);
    utskrevendeResepter.leggTil(p);
    return p;
  }

  public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    if (legemiddel instanceof Narkotisk){
      throw new UlovligUtskrift(this, legemiddel, pasient.hentPasientId());
    }

    BlaaResept b = new BlaaResept(legemiddel, this, pasient, reit);
    utskrevendeResepter.leggTil(b);
    return b;
  }
}

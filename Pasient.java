class Pasient{
  protected String navn;
  protected String fodselsnummer;
  protected int pasientId;
  protected static int teller = 0;
  protected Stabel<Resept> resepter;

  public Pasient(String navn, String fodselsnummer){
    this.navn = navn;
    this.fodselsnummer = fodselsnummer;
    pasientId = teller;//unikt id
    teller ++;
    resepter = new Stabel<Resept>();
  }
  public void leggTilResept(Resept resept){
    resepter.leggPaa(resept);
  }

  public int hentPasientId(){
    return pasientId;
  }

  public String hentPasientsNavn(){
    return navn;
  }

  public String hentFodselsnummer(){
    return fodselsnummer;
  }

  public Stabel<Resept> hentUtResepter(){
    return resepter;
  }

  public String toString(){
    return "Pasients navn:  " + navn + "\n" + "PasientId: " + pasientId + "\n" + "Pasientfodselsnummer: " + fodselsnummer;
  }

}

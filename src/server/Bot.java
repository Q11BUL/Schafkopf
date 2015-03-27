package server;

import ki.Hochzeit;
import ki.KI;
import ki.Spielauswahl;
import lib.Karte;
import lib.Model;
import lib.Model.modus;

public class Bot implements Spieler {
	
	private int ID;
	private String name;
	private Model model;
	//wird nur benutzt, wenn eine Hochzeit durchgeführt wird
	private Karte karte;
	
	private int spielt;
	private int mitspieler;
	
	private int wartezeit;
	
	private KI ki;
	private Spielauswahl spielauswahl;
	private Server server;
	
	public Bot(Server server, int botnr, int wartezeit) {
		name = "[BOT]-" + botnr;
		
		spielauswahl = new Spielauswahl();
		this.server = server;
		
		spielt = -1;
		mitspieler = -1;
		
		this.wartezeit = wartezeit;
	}

	public boolean erste3(Model model) {
		setzeModel(model);
		return spielauswahl.klopfen(model);
	} 

	public synchronized void spielen(Model model) {
		setzeModel(model);
	}
	
	public synchronized modus spielstDu(Model model, modus m) {
		setzeModel(model);
		modus modus = spielauswahl.wasSpielen(model);
		if(modus.equals(modus.HOCHZEIT)) {
			karte = Hochzeit.hochzeitVorschlagen(model, ID);
		}
		return modus;
	}

	public synchronized boolean modus(lib.Model.modus m) {
		ki = spielauswahl.gibKI(m, ID);
		ki.spieler(spielt, mitspieler);
		return ki.kontra(model);
	} 

	public void sieger(int s1, int s2) {
		//Der Sieger wird festgestellt
	}
	
	public String gibIP() {
		return "local";
	}

	public String gibName() {
		return name;
	}

	public synchronized void setzeID(int ID) {
		this.ID = ID;
		try {
			ki.setzeID(ID);
		} catch(NullPointerException npe) {
			//ki noch nicht initialisiert
		}
	}

	public synchronized Model gibModel() {
		try {
			//Warten, damit das Spiel ein wenig verzögert wird
			Thread.sleep(wartezeit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ki.spiel(model);
	}

	public synchronized Karte gibKarte() {
		return karte;
	}

	public synchronized boolean hochzeit() {
		karte = Hochzeit.hochzeitAnnehmen(model, ID);
		if(karte == null) {
			return false;
		} else {
			return true;
		}
	}

	public void spieler(int spielt, int mitspieler) throws Exception {
		//Der Server sendet, wer spielt
		this.spielt = spielt;
		this.mitspieler = mitspieler;
	}

	public void geklopft(boolean[] geklopft) throws Exception {
		//Der Server gibt an, wer geklopft hat (CLIENT)
	}

	public void kontra(boolean[] kontra) throws Exception {
		//Der Server gibt an, wer Kontra gegeben hat (CLIENT)
	}

	public synchronized void setzeModel(Model model) {
		this.model = model;
	}

	public void name() {
		//Der Server weist den Spieler an einen Namen einzugeben (CLIENT)
	}

	public void beenden() {
		//Server beendet das Spiel
		server.entferneSpieler(this);
	}

	public void abmelden() {
		//Server beendet das Spiel (CLIENT)
		beenden();
	}

	public void rundeZuende(int kontostand) {
	}

	public void konto(int kontostand) {
	}

	public void update(Model model) {
		setzeModel(model);
	}
	
	public void weristdran(int ID) {
		//Server sendet, wer dran ist (CLIENT)
	}
}

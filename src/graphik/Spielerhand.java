package graphik;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Spielerhand extends JPanel {
	
	private ArrayList<KartenLabel> karten;
	
	public Spielerhand() {
		super();
		
		this.setLayout(null);
		
		this.setSize(420, 100);
		
		karten = new ArrayList<KartenLabel>();
		for(int i = 0; i < 6; i++) {
			karten.add(new KartenLabel(null, 60, 100));
			this.add(karten.get(i));
			karten.get(i).setLocation(70*i, 0);
			karten.get(i).setVisible(true);
			//Kartenhintergrund anzeigen
			karten.get(i).setBild(null);
		}
	}
	
	/**
	 * Setzt die angezeigten Karten -> entfernt eine Karte
	 * @param Anzahl der angezeigten Karten
	 */
	public void setzeKarten(int angezeigt) {
		for(int i = 6; i > angezeigt; i--) {
			//setzt gespielte Karten unsichtbar
			karten.get(i).setVisible(false);
		}
	}
	
	/**
	 * Setzt alles auf Anfang
	 */
	public void reset() {
		for(int i = 0; i < 6; i++) {
			karten.get(i).setVisible(true);
		}
	}
}

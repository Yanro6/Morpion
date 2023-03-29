package morpion.fx.model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import morpions.kit.test.SpecifModeleMorpions;

public class ModeleMorpionFX implements SpecifModeleMorpions {

	// public int[][] matrice;
	private ReadOnlyIntegerWrapper[][] plateau;

	public ReadOnlyIntegerProperty casePlateauProperty(int ligne, int colonne) {
		return plateau[ligne - 1][colonne - 1].getReadOnlyProperty();
	}

	private int getVal(int ligne, int colonne) {
		return plateau[ligne][colonne].getValue();
	}

	private void setVal(int ligne, int colonne, int val) {
		plateau[ligne][colonne].setValue(val);
	}

	public Etat etat;

	// public int nbCoupsJoue;
	private ReadOnlyIntegerWrapper nbCoups;

	public ReadOnlyIntegerProperty nbCoupsProperty() {
		return nbCoups.getReadOnlyProperty();
	}

	// Symbole désignant le joueur courant
	private StringProperty symboleJoueurCourant;

	public StringProperty symboleJoueurCourantProperty() {
		return this.symboleJoueurCourant;
	}

	// Accesseurs "Java Bean" sur la valeur encapsulée
	public String getSymboleJoueurCourant() {
		return symboleJoueurCourant.getValue();
	}

	private void setSymboleJoueurCourant(String ch) {
		symboleJoueurCourant.setValue(ch);
	}

	public ModeleMorpionFX() {
		super();

		// def grille
		// matrice = new int[TAILLE][TAILLE];
		plateau = new ReadOnlyIntegerWrapper[TAILLE][TAILLE];
		for (int lig = 0; lig < TAILLE; lig++) {
			for (int col = 0; col < TAILLE; col++) {
				plateau[lig][col] = new ReadOnlyIntegerWrapper();
			}
		}

		// init nb coups joue
		nbCoups = new ReadOnlyIntegerWrapper();
		setNombreCoups(0);

		// init etat
		etat = Etat.J1_JOUE;

		// init symbole joueur
		symboleJoueurCourant = new SimpleStringProperty();
		setSymboleJoueurCourant(symboleJoueur(getJoueur()));
	}

	@Override
	public Etat getEtatJeu() {
		return etat;
	}

	@Override
	public int getJoueur() {
		if (etat == Etat.J1_JOUE) {
			return 1;
		} else if (etat == Etat.J2_JOUE) {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public int getVainqueur() {
		if (etat == Etat.J1_VAINQUEUR) {
			return 1;
		} else if (etat == Etat.J2_VAINQUEUR) {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public int getNombreCoups() {
		return nbCoups.intValue();
	}

	private void setNombreCoups(int i) {
		nbCoups.set(i);
	}

	@Override
	public boolean estFinie() {
		int valeur;

		// verifie lignes
		for (int lig = 0; lig < TAILLE; lig++) {
			valeur = 1;
			for (int col = 0; col < TAILLE; col++) {
				valeur = valeur * getVal(lig, col);
			}
			if (partieFinie(valeur)) {
				return true;
			}
		}

		// verifie colonnes
		for (int col = 0; col < TAILLE; col++) {
			valeur = 1;
			for (int lig = 0; lig < TAILLE; lig++) {
				valeur = valeur * getVal(lig, col);
			}
			if (partieFinie(valeur)) {
				return true;
			}
		}

		// verifie diagonales
		valeur = 1;
		for (int dia = 0; dia < TAILLE; dia++) {
			valeur = valeur * getVal(dia, dia);
		}
		if (partieFinie(valeur)) {
			return true;
		}

		valeur = 1;
		for (int dia = TAILLE - 1; dia >= 0; dia--) {
			valeur = valeur * getVal(dia, dia);
		}
		if (partieFinie(valeur)) {
			return true;
		}

		if (getNombreCoups() == 9) {
			etat = Etat.MATCH_NUL;
			return true;
		}

		return false;
	}

	public boolean partieFinie(int v) {
		if (v == 8) {
			etat = Etat.J2_VAINQUEUR;
			return true;
		} else if (v == 1) {
			etat = Etat.J1_VAINQUEUR;
			return true;
		}
		return false;
	}

	@Override
	public boolean estCoupAutorise(int ligne, int colonne) {
		if (estFinie()) {
			return false;
		} else if ((ligne > TAILLE) || (colonne > TAILLE) || (ligne < 1) || (colonne < 1)) {
			return false;
		} else if (getVal(ligne - 1, colonne - 1) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void jouerCoup(int ligne, int colonne) {
		if (estCoupAutorise(ligne, colonne)) {
			int lig = ligne - 1;
			int col = colonne - 1;
			if (getEtatJeu() == Etat.J1_JOUE) {
				setVal(lig, col, 1);
				etat = Etat.J2_JOUE;
			} else if (getEtatJeu() == Etat.J2_JOUE) {
				setVal(lig, col, 2);
				etat = Etat.J1_JOUE;
			}
			setNombreCoups(getNombreCoups() + 1);
		}

		setSymboleJoueurCourant(symboleJoueur(getJoueur()));
		estFinie();
		affiche();
	}

	public void affiche() {
		String ligne = "";
		for (int lig = 0; lig < TAILLE; lig++) {
			for (int col = 0; col < TAILLE; col++) {
				ligne = ligne + Integer.toString(getVal(lig, col));
			}
			ligne = ligne + "\n";
		}

		System.out.println(ligne);
	}

	public String symboleJoueur(int val) {
		switch (val) {
		case 1:
			return "x";
		case 2:
			return "o";
		default:
			return " ";
		}
	}
}

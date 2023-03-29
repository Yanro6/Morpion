package morpion.fx.v2;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import morpion.fx.model.ModeleMorpionFX;

public class ControllerV2 {

	private ModeleMorpionFX modele;

	@FXML // pour rendre la variable visible depuis SceneBuilder
	private GridPane grille;
	@FXML
	private Label labelNbCoups;
	@FXML
	private Label labelEtatJeu;
	@FXML
	private Label labelJoueur;

	@FXML
	private void initialize() {
		modele = new ModeleMorpionFX();
		recalculerLabelEtatJeu();
		for (Node n : grille.getChildren()) {
			n.setOnMouseClicked(e -> this.clicBouton(e));
		}
		modele.nbCoupsProperty().addListener((obsValue, oldValue, newValue) -> majNbCoups(newValue.intValue()));
		labelJoueur.textProperty().bind(modele.symboleJoueurCourantProperty());

		for (Node enfant : grille.getChildren()) {
			// enfants de "grille": des Labels, mais pas que...
			if (enfant instanceof Label) {
				Label l = (Label) enfant;
				int ligne = (int) l.getProperties().get("gridpane-row") + 1;
				int colonne = (int) l.getProperties().get("gridpane-column") + 1;
				modele.casePlateauProperty(ligne, colonne).addListener((obs, oldV, newV) -> {
					l.setText(modele.symboleJoueur(newV.intValue()));
				});
			}
		}

	}

	private void clicBouton(MouseEvent e) {
		Node n = (Node) e.getSource();
		Integer ligne = ((Integer) n.getProperties().get("gridpane-row")) + 1;
		Integer colonne = ((Integer) n.getProperties().get("gridpane-column")) + 1;
		modele.jouerCoup(ligne, colonne);
		recalculerLabelEtatJeu();
		System.out.println("Coup joué : " + ligne + "/" + colonne);
		System.out.println("résultat: " + modele.getEtatJeu());
	}

	private void recalculerLabelEtatJeu() {
		switch (modele.getEtatJeu()) {
		case J1_JOUE:
			labelEtatJeu.setText("C'est au tour du joueur 1 de jouer");
		case J2_JOUE:
			labelEtatJeu.setText("C'est au tour du joueur 2 de jouer");
			break;
		case J1_VAINQUEUR:
			labelEtatJeu.setText("Le gagnant est le joueur 1");
		case J2_VAINQUEUR:
			labelEtatJeu.setText("Le gagnant est le joueur 2");
		case MATCH_NUL:
			labelEtatJeu.setText("Le match est nul");
			break;
		default:
			break;
		}
	}

	private void majNbCoups(int nb) {
		if (nb == 0) {
			labelNbCoups.setText("aucun coup joué");
		} else {
			String ch;
			if (nb == 1)
				ch = " coup joué";
			else
				ch = " coups joués";
			labelNbCoups.setText(Integer.toString(nb) + ch);
		}
	}

}

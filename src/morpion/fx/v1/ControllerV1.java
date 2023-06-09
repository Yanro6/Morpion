package morpion.fx.v1;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import morpion.fx.model.ModeleMorpionFX;

public class ControllerV1 {

	private ModeleMorpionFX modele;

	@FXML // pour rendre la variable visible depuis SceneBuilder
	private GridPane grille;

	@FXML // pour rendre la m�thode visible depuis SceneBuilder
	private void initialize() {
		modele = new ModeleMorpionFX();
		for (Node n : grille.getChildren()) {
			n.setOnMouseClicked(e -> this.clicBouton(e));
		}
	}

	private void clicBouton(MouseEvent e) {
		Node n = (Node) e.getSource();
		Integer ligne = ((Integer) n.getProperties().get("gridpane-row")) + 1;
		Integer colonne = ((Integer) n.getProperties().get("gridpane-column")) + 1;
		modele.jouerCoup(ligne, colonne);
		System.out.println("Coup jou� : " + ligne + "/" + colonne);
		System.out.println("r�sultat: " + modele.getEtatJeu());
	}

}

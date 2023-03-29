package morpion.fx.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import morpions.kit.test.Bogue1;
import morpions.kit.test.Bogue2;
import morpions.kit.test.Bogue3;
import morpions.kit.test.Bogue4;
import morpions.kit.test.Bogue5;
import morpions.kit.test.MorpionsReference;
import morpions.kit.test.SpecifModeleMorpions;
import morpions.kit.test.SpecifModeleMorpions.Etat;

public class MorpionsTest {
	SpecifModeleMorpions morpions;
	public static final int TAILLE = 3;
	public static final int NB_CASES = 9;

	@BeforeEach
	public void setUp() throws Exception {
		// morpions = new MorpionsReference();
		// morpions = new Bogue1();
		// morpions = new Bogue2();
		// morpions = new Bogue3();
		// morpions = new Bogue4();
		// morpions = new Bogue5();
		 morpions = new ModeleMorpionFX();
	}

	@Test
	public void testInit() {// TODO

		// Scenario verifiant l'etat du jeu avant le premier coup:
		// * non-fin de partie
		assertTrue(!morpions.estFinie(), "Partie pas finie");

		// * cases accessibles
		for (int lig = 1; lig <= TAILLE; lig++) {
			for (int col = 1; col <= TAILLE; col++) {
				assertTrue(morpions.estCoupAutorise(lig, col), "La case (" + lig + "," + col + ") doit etre jouable");
			}
		}

		// Test de l'invariant de la classe
		testInvariant();
	}

	@Test
	public void testPremierCoup() {

		// Scenario verifiant le premier coup joué, notamment:
		// * non fin de partie
		assertTrue(!morpions.estFinie(), "Partie pas finie apres premier coup");

		// * identite du premier joueur
		assertEquals(Etat.J1_JOUE, morpions.getEtatJeu(), "le joueur 1 doit faire le premier coup");

		// * position correcte ou non
		assertTrue(morpions.estCoupAutorise(1, 1), "La case (1,1) doit etre jouable");

		morpions.jouerCoup(1, 1);

		// On reteste l'invariant
		testInvariant();
	}

	private void testInvariant() {// TODO
		// Le nombre de coups est positif ou nul, et infÃ©rieur ou Ã©gal au nombre de
		// cases
		assertTrue(morpions.getNombreCoups() >= 0, "Nombre de coups >= 0");
		assertTrue(morpions.getNombreCoups() <= NB_CASES, "Nombre de coups <= " + NB_CASES);
		// ----------------------
		// SÃ‰QUENCE 3 Ã€ COMPLÃ‰TER
		// ----------------------
	}

	@Test
	public void testCoupDejaJoue() {
		morpions.jouerCoup(1, 1);
		assertTrue(!morpions.estCoupAutorise(1, 1), "La case (1,1) ne peut etre joue");
	}

	@Test
	public void testPartiePasFinie() {
		morpions.jouerCoup(1, 1);
		morpions.jouerCoup(2, 2);

		// Scénario explorant les situations de non-fin de partie, avec vérification
		// systématique de estFinie() == false
		assertTrue(!morpions.estFinie(), " La partie est finie");
	}

	@Test
	public void testJoueur1gagnant() {
		morpions.jouerCoup(1, 1); // 1
		morpions.jouerCoup(2, 2);
		morpions.jouerCoup(1, 2); // 1
		morpions.jouerCoup(2, 3);
		morpions.jouerCoup(1, 3); // 1

		assertEquals(1, morpions.getVainqueur(), "le joueur 1 n'est pas gagnant");
		assertTrue(morpions.estFinie(), " La partie doit etre finie");
		assertFalse(morpions.estCoupAutorise(3, 1), "Le coup ne peut etre joue, la partie est finie");
	}

	// TODO
	@Test
	public void testControle() {
		// Scénario tentant divers coups non autorisés,// avec vérification systématique
		// de estCoupAutorise()
		assertFalse(morpions.estCoupAutorise(0, 0), "La case (0,0) ne peut etre joue");
		assertFalse(morpions.estCoupAutorise(-1, 1), "La case (-1,1) ne peut etre joue");
		assertFalse(morpions.estCoupAutorise(1, 5), "La case (1,5) ne peut etre joue");

	}

	@Test
	public void testFinPartie() {
		// Scenario explorant les situations de fin de partie
		morpions.jouerCoup(1, 3); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(1, 2);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 1); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(1, 1);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 2); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(3, 1);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(3, 2); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 3);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(3, 3); // 1
		assertTrue(morpions.estFinie(), " La partie doit etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
	}

	public void testHorizontal() {
		morpions.jouerCoup(1, 1); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 2);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(1, 2); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 1);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(1, 3); // 1
		assertTrue(morpions.estFinie(), " La partie doit etre finie");
		assertEquals(1, morpions.getVainqueur(), "le vainqueur doit etre le joueur 1");
	}

	public void testVertical() {
		morpions.jouerCoup(1, 1); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 2);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 1); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 3);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(3, 1); // 1
		assertTrue(morpions.estFinie(), " La partie doit etre finie");
		assertEquals(1, morpions.getVainqueur(), "le vainqueur doit etre le joueur 1");
	}

	public void testDiagonal() {
		morpions.jouerCoup(1, 1); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 1);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 2); // 1
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(2, 3);
		assertFalse(morpions.estFinie(), " La partie ne doit pas etre finie");
		assertEquals(0, morpions.getVainqueur(), "il n'y a pas de vainqueur");
		morpions.jouerCoup(3, 3); // 1
		assertTrue(morpions.estFinie(), " La partie doit etre finie");
		assertEquals(1, morpions.getVainqueur(), "le vainqueur doit etre le joueur 1");
	}

}

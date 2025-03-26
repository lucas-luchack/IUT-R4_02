package com.montaury.pokebagarre.ui;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(ApplicationExtension.class)
class PokeBagarreAppTest {
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1 = "#nomPokemon1";
    private static final String IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2 = "#nomPokemon2";
    private static final String IDENTIFIANT_BOUTON_BAGARRE = ".button";

    private static String getResultatBagarre(final FxRobot robot) {
        return robot.lookup("#resultatBagarre").queryText().getText();
    }

    private static String getMessageErreur(final FxRobot robot) {
        return robot.lookup("#resultatErreur").queryLabeled().getText();
    }

    @Start
    private void start(final Stage stage) {
        new PokeBagarreApp().start(stage);
    }

    @Test
    void interface_should_show_winner_when_pkm1_ok_pkm2_ok(final FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Raichu");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    String resultatBagarre = getResultatBagarre(robot);
                    assertThat(resultatBagarre).contains("Le vainqueur est:");
                });
    }

    @Test
    void interface_should_display_error_when_pkm1_empty(final FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    String erreur = getMessageErreur(robot);
                    assertThat(erreur).isEqualTo("Erreur: Le premier pokemon n'est pas renseigne");
                });
    }

    @Test
    void interface_should_display_error_when_pkm2_empty(final FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    String erreur = getMessageErreur(robot);
                    assertThat(erreur).isEqualTo("Erreur: Le second pokemon n'est pas renseigne");
                });
    }

    @Test
    void interface_should_display_error_when_pkm1_ok_pkm2_doesnt_exist(final FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Null");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    String erreur = getMessageErreur(robot);
                    assertThat(erreur).isEqualTo("Erreur: Impossible de recuperer les details sur 'Null'");
                });
    }

    @Test
    void interface_should_display_error_when_pkm1_pkm2_same(final FxRobot robot) {
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_1);
        robot.write("Pikachu");
        robot.clickOn(IDENTIFIANT_CHAMP_DE_SAISIE_POKEMON_2);
        robot.write("Pikachu");

        robot.clickOn(IDENTIFIANT_BOUTON_BAGARRE);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(
                () -> {
                    String erreur = getMessageErreur(robot);
                    assertThat(erreur).isEqualTo("Erreur: Impossible de faire se bagarrer un pokemon avec lui-meme");
                });
    }
}
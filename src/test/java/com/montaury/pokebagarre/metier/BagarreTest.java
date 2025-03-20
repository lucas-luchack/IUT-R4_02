package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.erreurs.ErreurRecuperationPokemon;
import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class BagarreTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void pkm1_is_null_pkm2_ok_thrown_error() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = null;
        String nomSecondPokemon = "pikachu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pkm1_ok_pkm2_is_null_thrown_error() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = null;

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void pkm1_ok_pkm2_ok_pkm1_equals_pkm2_thrown_error() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "pikachu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurMemePokemon.class);
    }

    @Test
    void pkm1_is_empty_pkm2_ok_thrown_error() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "";
        String nomSecondPokemon = "raichu";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    void pkm1_ok_pkm2_is_empty_thrown_error() {
        // GIVEN
        Bagarre bagarre = new Bagarre();
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "";

        // WHEN
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(nomPremierPokemon, nomSecondPokemon));

        // THEN
        assertThat(thrown).isInstanceOf(ErreurPokemonNonRenseigne.class).hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    void pkm1_ok_win_pkm2_ok() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "raichu";
        Pokemon pikachu = ConstructeurDePokemon.unPokemon().avecAttaque(10).construire();
        Pokemon raichu = ConstructeurDePokemon.unPokemon().construire();

        // WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon))
                .thenReturn(CompletableFuture.completedFuture(pikachu));
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon))
                .thenReturn(CompletableFuture.completedFuture(raichu));

        CompletableFuture<Pokemon> resultat = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        // THEN
        assertThat(resultat).succeedsWithin(Duration.ofSeconds(2)).satisfies(pokemon -> {
            assertThat(pokemon).isEqualTo(pikachu);
        });
    }

    @Test
    void pkm1_ok_loose_pkm2_ok() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "raichu";
        Pokemon pikachu = ConstructeurDePokemon.unPokemon().construire();
        Pokemon raichu = ConstructeurDePokemon.unPokemon().avecAttaque(10).construire();

        // WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon))
                .thenReturn(CompletableFuture.completedFuture(pikachu));
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon))
                .thenReturn(CompletableFuture.completedFuture(raichu));

        CompletableFuture<Pokemon> resultat = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        // THEN
        assertThat(resultat).succeedsWithin(Duration.ofSeconds(2)).satisfies(pokemon -> {
            assertThat(pokemon).isEqualTo(raichu);
        });
    }

    @Test
    void pkm1_does_not_exist_pkm2_ok_thrown_error() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "raichu";
        Pokemon raichu = ConstructeurDePokemon.unPokemon().construire();

        // WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomPremierPokemon)));
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon))
                .thenReturn(CompletableFuture.completedFuture(raichu));

        CompletableFuture<Pokemon> resultat = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        // THEN
        assertThat(resultat).failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur '" + nomPremierPokemon + "'");
    }

    @Test
    void pkm1_ok_pkm2_does_not_exist_thrown_error() {
        // GIVEN
        var fausseApi = Mockito.mock(PokeBuildApi.class);
        Bagarre bagarre = new Bagarre(fausseApi);
        String nomPremierPokemon = "pikachu";
        String nomSecondPokemon = "raichu";
        Pokemon pikachu = ConstructeurDePokemon.unPokemon().construire();

        // WHEN
        Mockito.when(fausseApi.recupererParNom(nomPremierPokemon))
                .thenReturn(CompletableFuture.completedFuture(pikachu));
        Mockito.when(fausseApi.recupererParNom(nomSecondPokemon))
                .thenReturn(CompletableFuture.failedFuture(new ErreurRecuperationPokemon(nomSecondPokemon)));

        CompletableFuture<Pokemon> resultat = bagarre.demarrer(nomPremierPokemon, nomSecondPokemon);

        // THEN
        assertThat(resultat).failsWithin(Duration.ofSeconds(2))
                .withThrowableOfType(ExecutionException.class)
                .havingCause()
                .isInstanceOf(ErreurRecuperationPokemon.class)
                .withMessage("Impossible de recuperer les details sur '" + nomSecondPokemon + "'");
    }

}
package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonTest {
    ConstructeurDePokemon pokemon = new ConstructeurDePokemon();

    /**
     * Test du cas de victoire du Pokémon 1 face au Pokémon 2 avec une victoire par attaque dominante
     */
    @Test
    void pkm1_win_pkm2_by_attack() {
        // GIVEN
        Pokemon poke1 = this.pokemon.avecAttaque(10).construire();
        Pokemon poke2 = this.pokemon.avecAttaque(0).construire();

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertThat(resultat).isTrue();
    }

    /**
     * Test du cas de victoire du Pokémon 2 face au Pokémon 1 avec une victoire par attaque dominante
     */
    @Test
    void pkm1_loose_pkm2_by_attack() {
        // GIVEN
        Pokemon poke1 = this.pokemon.avecAttaque(0).construire();
        Pokemon poke2 = this.pokemon.avecAttaque(10).construire();

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertThat(resultat).isFalse();
    }

    /**
     * Test du cas de victoire du Pokémon 1 face au Pokémon 2 avec une victoire par défense dominante lorsque l'attaque est égale
     */
    @Test
    void pkm1_win_pkm2_with_defense_same_attack() {
        // GIVEN
        Pokemon poke1 = this.pokemon.avecDefense(10).construire();
        Pokemon poke2 = this.pokemon.avecDefense(0).construire();

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertThat(resultat).isTrue();
    }

    /**
     * Test du cas de victoire du Pokémon 2 face au Pokémon 1 avec une victoire par défense dominante lorsque l'attaque est égale
     */
    @Test
    void pkm1_loose_pkm2_with_defense_same_attack() {
        // GIVEN
        Pokemon poke1 = this.pokemon.avecDefense(0).construire();
        Pokemon poke2 = this.pokemon.avecDefense(10).construire();

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertThat(resultat).isFalse();
    }

    /**
     * Test du cas de victoire du Pokémon 1 face au Pokémon 2 avec une victoire par priorité lorsque l'attaque et la défense est égale
     */
    @Test
    void pkm1_win_pkm2_with_same_defense_and_attack() {
        // GIVEN
        Pokemon poke1 = this.pokemon.construire();
        Pokemon poke2 = this.pokemon.construire();

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertThat(resultat).isTrue();
    }
}
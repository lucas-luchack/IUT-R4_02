package com.montaury.pokebagarre.metier;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    public static final String PKM1_WIN = "Le Pokemon 1 n'est pas gagnant !";
    public static final String PKM2_WIN = "Le Pokemon 2 n'est pas gagnant !";


//    Pokemon pokemon1;
//    Pokemon pokemon2;
//
//    @BeforeAll
//    void setupPokemons() {
//        pokemon1 = new Pokemon("Pokemon 1", "", new Stats(10, 0));
//    }

    @Test
    void pkm1_win_pkm2_by_attack() {
        // GIVEN
        Pokemon poke1 = new Pokemon("1", null, new Stats(10, 0));
        Pokemon poke2 = new Pokemon("2", null, new Stats(0, 0));

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertTrue(resultat, PKM1_WIN);
    }

    @Test
    void pkm1_loose_pkm2_by_attack() {
        // GIVEN
        Pokemon poke1 = new Pokemon("1", null, new Stats(0, 0));
        Pokemon poke2 = new Pokemon("2", null, new Stats(10, 0));

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertFalse(resultat, PKM2_WIN);
    }

    @Test
    void pkm1_win_pkm2_with_defense_same_attack() {
        // GIVEN
        Pokemon poke1 = new Pokemon("1", null, new Stats(10, 10));
        Pokemon poke2 = new Pokemon("2", null, new Stats(10, 0));

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertTrue(resultat, PKM1_WIN);
    }

    @Test
    void pkm1_loose_pkm2_with_defense_same_attack() {
        // GIVEN
        Pokemon poke1 = new Pokemon("1", null, new Stats(10, 0));
        Pokemon poke2 = new Pokemon("2", null, new Stats(10, 10));

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertFalse(resultat, PKM2_WIN);
    }

    @Test
    void pkm1_win_pkm2_with_same_defense_and_attack() {
        // GIVEN
        Pokemon poke1 = new Pokemon("1", null, new Stats(10, 10));
        Pokemon poke2 = new Pokemon("2", null, new Stats(10, 10));

        // WHEN
        boolean resultat = poke1.estVainqueurContre(poke2);

        // THEN
        assertTrue(resultat, PKM1_WIN);
    }
}
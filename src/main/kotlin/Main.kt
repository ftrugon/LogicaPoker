package org.example

fun main() {
    val jugadores = listOf(
        Jugador("A",123),
        Jugador("B",123),
        Jugador("C",123))


    val manoManager = ManosManager()
    val poker = Poker(jugadores,manoManager)

    poker.iniciarRonda()
}
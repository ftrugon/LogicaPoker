package org.example


fun main() {
    val jugadores = mutableListOf(
        Jugador("A",123),
        Jugador("B",123),
        Jugador("C",123))

    val manoManager = ManosManager()
    val apuestasManager = ApuestasManager()
    val poker = Poker(jugadores,manoManager,apuestasManager)

    poker.iniciarRonda()
}
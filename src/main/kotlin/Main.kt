package org.example


fun main() {
    val jugadores = mutableListOf(
        Jugador("A",200),
        Jugador("B",200),
        Jugador("C",100))

    val manoManager = ManosManager()
    val apuestasManager = ApuestasManager()
    val potManager = PotManager(apuestasManager)
    val poker = Poker(jugadores,manoManager,apuestasManager,potManager)

    poker.iniciarRonda()
}
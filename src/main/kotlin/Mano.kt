package org.example

data class Mano(
    var ranking: JugadaRanking,
    var cartasJugada: List<Carta>,
    var kickers: List<Carta>
)

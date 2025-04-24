package org.example

class ApuestasManager {

    private val apuestasHechas = mutableListOf<Apuesta>()

    fun hacerApuesta(jugador: Jugador, cantidad: Int) {
        apuestasHechas.add(Apuesta(jugador, cantidad))
    }

    fun calcularDineroTotal(): Int {
        return apuestasHechas.sumOf { it.cantidad }
    }

    fun apuestasPorJugador(jugador: Jugador): List<Apuesta> {
        return apuestasHechas.filter { it.jugador == jugador }
    }

    fun apuestaTotalPorJugador(jugador: Jugador): Int {
        return apuestasHechas.filter { it.jugador == jugador }.sumOf { it.cantidad }
    }

    fun limpiar() {
        apuestasHechas.clear()
    }


}
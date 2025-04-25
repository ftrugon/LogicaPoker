package org.example

class PotManager(private val apuestasManager: ApuestasManager) {

    fun calcularSidePots(): List<SidePot> {
        val apuestasPorJugador = apuestasManager
            .obtenerApuestasHechas()
            .groupBy { it.jugador }
            .mapValues { it.value.sumOf { a -> a.cantidad } }
            .toMutableMap()

        val pots = mutableListOf<SidePot>()

        while (apuestasPorJugador.isNotEmpty()) {
            val min = apuestasPorJugador.values.minOrNull() ?: break
            val jugadores = apuestasPorJugador.filterValues { it >= min }.keys.toList()
            val monto = min * jugadores.size
            pots.add(SidePot(monto, jugadores))

            jugadores.forEach {
                apuestasPorJugador[it] = apuestasPorJugador[it]!! - min
                if (apuestasPorJugador[it] == 0) apuestasPorJugador.remove(it)
            }
        }

        return pots
    }


}
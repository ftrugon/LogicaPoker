package org.example

class Poker(
    private val jugadores :List<Jugador>,
    private val manosManager: ManosManager
) {

    private val baraja = Baraja()
    private val cartasSobreMesa = mutableListOf<Carta>()

    fun iniciarRonda() {
        println("\n--- Nueva Ronda ---")
        cartasSobreMesa.clear()
        baraja.llenarBaraja()
        baraja.barajar()
        jugadores.forEach {
            it.darCarta(baraja.sacarCarta())
            it.darCarta(baraja.sacarCarta())
        }
        distribuirCartasComunitarias()
        elegirGanador()
    }

    fun elegirGanador(){
        calcularManosJugadores()
    }

    fun calcularManosJugadores(){
        jugadores.forEach {jugador ->
            jugador.mano = manosManager.calcularMano((jugador.cartas + cartasSobreMesa).sortedByDescending { it.valor.peso })
        }
    }

    private fun distribuirCartasComunitarias() {
        cartasSobreMesa.addAll(
            listOf(
                baraja.sacarCarta(),
                baraja.sacarCarta(),
                baraja.sacarCarta(),
                baraja.sacarCarta(),
                baraja.sacarCarta())
        )
        println("Cartas comunitarias: $cartasSobreMesa")
    }



}
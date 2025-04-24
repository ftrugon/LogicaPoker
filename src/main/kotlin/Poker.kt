package org.example

class Poker(
    private val jugadores :MutableList<Jugador>,
    private val manosManager: ManosManager,
    private val apuestasManager: ApuestasManager
) {

    private val jugadoresActivos = jugadores
    private val baraja = Baraja()
    private val cartasSobreMesa = mutableListOf<Carta>()



    fun iniciarRonda() {
        println("\n--- Nueva Partida ---")

        cartasSobreMesa.clear()
        baraja.llenarBaraja()
        baraja.barajar()

        preFlop()

        flop()

        turn()

        river()

        showdown()
    }

    private fun repartirCartas(){
        jugadores.forEach {
            it.darCarta(baraja.sacarCarta())
            it.darCarta(baraja.sacarCarta())
        }
    }

    fun calcularJugadoresActivos() {
        jugadoresActivos.clear()

        if (jugadores.isEmpty()) return

        val dealerIndex = jugadores.indexOfFirst { it.isDealer }

        // Buscar el primer jugador activo desde el dealer (incluyéndolo) en orden circular
        val startIndex = jugadores
            .indices
            .map { (dealerIndex + it) % jugadores.size }
            .firstOrNull { !jugadores[it].hasFolded }

        if (startIndex != null) {
            // Desde el primer activo (dealer o siguiente), agregar todos los jugadores activos en orden circular
            for (i in jugadores.indices) {
                val index = (startIndex + i) % jugadores.size
                val jugador = jugadores[index]
                if (!jugador.hasFolded) {
                    jugadoresActivos.add(jugador)
                }
            }
        }
    }

    private fun rondaDeApuestas(ronda:Int){
        if (jugadoresActivos.isEmpty()) return

        var ultimaApuesta = 0
        var index = 0
        var jugadoresQueIgualaron = 0

        while (jugadoresQueIgualaron < jugadoresActivos.size) {
            val jugador = jugadoresActivos[index % jugadoresActivos.size]

            // Si el jugador ya se fue all-in, lo salteamos (no puede igualar ni subir)
            if (jugador.dinero <= 0) {
                jugadoresQueIgualaron++
                index++
                continue
            }

            println("Turno de ${jugador.nombre} (dinero: ${jugador.dinero})")
            println("Tus cartas")
            for (carta in jugador.cartas) {
                println(carta)
            }
            println("1. Foldear")
            println("2. Apostar / Igualar (mínimo para igualar: $ultimaApuesta)")

//            val opcion = readln().toIntOrNull()
            val opcion = 2
            when (opcion) {
                1 -> {
                    println("${jugador.nombre} se retira.")
                    jugador.hasFolded = true
                    jugadoresActivos.remove(jugador)
                    if (jugadoresActivos.size == 1) return
                }
                2 -> {
                    println("¿Cuánto deseas apostar?")
//                    val cantidadDeseada = readln().toIntOrNull() ?: 0
                    val cantidadDeseada = 0
                    val cantidadReal = minOf(jugador.dinero, cantidadDeseada)

                    jugador.dinero -= cantidadReal
                    cantidadReal
                    apuestasManager.hacerApuesta(jugador,cantidadReal)

                    if (cantidadReal > ultimaApuesta) {
                        ultimaApuesta = cantidadReal
                        jugadoresQueIgualaron = 1 // alguien subió, reiniciamos
                    } else if (cantidadReal == ultimaApuesta) {
                        jugadoresQueIgualaron++
                    } else {
                        // Apostó menos porque fue all-in, se cuenta como igualado
                        jugadoresQueIgualaron++
                    }
                }
                else -> {
                    println("Opción inválida.")
                    continue
                }
            }

            index++
        }

    }

    fun preFlop(){
        repartirCartas()
        rondaDeApuestas(0)
    }

    private fun distribuirCartasComunitarias(numCartas:Int) {
        for (i in 0..<numCartas){
            cartasSobreMesa.add(baraja.sacarCarta())
        }
        println("Cartas comunitarias: $cartasSobreMesa")
    }



    fun flop(){
        distribuirCartasComunitarias(3)
        calcularManosJugadores()
        rondaDeApuestas(1)
    }

    fun turn(){
        distribuirCartasComunitarias(1)
        calcularManosJugadores()
        rondaDeApuestas(2)
    }

    fun river(){
        distribuirCartasComunitarias(1)
        calcularManosJugadores()
        rondaDeApuestas(3)
    }

    fun showdown(){
        elegirGanador()
    }

    fun elegirGanador(){

        println(manosManager.compararManos(jugadores))


    }

    fun calcularManosJugadores(){
        jugadores.forEach {jugador ->
            jugador.mano = manosManager.calcularMano((jugador.cartas + cartasSobreMesa).sortedByDescending { it.valor.peso })
        }
    }





}
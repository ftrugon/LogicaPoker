package org.example

class ManosManager {

    fun calcularMano(todasLasCartasOrdenadas: List<Carta>):Mano{

        println(todasLasCartasOrdenadas)
        when {

            esEscaleraReal(todasLasCartasOrdenadas) ->{ // escalera real
                val escaleraReal = encontrarEscalera(todasLasCartasOrdenadas)
                println("*** ESCALERA REAl *** --> $escaleraReal")
                println("kickerss --> NO HAY KICKERS EN UNA ESCALERA REAL")

                return Mano(JugadaRanking.ESCALERA_REAL, escaleraReal, emptyList())
            }
            esEscaleraDeColor(todasLasCartasOrdenadas) ->{ // escalera de color
                val escaleraDeColor = encontrarEscalera(todasLasCartasOrdenadas)
                println("*** ESCALERA DE COLOR *** --> $escaleraDeColor")
                println("kickerss --> NO HAY KICKERS EN UNA ESCALERA DE COLOR")

                return Mano(JugadaRanking.ESCALERA_COLOR, escaleraDeColor, emptyList())
            }
            agruparCartas(todasLasCartasOrdenadas,4) -> { // caso para que sea pokjer
                val pokerJugado = todasLasCartasOrdenadas.groupBy { it.valor }.values.first { it.size == 4 }
                val kickers = todasLasCartasOrdenadas.filter { it.valor != pokerJugado[0].valor }.take(1)
                println("*** POKER *** --> $pokerJugado")
                println("kickerss --> $kickers")
                return Mano(JugadaRanking.POKER,pokerJugado,kickers)
            }
            agruparCartas(todasLasCartasOrdenadas,3) && agruparCartas(todasLasCartasOrdenadas,2) -> { // caso para que sea full house
                val parejaOTrioJugada = todasLasCartasOrdenadas.groupBy { it.valor }.values.first { it.size >= 2 }
                val segundaParejaOTrioJugada = todasLasCartasOrdenadas.filter { it.valor != parejaOTrioJugada[0].valor }.groupBy { it.valor }.values.first { it.size >= 2 }

                val fullJugado = parejaOTrioJugada + segundaParejaOTrioJugada

                println("*** FULL HOUSE *** --> $fullJugado")
                println("kickerss --> NO HAY KICKERS EN UN FULL HOUSE")
                return Mano(JugadaRanking.FULL_HOUSE,fullJugado, listOf())
            }
            esColor(todasLasCartasOrdenadas) -> { // color
                val colorJugado = todasLasCartasOrdenadas.groupBy { it.palo }.values.first { it.size >= 5 }.take(5)

                println("*** COLOR *** --> $colorJugado")
                println("kickerss --> NO HAY KICKERS EN UN FLUSH")

                return Mano(JugadaRanking.COLOR,colorJugado, listOf())

            }
            encontrarEscalera(todasLasCartasOrdenadas).isNotEmpty() ->{ // escalesra
                val escaleraJugada = encontrarEscalera(todasLasCartasOrdenadas)


                println("*** ESCALERA *** --> $escaleraJugada ")
                println("kickerss --> NO HAY KICKERS EN UNA ESCALERA")

                return Mano(JugadaRanking.ESCALERA, escaleraJugada, listOf())
            }
            agruparCartas(todasLasCartasOrdenadas,3) -> { // caso para que sea trio
                val trioJugado = todasLasCartasOrdenadas.groupBy { it.valor }.values.first { it.size == 3 }
                val kickers = todasLasCartasOrdenadas.filter { it.valor != trioJugado[0].valor }.take(2)
                println("*** TRIO *** --> $trioJugado")
                println("kickerss --> $kickers")
                return Mano(JugadaRanking.TRIO,trioJugado,kickers)
            }
            esDoblePareja(todasLasCartasOrdenadas) ->{ // caso de doble pareja
                val parejas = todasLasCartasOrdenadas.groupBy { it.valor }.values.filter { it.size == 2 }.sortedByDescending { it[0].valor.peso }.take(2).flatten()
                val kickers = todasLasCartasOrdenadas.filter { it.valor != parejas[0].valor && it.valor != parejas[2].valor }.take(1)

                println("*** DOBBLE PAREJA *** --> $parejas")
                println("kickerss --> $kickers")

                return Mano(JugadaRanking.DOBLE_PAREJA,parejas,kickers)

            }
            agruparCartas(todasLasCartasOrdenadas,2) -> { // caso para que sea pareja
                val parejaJugada = todasLasCartasOrdenadas.groupBy { it.valor }.values.first { it.size == 2 }
                val kickers = todasLasCartasOrdenadas.filter { it.valor != parejaJugada[0].valor }.take(3)
                println("*** PAREJA *** --> $parejaJugada")
                println("kickerss --> $kickers")
                return Mano(JugadaRanking.PAREJA,parejaJugada,kickers)
            }
            else -> { // carta alta
                val cartaAlta = todasLasCartasOrdenadas.first()
                val kickers = todasLasCartasOrdenadas.filter { it != cartaAlta }.take(4)

                println("*** CARTA ALTA *** --> $cartaAlta")
                println("kickerss --> $kickers")

                return Mano(JugadaRanking.CARTA_ALTA, listOf(cartaAlta),kickers)
            }
        }
    }

    private fun esEscaleraReal(cartas:List<Carta>):Boolean{
        return esColor(cartas) && encontrarEscalera(cartas).isNotEmpty() && cartas.any{it.valor == ValorCarta.AS}
    }

    private fun esEscaleraDeColor(cartas: List<Carta>):Boolean{
        return esColor(cartas) && encontrarEscalera(cartas).isNotEmpty()
    }

    private fun encontrarEscalera(cartas: List<Carta>):List<Carta>{
        val cartasSinRepeticiones = cartas.distinctBy { it.valor }

        val consecutivas = mutableListOf<Carta>()

        for (i in 0 until cartasSinRepeticiones.size-1){

            if (cartasSinRepeticiones[i].valor.peso == cartasSinRepeticiones[i+1].valor.peso + 1){

                if (consecutivas.isEmpty()){
                    consecutivas.add(cartasSinRepeticiones[i])
                }

                consecutivas.add(cartasSinRepeticiones[i+1])
                if (consecutivas.size == 5) return consecutivas

            }else{
                consecutivas.clear()
            }

        }

        return emptyList()
    }

    private fun esColor(cartas: List<Carta>):Boolean{
        return cartas.groupBy { it.palo }.any { it.value.size >= 5 }
    }

    private fun esDoblePareja(cartas: List<Carta>):Boolean{
        return cartas.groupBy { it.valor }.filter { it.value.size == 2 }.size >= 2
    }

    private fun agruparCartas(cartas:List<Carta>,numCartas:Int):Boolean{
        return cartas.groupBy { it.valor }.any { it.value.size == numCartas }
    }

    fun compararManos(){

    }


}
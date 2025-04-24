package org.example

class Jugador(
    val nombre:String,
    var dinero:Int,
    var cartas: MutableList<Carta> = mutableListOf<Carta>(),
    var mano: Mano? = null,
    var hasFolded:Boolean = false,
    var isBig:Boolean = false,
    var isSmall:Boolean = false,
    var isDealer:Boolean = false,
){
    fun darCarta(carta: Carta){
        cartas.add(carta)
    }

}

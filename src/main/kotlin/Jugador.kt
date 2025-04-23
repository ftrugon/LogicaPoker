package org.example

class Jugador(
    val nombre:String,
    var dinero:Int,
    var cartas: MutableList<Carta> = mutableListOf<Carta>(),
    var mano: Mano? = null
){
    fun darCarta(carta: Carta){
        cartas.add(carta)
    }
}

package org.example

data class Carta(var palo :PaloCarta, var valor :ValorCarta){
    override fun toString(): String {
        return "Carta: $valor de $palo"
    }
}
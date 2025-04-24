package org.example

class Baraja() {
    val cartas = mutableListOf<Carta>()


    fun llenarBaraja(){
        for (palo in PaloCarta.entries){
            for (valor in ValorCarta.entries){
                cartas.add(Carta(palo,valor))
            }
        }
    }

    fun sacarCarta():Carta{
        val cartaToReturn = cartas.first()
        cartas.removeFirst()
        return cartaToReturn
    }

    fun barajar(){
        cartas.shuffle()
    }

}

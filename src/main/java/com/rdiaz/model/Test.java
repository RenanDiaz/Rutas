package com.rdiaz.model;

public class Test
{
    int id;
    String partida;
    String destino;
    
    public Test(int id, String partida, String destino)
    {
        this.id = id;
        this.partida = partida;
        this.destino = destino;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPartida()
    {
        return partida;
    }

    public void setPartida(String partida)
    {
        this.partida = partida;
    }

    public String getDestino()
    {
        return destino;
    }

    public void setDestino(String destino)
    {
        this.destino = destino;
    }
}

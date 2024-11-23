package model;

import utils.RedBlackTree;

public class Hotel {
    private String nome_hotel;
    private RedBlackTree<Reserva> reservas;
    private RedBlackTree<Reserva> historico_cancelamentos;

    public Hotel(String nome_hotel) {
        this.nome_hotel = nome_hotel;
        this.reservas = new RedBlackTree<>();
        this.historico_cancelamentos = new RedBlackTree<>();
    }

    public RedBlackTree<Reserva> get_reservas() {
        return reservas;
    }

    public RedBlackTree<Reserva> get_historico_cancelamentos() {
        return historico_cancelamentos;
    }

    public String get_nome_hotel() {
        return nome_hotel;
    }
}

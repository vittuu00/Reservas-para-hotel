package controller;

import model.Hotel;
import model.Reserva;


public class ReservaController {
    private Hotel hotel;

    public ReservaController(Hotel hotel) {
        this.hotel = hotel;
    }

    // Cadastrar nova reserva
    public boolean cadastrar_reserva(Reserva reserva) {
        if (!verificar_disponibilidade(reserva)) {
            System.out.println("O quarto não está disponível para essas datas.");
            return false;
        }
        hotel.get_reservas().insert(reserva);
        System.out.println("Reserva cadastrada com sucesso!");
        return true;
    }

    // Verificar se o quarto está disponível para as datas solicitadas
public boolean verificar_disponibilidade(Reserva reserva) {
    for (Reserva r : hotel.get_reservas()) {
        if (r.get_numero_quarto() == reserva.get_numero_quarto() &&
            (reserva.get_data_check_in().isBefore(r.get_data_check_out()) &&
             reserva.get_data_check_out().isAfter(r.get_data_check_in()))) {
            return false; // Conflito de datas
        }
    }
    return true; // Sem conflito
}

    // Cancelar reserva
    public void cancelar_reserva(String cpf_cliente) {
        Reserva reserva = buscar_reserva(cpf_cliente);
        if (reserva != null) {
            hotel.get_reservas().remove(reserva);
            hotel.get_historico_cancelamentos().insert(reserva);
            System.out.println("Reserva cancelada com sucesso.");
        } else {
            System.out.println("Reserva não encontrada.");
        }
    }

    // Buscar reserva por CPF
    private Reserva buscar_reserva(String cpf_cliente) {
        for (Reserva reserva : hotel.get_reservas()) {
            if (reserva.get_cpf_cliente().equals(cpf_cliente)) {
            return reserva; // CPF encontrado
            }
        }
        return null; // CPF não encontrado
    }

}

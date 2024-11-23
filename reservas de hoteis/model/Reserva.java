package model;

import java.time.LocalDate;

public class Reserva implements Comparable<Reserva> {
    private String nome_cliente;
    private String cpf_cliente;
    private int numero_quarto;
    private LocalDate data_check_in;
    private LocalDate data_check_out;
    private String categoria_quarto;

    public Reserva(String nome_cliente, String cpf_cliente, int numero_quarto,
                   LocalDate data_check_in, LocalDate data_check_out, String categoria_quarto) {
        this.nome_cliente = nome_cliente;
        this.cpf_cliente = cpf_cliente;
        this.numero_quarto = numero_quarto;
        this.data_check_in = data_check_in;
        this.data_check_out = data_check_out;
        this.categoria_quarto = categoria_quarto;
    }

    // Getters e Setters
    public String get_nome_cliente() { return nome_cliente; }
    public void set_nome_cliente(String nome_cliente) { this.nome_cliente = nome_cliente; }
    public String get_cpf_cliente() { return cpf_cliente; }
    public void set_cpf_cliente(String cpf_cliente) { this.cpf_cliente = cpf_cliente; }
    public int get_numero_quarto() { return numero_quarto; }
    public void set_numero_quarto(int numero_quarto) { this.numero_quarto = numero_quarto; }
    public LocalDate get_data_check_in() { return data_check_in; }
    public void set_data_check_in(LocalDate data_check_in) { this.data_check_in = data_check_in; }
    public LocalDate get_data_check_out() { return data_check_out; }
    public void set_data_check_out(LocalDate data_check_out) { this.data_check_out = data_check_out; }
    public String get_categoria_quarto() { return categoria_quarto; }
    public void set_categoria_quarto(String categoria_quarto) { this.categoria_quarto = categoria_quarto; }

    // Implementação do método compareTo
    @Override
    public int compareTo(Reserva other) {
        // Comparação principal: CPF do cliente (String)
        int cpf_comparison = this.cpf_cliente.compareTo(other.cpf_cliente);
        if (cpf_comparison != 0) {
            return cpf_comparison;
        }

        // Comparação secundária: Número do quarto (int)
        int quarto_comparison = Integer.compare(this.numero_quarto, other.numero_quarto);
        if (quarto_comparison != 0) {
            return quarto_comparison;
        }

        // Comparação terciária: Data de check-in
        return this.data_check_in.compareTo(other.data_check_in);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "nome_cliente='" + nome_cliente + '\'' +
                ", cpf_cliente='" + cpf_cliente + '\'' +
                ", numero_quarto=" + numero_quarto +
                ", data_check_in=" + data_check_in +
                ", data_check_out=" + data_check_out +
                ", categoria_quarto='" + categoria_quarto + '\'' +
                '}';
    }
}

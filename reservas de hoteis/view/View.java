package view;

import controller.ReservaController;
import model.Hotel;
import model.Reserva;

import java.time.LocalDate;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Hotel Exemplo");
        ReservaController controller = new ReservaController(hotel);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1- Cadastrar Reserva");
                System.out.println("2- Cancelar Reserva");
                System.out.println("3- Verificar Disponibilidade");
                System.out.println("4- Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer

                if (opcao == 1) {
                    // Cadastro de reserva
                    System.out.print("CPF do Cliente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Nome do Cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Número do Quarto: ");
                    int numero_quarto = scanner.nextInt();
                    System.out.print("Data Check-In (YYYY-MM-DD): ");
                    String check_in = scanner.next();
                    System.out.print("Data Check-Out (YYYY-MM-DD): ");
                    String check_out = scanner.next();

                    LocalDate data_check_in = LocalDate.parse(check_in);
                    LocalDate data_check_out = LocalDate.parse(check_out);
                    Reserva reserva = new Reserva(nome, cpf, numero_quarto, data_check_in, data_check_out, "Econômico");

                    controller.cadastrar_reserva(reserva);
                } else if (opcao == 2) {
                    // Cancelamento de reserva
                    System.out.print("CPF do cliente para cancelamento: ");
                    String cpf_cancelamento = scanner.nextLine();
                    controller.cancelar_reserva(cpf_cancelamento);
                } else if (opcao == 3) {
                    // Consultar disponibilidade
                    System.out.print("Número do quarto: ");
                    int numero_quarto = scanner.nextInt();
                    System.out.print("Data Check-In (YYYY-MM-DD): ");
                    String check_in = scanner.next();
                    System.out.print("Data Check-Out (YYYY-MM-DD): ");
                    String check_out = scanner.next();

                    LocalDate data_check_in = LocalDate.parse(check_in);
                    LocalDate data_check_out = LocalDate.parse(check_out);
                    Reserva reserva = new Reserva("", "", numero_quarto, data_check_in, data_check_out, "");

                    if (controller.verificar_disponibilidade(reserva)) {
                        System.out.println("O quarto está disponível!");
                    } else {
                        System.out.println("O quarto não está disponível.");
                    }
                } else if (opcao == 4) {
                    break;
                }
            }
        }
    }
}

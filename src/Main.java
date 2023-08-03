import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem vindo!");

        while (true) {
            exibirStatusPedidos();

            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Fazer pedido");
            System.out.println("3 - Finalizar pedido");
            System.out.println("4 - Emitir recibo");

            int opc = scan.nextInt();
            scan.nextLine(); // Limpar o buffer do scanner após ler o número

            switch (opc) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    fazerPedido();
                    break;
                case 3:
                    finalizarPedido();
                    break;
                case 4:
                    emitirRecibo();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void exibirStatusPedidos() {
        int andamento = 0;
        int encerrado = 0;

        for (Pedido pedido : pedidos) {
            String estadoPedido = pedido.getEstado();
            if (estadoPedido.equals("Em andamento")) {
                andamento++;
            } else if (estadoPedido.equals("Encerrado")) {
                encerrado++;
            }
        }

        if (!pedidos.isEmpty()) {
            System.out.println(pedidos.size() + " Pedidos - Em andamento: " + andamento + " - Encerrados: " + encerrado);
        }
    }

    private static void cadastrarCliente() {
        System.out.println("NOME:");
        String nome = scan.nextLine();

        System.out.println("RUA:");
        String rua = scan.nextLine();
        System.out.println("NÚMERO:");
        int num = scan.nextInt();

        clientes.add(new Cliente(nome, new Endereco(rua, num)));
    }

    private static void fazerPedido() {
        System.out.println("Sabor:");
        String sabor = scan.nextLine();

        boolean clienteEncontrado = false;
        Cliente clienteBuscado = null;

        while (!clienteEncontrado) {
            System.out.println("Cliente:");
            String busca = scan.nextLine();

            for (Cliente cliente : clientes) {
                if (cliente.getNome().equalsIgnoreCase(busca.toLowerCase())) {
                    clienteEncontrado = true;
                    clienteBuscado = cliente;
                    break; // Encontrou o cliente, pode sair do loop
                }
            }

            if (!clienteEncontrado) {
                System.out.println("Cliente não encontrado");
            }
        }

        Pedido pedido = new Pedido(sabor, "Em andamento", clienteBuscado);
        pedidos.add(pedido);
        System.out.println("\u001B[32mPedido feito com sucesso\u001B[0m");
    }

    private static void finalizarPedido() {
        int countEmAndamento = 0;
        for (Pedido pedidoEncerrado : pedidos) {
            if (pedidoEncerrado.getEstado().equals("Em andamento")) {
                countEmAndamento++;
            }
        }

        if (countEmAndamento == 0) {
            System.out.println("Não há pedidos em andamento");
            return;
        }

        boolean abt = true;

        while (abt) {
            int count = 1;
            for (Pedido pedidoAberto : pedidos) {
                if (pedidoAberto.getEstado().equals("Encerrado")) {
                    continue;
                }

                System.out.println(count + " - " + pedidoAberto.getSabor() + " Cliente  - " + pedidoAberto.getCliente().getNome());
                count++;
            }

            System.out.println("Digite o número do pedido que deseja encerrar: ");
            int numPedido = scan.nextInt();
            int index = numPedido - 1;

            if (index >= 0 && index < pedidos.size() && pedidos.get(index).getEstado().equals("Em andamento")) {
                pedidos.get(index).setEstado("Encerrado");
                abt = false;
                System.out.println("\u001B[32mPedido encerrado com sucesso\u001B[0m");
            } else {
                System.out.println("Número inválido");
            }
        }
    }

    private static void emitirRecibo() {
        // Caminho do arquivo que será criado
        String caminhoArquivo = "recibo.txt";

        try {
            // Criar um BufferedWriter para escrever no arquivo
            BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo));

            int count = 1;
            for (Pedido pedidoPrint: pedidos) {

                if (pedidoPrint.getEstado().equals("Encerrado")) {

                    if (count != 1) {
                        writer.write("\n\n--------------------------------------------------------------------\n\n");
                    }

                    writer.write("Pedido " + count);
                    writer.write("\n\nSabor: " + pedidoPrint.getSabor());
                    writer.write("\n\nCliente: " + pedidoPrint.getCliente().getNome());
                    writer.write("\nEndereço\n\tRua : " + pedidoPrint.getCliente().getEndereco().getRua() + "\n\tNumero: " + pedidoPrint.getCliente().getEndereco().getNumero());

                    count++;
                }
            }

            // Fechar o BufferedWriter
            writer.close();

            System.out.println("Arquivo criado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }
}

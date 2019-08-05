/*
 * Batalha Naval - versão simples para Fundamentos de Programação
 * Daniel Callegari 2017
 */
package batalhanaval;

import java.util.Scanner;

public class BatalhaNaval {
    private static final int MAXTAB = 8;            // Tamanho do tabuleiro NxN
    private static final int MAXVIDAS = 15;         // Vidas do jogador
    private static int vidasRestantes = MAXVIDAS;   // Vidas restantes
    private static int totalPartes = 0;             // Partes de embarcações
    private static int totalPartesAcertadas = 0;    // Quantas partes afundadas
    private static final char[][] tabuleiroEscondido = new char[MAXTAB][MAXTAB];
    private static final char[][] tabuleiroJogador = new char[MAXTAB][MAXTAB];
    private static Scanner teclado = new Scanner(System.in);
    private static String mensagem = "Começou a batalha!";
    
    public static void main(String[] args) {
        inicializa();
        
        while (vidasRestantes > 0 && totalPartesAcertadas < totalPartes) {
            exibeTabuleiro();
            processaJogada();
        }
        
        finaliza();        
    }

    private static void inicializa() {
        // Cria strings temporárias para facilitar a definição do tabuleiro
        // ~ - água
        // 1 - lancha       (apenas 1 parte)
        // 2 - barco        (contém 2 partes)
        // 3 - navio        (contém 3 partes)
        // 4 - porta-aviões (contém 4 partes)
        
        String[] tempTab = new String[MAXTAB];
        //           |01234567| Monte o tabuleiro escondido!
        tempTab[0] = "~~~~~1~~";
        tempTab[1] = "~~3~~~~~";
        tempTab[2] = "~~3~~~~~";
        tempTab[3] = "~~3~~~22";
        tempTab[4] = "~~~~~~~~";
        tempTab[5] = "~~~4444~";
        tempTab[6] = "~~~~~~~~";
        tempTab[7] = "~~~~~~~~";

        // Guarda o tabuleiro na matriz, a partir das Strings
        for (int linha = 0; linha < MAXTAB; linha++) {
            for (int coluna = 0; coluna < MAXTAB; coluna++) {
                char parte =  tempTab[linha].charAt(coluna);
                
                if (parte != '~') {   // Se não for água,
                    totalPartes++;    // conta a parte
                }
                
                tabuleiroEscondido[linha][coluna] = parte;
                tabuleiroJogador[linha][coluna] = '.';
            }
        }
        
    }

    private static void exibeTabuleiro() {
        System.out.print("\f"); // Limpa a tela
        System.out.println();
        System.out.println("===================");
        System.out.println("   BATALHA NAVAL   ");
        System.out.println("===================");
        
        // Mostra números das colunas
        System.out.print("  ");
        for (int c = 0; c < MAXTAB; c++) {
            System.out.print(" " + (c+1));
        }
        System.out.println();
        
        // Mostra cada linha do tabuleiro do jogador, com um número na frente
        for (int l = 0; l < MAXTAB; l++) {
            System.out.print(" " + (l+1));
            for (int c = 0; c < MAXTAB; c++) {
                System.out.print(" " + tabuleiroJogador[l][c]);
            }
            System.out.println();
        }
        
        // Mostra mensagens
        System.out.println();  
        System.out.println(mensagem);
        System.out.printf("Pontos: %d de %d\n", totalPartesAcertadas, totalPartes);
        System.out.println("Vidas restantes: " + vidasRestantes);
    }

    private static void processaJogada() {
        System.out.println("Faça sua jogada!");
        
        int linha  = obtemValor("  Linha : ");
        int coluna = obtemValor("  Coluna: ");
        
        if (tabuleiroJogador[linha-1][coluna-1] != '.') {
            mensagem = "Posição já revelada. Refaça a sua jogada.";
            return;
            
        }
        
        // Copia o que estiver no tabuleiro escondido
        // para o tabuleiro do jogador
        tabuleiroJogador[linha-1][coluna-1] = tabuleiroEscondido[linha-1][coluna-1];
        
        // Verifica o que há na posição
        switch (tabuleiroEscondido[linha-1][coluna-1]) {
            case '~':
                mensagem = "AGUA!";
                vidasRestantes--;
                break;

            case '1':
                mensagem = "BOOM! Acertou uma lancha!";
                totalPartesAcertadas++;
                break;

            case '2':
                mensagem = "BOOM! Acertou parte de um barco!";
                totalPartesAcertadas++;
                break;

            case '3':
                mensagem = "BOOM! Acertou parte de um navio!";
                totalPartesAcertadas++;
                break;

            case '4':
                mensagem = "BOOM! Acertou parte de um porta-aviões!";
                totalPartesAcertadas++;
                break;

            default:
                mensagem = "* BUG NO PROGRAMA *";
                vidasRestantes = 0;
        }
    }

    private static void finaliza() {
        exibeTabuleiro();
        System.out.println("Fim da batalha!");
        
        if (vidasRestantes > 0) {
            System.out.println("Você venceu!");
        } else {
            System.out.println("Você perdeu...");
        }
        
        System.out.println("===================");
    }

    private static int obtemValor(String strValor) {
        int valorUsuario = -1;
        
        // Garante que o usuário entra com um valor
        // válido entre 1 e MAXTAB.
        do {
            System.out.print(strValor);
            valorUsuario = teclado.nextInt();

            if (valorUsuario < 1 || valorUsuario > MAXTAB) {
                System.out.println("  Valor inválido!");
            }
        } while (valorUsuario < 1 || valorUsuario > MAXTAB);
        
        return valorUsuario;
    }
    
}

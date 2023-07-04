import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {

        try (Scanner input = new Scanner(System.in)) {
            
            // Lendo números providos pelo usuário
            int n1, n2, n3, n4;
            n1 = input.nextInt();
            n2 = input.nextInt();
            n3 = input.nextInt();
            n4 = input.nextInt();

            // Adquirindo path para o arquivo a ser lido
            String file_name = "dados.csv";
            Path file_path = Paths.get(file_name); 

            // Construindo lista de objetos 'Staat' (== País)
            List <Staat> paises = new ArrayList<>();
            /* Utilização de um Stream e função lambda que a cada linha do arquivo, 
             * constrói um objeto 'Staat' e adiciona na lista de países
             */
            Files.lines(file_path)
                .forEach( p -> { paises.add( new Staat(p) ); } );


            /* Etapa 1 */
            System.out.println(
            paises
                .stream()
                .filter(p -> p.getConfirmed() > n1)
                .mapToInt(p -> p.getActive())
                .sum()
            );


            /* Etapa 2 */
            paises
                .stream()
                .sorted(Comparator.comparing(Staat::getActive).reversed())
                .limit(n2)
                .sorted(Comparator.comparing(Staat::getConfirmed))
                .limit(n3)
                .map(p -> p.getDeaths())
                .forEach(System.out::println);
                
                
            /* Etapa 3 */
            paises
                .stream()
                .sorted(Comparator.comparing(Staat::getConfirmed).reversed())
                .limit(n4)
                .map(p -> p.getName())
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
                
        }
    }
}

class Staat {
    
    /* Variaveis de instância correspondente a cada campo do .csv, sendo eles,
     * em ordem: Country,Confirmed,Deaths,Recovery,Active
     */
    
    private final String name;
    private final int confirmed;
    private final int deaths;
    private final int recovery;
    private final int active;
    

    /* Construtor padrão */
    
    public Staat(String line) {
        /* Recebe uma linha, distribui os campos separados por vírgulas em um 
         * vetor de Strings
         */
        String[] line_parts = line.split(",");

        // Atribui cada cada para as respectivas variáveis de instância
        this.name = line_parts[0];
        this.confirmed = Integer.parseInt(line_parts[1]);
        this.deaths = Integer.parseInt(line_parts[2]);
        this.recovery = Integer.parseInt(line_parts[3]);
        this.active = Integer.parseInt(line_parts[4]);
    }


    /* Getters para acessar variáveis de instância */

    public String getName() {
        return this.name;
    }

    public int getConfirmed() {
        return this.confirmed;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getRecovery() {
        return this.recovery;
    }

    public int getActive() {
        return this.active;
    }


    /* Representação em string */

    @Override
    public String toString() {
        return String.format("(Name: %s | Confirmed: %d | Deaths: %d | Recovery: %d | Active: %d)"
        , getName(), getConfirmed(), getDeaths(), getRecovery(), getActive());
    }
}

import java.io.*;
import java.util.Scanner;


public class Hondt {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {


        //Definició de variables
        //Variables doc 07
        int[] vots = new int[72];
        int[] votsBlanc = new int[72];
        int[] votsNuls = new int[72];
        int[] codiProvincia07 = new int[72];
        String[] nomTerritori = new String[73];
        int[] nEscons = new int[72];
        //Variables doc 08
        int[] codiProvincia08 = new int[957];
        int[] codiCandidatura08 = new int[957];
        int[] votsCandidatura = new int[957];
        //Variables doc 03
        String[] nomCandidatura = new String[106];
        int[] codiCandidatura03 = new int[106];
        //Variables càlculs
        String provincia;
        int index07 = 73;
        int[] votsCandidaturaProvincia = new int[957];
        int[] votsHondt = new int[957];
        int[] nHondt = new int[957];
        int[] codiCandidaturaHondt = new int[957];
        int esconsProv;
        int votsProv;
        int[] esconsCandidatura = new int[957];
        int recomptePartitsEscons = 0;


        //Lectura de fitxer 07
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/07021911.DAT"), "ISO-8859-1"));
            String line = null;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                String votsSubstring = line.substring(141, 149);
                int votsValue = Integer.parseInt(votsSubstring);
                vots[index] = votsValue;

                String votsBlancSubstring = line.substring(125, 133);
                int votsBlancValue = Integer.parseInt(votsBlancSubstring);
                votsBlanc[index] = votsBlancValue;

                String votsNulsSubstring = line.substring(133, 141);
                int votsNulsValue = Integer.parseInt(votsNulsSubstring);
                votsNuls[index] = votsNulsValue;

                String codiProvinciaSubstring = line.substring(11, 13);
                int codiProvinciaValue = Integer.parseInt(codiProvinciaSubstring);
                codiProvincia07[index] = codiProvinciaValue;

                String nomTerritoriSubstring = line.substring(14, 64);
                nomTerritori[index] = nomTerritoriSubstring.trim();
                for (int i = 0; i < nomTerritori[index].length(); i++) {
                    if (nomTerritori[index].charAt(i) == ' ' && i + 1 < nomTerritori[index].length() && nomTerritori[index].charAt(i + 1) == '/') {
                        nomTerritori[index] = nomTerritori[index].substring(0, i);
                        nomTerritori[index] = nomTerritori[index].trim();
                        break;
                    }
                }

                String nEsconsSubstring = line.substring(149, 155);
                int nEsconsValue = Integer.parseInt(nEsconsSubstring);
                nEscons[index] = nEsconsValue;

                index++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Lectura de fitxer 08
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/08021911.DAT"), "ISO-8859-1"));
            String line = null;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                String codiProvincia2Substring = line.substring(11, 13);
                int codiProvincia2Value = Integer.parseInt(codiProvincia2Substring);
                codiProvincia08[index] = codiProvincia2Value;

                String votsCandidaturaSubstring = line.substring(20, 28);
                int votsCandidaturaValue = Integer.parseInt(votsCandidaturaSubstring);
                votsCandidatura[index] = votsCandidaturaValue;

                String codiCandidaturaSubstring = line.substring(14, 20);
                int codiCandidaturaValue = Integer.parseInt(codiCandidaturaSubstring);
                codiCandidatura08[index] = codiCandidaturaValue;

                index++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Lectura fitxer 03
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/03021911.DAT"), "ISO-8859-1"));
            String line = null;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                String nomCSubstring = line.substring(14, 64);
                nomCandidatura[index] = nomCSubstring;

                String codiCandidaturaSubstring = line.substring(8, 14);
                int codiCandidaturaValue = Integer.parseInt(codiCandidaturaSubstring);
                codiCandidatura03[index] = codiCandidaturaValue;

                index++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Mostrar per pantalla enunciat i gestió d'erros
        System.out.println("Introdueix el nom de la provincia");
        provincia = scan.nextLine();
        try {


            for (int i = 0; i < nomTerritori.length; i++) {
                if (nomTerritori[i].equalsIgnoreCase(provincia)) {
                    index07 = i;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Hi ha hagut un problema amb el nom");
        }
        if (index07 == 73) {
            System.out.println("No s'ha trobat el nom de la provincia indicada");
            return;
        }

        //Guardar dades de la provincia
        votsProv = vots[index07];
        esconsProv = nEscons[index07];

        for (int i = 0; i < codiProvincia08.length; i++) {
            if (codiProvincia07[index07] == codiProvincia08[i]) {
                codiCandidaturaHondt[i] = codiCandidatura08[i];
                votsCandidaturaProvincia[i] = votsCandidatura[i];
            }
        }
        if (codiProvincia07[index07] == 99) {
            System.out.println("Hi ha hagut un error amb la provincia escullida");
            return;
        }

        //Aplicació de la llei d'Hondt
        for (int i = 0; i < esconsProv; i++) {
            int maxIndex = -1;
            int maxVots = 0;
            for (int j = 0; j < votsCandidaturaProvincia.length; j++) {
                if (maxVots < votsCandidaturaProvincia[j] && votsCandidaturaProvincia[j] > (3 / 100 * votsProv)) {
                    maxVots = votsCandidaturaProvincia[j];
                    maxIndex = j;
                }
            }
            esconsCandidatura[maxIndex] += 1;

            if (nHondt[maxIndex] == 0) {
                votsHondt[maxIndex] = votsCandidaturaProvincia[maxIndex];
                nHondt[maxIndex] = 2;
                votsCandidaturaProvincia[maxIndex] /= 2;
                recomptePartitsEscons++;
            } else {
                nHondt[maxIndex] += 1;
                votsCandidaturaProvincia[maxIndex] = votsHondt[maxIndex] / nHondt[maxIndex];
            }

        }
        //Mostrar dades solicitades per pantalla
        System.out.println("Provincia " + nomTerritori[index07]);
        System.out.println("-----------------------------");
        System.out.println("Vots: " + votsProv);
        System.out.println("Vots blanc: " + votsBlanc[index07]);
        System.out.println("Vots nuls: " + votsNuls[index07]);
        for (int i = 0; i < recomptePartitsEscons; i++) {
            int maxEscons = 0;
            int maxIndex = 0;
            for (int j = 0; j < esconsCandidatura.length; j++) {
                if (maxEscons < esconsCandidatura[j]) {
                    maxEscons = esconsCandidatura[j];
                    maxIndex = j;
                }
            }
            for (int k = 0; k < codiCandidatura03.length; k++) {
                if (codiCandidatura08[maxIndex] == codiCandidatura03[k]) {
                    System.out.println(nomCandidatura[k] + ": " + esconsCandidatura[maxIndex]);
                    esconsCandidatura[maxIndex] = 0;
                    break;
                }
            }
        }
    }

}






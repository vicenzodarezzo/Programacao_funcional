
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
// 1 : DATA CLASS :
/*
 * Stores the information of a country, being able to access and modify they according to the
 * given public methods.
 * This class have 3 static methods that implements the functionality for the given situation.
 */
// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
class Country {

    // -> ATTRIBUTES
    String name;
    int[] data_list;

    // -> STANDARD METHODS
    public Country(String name, List<String> read_data) {
        this.name = name;
        int counter = 0;
        this.data_list = new int[4];
        for (String number : read_data) {
            data_list[counter++] = Integer.parseInt(number);
        }

    }

    public Country(String name, int[] read_data) {
        this.name = name;
        int counter = 0;
        this.data_list = new int[4];
        for (int number : read_data) {
            data_list[counter++] = number;
        }

    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", data_list=" + Arrays.toString(data_list) +
                '}';
    }

    // -> ACCESS METHODS :
    public String get_name() {
        return name;
    }

    public int[] get_data_list() {
        return data_list;
    }

    public int get_confirmed_value() {
        return data_list[0];
    }

    public int get_deaths_value() {
        return data_list[1];
    }

    public int get_active_value() {
        return data_list[3];
    }

    // -> MODIFY METHODS :
    public void set_name(String s) {
        this.name = s;
    }

    public void set_data_list(int[] list) {
        this.data_list = list;
    }


    // -> SORTING METHODS

    /*
     * A bubble-sort implementation for sorting a Country list according to the
     * descending order of the active value ;
     */
    public static void sorting_byActive(List<Country> countries) {

        Country temp;

        for (int i = 0; i < countries.size() - 1; i++) {
            for (int j = 0; j < countries.size() - i - 1; j++) {
                if (countries.get(j).get_active_value() < countries.get(j + 1).get_active_value()) {

                    temp = new Country(countries.get(j).get_name(), countries.get(j).get_data_list());

                    countries.get(j).set_name(countries.get(j + 1).get_name());
                    countries.get(j).set_data_list(countries.get(j + 1).get_data_list());

                    countries.get(j + 1).set_name(temp.get_name());
                    countries.get(j + 1).set_data_list(temp.get_data_list());

                }
            }
        }

    }

    /*
     * A bubble-sort implementation for sorting a Country list according to the
     * confirmed value. The direction of the sorting will be determinate by the flag
     * passed as a parameter, that indicates lowest to highest if it is true and the
     * opposite if it is false.
     */
    public static void sorting_byConfirmed(List<Country> countries, boolean ascending_flag) {

        Country temp;

        for (int i = 0; i < countries.size() - 1; i++) {
            for (int j = 0; j < countries.size() - (i + 1); j++) {
                if (ascending_flag && (countries.get(j).get_confirmed_value() > countries.get(j + 1).get_confirmed_value())) {
                    temp = new Country(countries.get(j).get_name(), countries.get(j).get_data_list());

                    countries.get(j).set_name(countries.get(j + 1).get_name());
                    countries.get(j).set_data_list(countries.get(j + 1).get_data_list());

                    countries.get(j + 1).set_name(temp.get_name());
                    countries.get(j + 1).set_data_list(temp.get_data_list());

                } else if (!ascending_flag && (countries.get(j).get_confirmed_value() < countries.get(j + 1).get_confirmed_value())) {
                    temp = new Country(countries.get(j).get_name(), countries.get(j).get_data_list());

                    countries.get(j).set_name(countries.get(j + 1).get_name());
                    countries.get(j).set_data_list(countries.get(j + 1).get_data_list());

                    countries.get(j + 1).set_name(temp.get_name());
                    countries.get(j + 1).set_data_list(temp.get_data_list());

                }
            }
        }

    }

    /*
     * A bubble-sort implementation for sorting a Country list according to the
     * descending order of alphabetic order of the names ;
     */
    public static void sorting_byName(List<Country> countries) {

        Country temp;

        for (int i = 0; i < countries.size() - 1; i++) {
            for (int j = 0; j < countries.size() - (i + 1); j++) {
                if (countries.get(j).get_name().compareTo(countries.get(j + 1).get_name()) > 0) {

                    temp = new Country(countries.get(j).get_name(), countries.get(j).get_data_list());

                    countries.get(j).set_name(countries.get(j + 1).get_name());
                    countries.get(j).set_data_list(countries.get(j + 1).get_data_list());

                    countries.get(j + 1).set_name(temp.get_name());
                    countries.get(j + 1).set_data_list(temp.get_data_list());
                }
            }
        }

    }

    // -> OPERATION METHODS

    /*
     * Returns the sum of the active value of each country
     * whose confirmed value is greater than the parameter;
     */
    public static int Confirmed_sum(List<Country> countries, int parameter) {
        int sum = 0;

        for (Country c : countries) {
            if (c.get_confirmed_value() > parameter) {
                sum = sum + c.get_active_value();
            }
        }
        return sum;
    }

    /*
     * Show the death value of a specified list of countries based in their active and confirmed
     * data and how they are related to the parametrized ones ;
     */
    public static void deaths_show(List<Country> countries, int sorting_length, int showing_length) {

        List<Country> selected_list;
        // The first step is sorting the countries list according to the Active
        // value, organizing the list from the greatest to the smallest ;

        sorting_byActive(countries);

        // Now we have to access the sublist of the countries, indicated by the first and the
        // passed index ;
        selected_list = countries.subList(0, sorting_length);

        // Having the selected list, we re-sort it according to the confirmed value ;
        sorting_byConfirmed(selected_list, true);

        // Now, for each of the sub list determinate by the parameter, we print the death value
        if(showing_length > sorting_length){
            for (Country c : selected_list.subList(0, sorting_length)) {
                System.out.println(c.get_deaths_value());
            }
        }else{
            for (Country c : selected_list.subList(0, showing_length)) {
                System.out.println(c.get_deaths_value());
            }
        }

    }

    /*
     * Returns a list of countries in alphabetic order of names according to a previous
     * descending sorting by the confirmed value ;
     */
    public static List<Country> highest_confirmed(List<Country> countries, int sorting_length) {

        List<Country> selected_list;
        // the first thing to do is sorting the countries list according to
        // the descending order of the confirmed value;
        sorting_byConfirmed(countries, false);

        // having the sorted list, we select the sublist according to the limit passed
        // as a parameter
        selected_list = countries.subList(0, sorting_length);

        //Now, we have to re-sort it according to the alphanumeric order od the country names;
        sorting_byName(selected_list);

        return selected_list;
    }
}

// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------

// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
// INPUT READING CLASS :
/*
 * Will deal of the transition of data between our implemented structures in the main memory
 * and the I/O modules - CSV file and standard IO .
 */
// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
class Input_reading {

    /*
     * Read the crime list of a csv file whose cells are separated by "," .
     * This function may throw an IOException indicating an error in the
     * read data ;
     */
    public static List<Country> table_readerFromFile(String file_path) throws  IOException{
        // creating the IO interface objects ;
        FileReader file = new FileReader(file_path);
        BufferedReader buffer = new BufferedReader(file);
        List <Country> countries = new ArrayList<>();

        while(true){
            // reading loop for each line in the CSV file;
            String read_line = buffer.readLine();
            List<String> data_cells;

            if(read_line == null) break ; //EOF

            else{
                data_cells = List.of(read_line.split(","));
                countries.add(new Country(data_cells.get(0), data_cells.subList(1, 5)));
            }
        }
        file.close();
        buffer.close();
        return countries;
    }

    /*
     * Read an integer list of the given parameters from the standard
     * input through a read-line. As the above, this may throw an
     * IOException indicating an error in the read data ;
     */
    public static List<Integer> parameters_reader() throws IOException{
        Scanner scanner = new Scanner(System.in);
        String read_line = scanner.nextLine();
        String[] str_list = read_line.split(" ");
        List<Integer> parameters = new ArrayList<>();

        for (String s : str_list){
            parameters.add(Integer.parseInt(s));
        }

        scanner.close();
        return parameters;
    }

}

// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------


class Main {
    public static void main(String[] args) {

        List<Country> countries;
        List<Integer> parameters;

        try{
            // reading the information through the input_reading class.
            countries = Input_reading.table_readerFromFile("dados.csv");
            parameters = Input_reading.parameters_reader();

            // showing the results of the 3 implemented functionalities in the Country class
            System.out.println(Country.Confirmed_sum(countries, parameters.get(0)));
            Country.deaths_show(countries, parameters.get(1) , parameters.get(2));
            for(Country c : Country.highest_confirmed(countries, parameters.get(3))){
                System.out.println(c.get_name());
            }


        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

    }
}
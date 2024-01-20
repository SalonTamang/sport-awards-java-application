
package task1;

import java.io.File;
import java.io.FileNotFoundException;
/*
 * @author salon ghalan tamang
 */
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ApplicationRunner {

    // to print the main menu of the program

    private static void printMenu() {
        System.out.println("\n\n----------------------");
        System.out.println("Sports award menu");
        System.out.println("----------------------");
        System.out.println("List ................1");
        System.out.println("Select ..............2");
        System.out.println("Sort ................3");
        System.out.println("Exit.................0");
        System.out.println("----------------------");
        System.out.print("Enter choice > ");
    }
    
    public static class SportsAward {
        private int year;
        private IndividualSportsAward winner;
        private IndividualSportsAward secondPlace;
        private IndividualSportsAward thirdPlace;
        private TeamSportsAward teamWinner;

        public SportsAward(int year, IndividualSportsAward winner,
                            IndividualSportsAward secondPlace, IndividualSportsAward thirdPlace,
                            TeamSportsAward teamWinner) {
            this.year = year;
            this.winner = winner;
            this.secondPlace = secondPlace;
            this.thirdPlace = thirdPlace;
            this.teamWinner = teamWinner;
        }

        public int getYear() {
            return year;
        }

        public IndividualSportsAward getWinner() {
            return winner;
        }

        public IndividualSportsAward getSecondPlace() {
            return secondPlace;
        }

        public IndividualSportsAward getThirdPlace() {
            return thirdPlace;
        }

        public TeamSportsAward getTeamWinner() {
            return teamWinner;
        }

        //bulding string with stringbuilder and returing as result with toString() method.
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append(" -----------------------------------------------------------------------------------------------------------------\n");
            result.append("|                           Individual Sports Person                           |               Team              |\n");
            result.append(" -----------------------------------------------------------------------------------------------------------------\n");
            String formatter = "| %-10s %-66s %-30s |%n";
            String winnerResult = String.format(formatter, "Winner" , winner, this.getTeamWinner().getName());
            String secondResult = String.format(formatter, "Second" , secondPlace , this.getTeamWinner().getCaptain());
            String thirdResult = String.format(formatter,"Third", thirdPlace, this.getTeamWinner().getHomeNation());
            result.append(winnerResult);
            result.append(secondResult);
            result.append(thirdResult);
            result.append(" -----------------------------------------------------------------------------------------------------------------\n");
            return result.toString();
        }

    }

    //class for individaul award with name, nation and sport
    public static class IndividualSportsAward {
        private String name;
        private String homeNation;
        private String sport;

        public IndividualSportsAward(String name, String homeNation, String sport) {
            this.name = name;
            this.homeNation = homeNation;
            this.sport = sport;
        }

        public String getName() {
            return name;
        }

        public String getHomeNation() {
            return homeNation;
        }

        public String getSport() {
            return sport;
        }

        @Override
        public String toString() {
            String leftalignFormat = "| %-25s | %-17s | %-15s | ";
            String result =  String.format(leftalignFormat, name, sport, homeNation);
            return result;
        }
    }

    //class for team awards with team name, nation, sport and caption
    public static class TeamSportsAward {
        private String name;
        private String homeNation;
        private String sport;
        private String captain;

        public TeamSportsAward(String name, String homeNation, String sport, String captain) {
            this.name = name;
            this.homeNation = homeNation;
            this.sport = sport;
            this.captain = captain;
        }

        public String getName() {
            return name;
        }

        public String getHomeNation() {
            return homeNation;
        }

        public String getSport() {
            return sport;
        }

        public String getCaptain() {
            return captain;
        }

        @Override
        public String toString() {
            String leftalignFormat = " %-25s %n %-25s %n %-25s ";
            String result =  String.format(leftalignFormat, name, sport, captain, homeNation);
            return result;
            
        }
    }

    //function that takes sport year as user input and puts that years' award details to be displayed on console using SportAward.toString();
    private static void selectSportsAward(List<SportsAward> sportsAwards) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the year of the sports award: ");
        int year = scanner.nextInt();
        SportsAward requestAward = null;
        for (SportsAward award : sportsAwards) {
            if (award.getYear() == year) {
                requestAward = award;
                break;
            }
        }
        //if no year is entered
        if (requestAward == null) {
            System.out.println("No sports award found for the year " + year);
        } else {
            System.out.println(requestAward.toString());
        }
    }


    //listing sport awards method using year and their winner
    private static void listSportsAwards(List<SportsAward> sportsAwards) {
        //string formatter for output
        String formatter = "| %-10s | %-25s | %-35s |%n";
        System.out.println(" ------------------------------------------------------------------------------");
        System.out.format(formatter, "Year", "Individual Award", "Team Award");
        System.out.println(" ------------------------------------------------------------------------------");

        for (SportsAward sportsAward : sportsAwards) {
            System.out.format(formatter, sportsAward.getYear(),sportsAward.getWinner().getName(),sportsAward.getTeamWinner().getName());
        }
        System.out.println(" ------------------------------------------------------------------------------");
    }


    //loading files as list from the file named "sports-personalities.txt"
    private static List<SportsAward> loadSportsAwardsFromFile() {
        List<SportsAward> sportsAwards = new ArrayList<>();
         
        String fileLocation = System.getProperty("user.dir");
        String dataPath = fileLocation + File.separator + "Task1" + File.separator + "sports-personalities.txt";

        try  {
            File myFile = new File(dataPath);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                //getting every line of the text file
                String line = myReader.nextLine();
                //splitting by "|"
                String[] fields = line.split("\\|");
                int year = Integer.parseInt(fields[0]);
                IndividualSportsAward winner = parseIndividualSportsPersonality(fields[1]);
                IndividualSportsAward secondPlace = parseIndividualSportsPersonality(fields[2]);
                IndividualSportsAward thirdPlace = parseIndividualSportsPersonality(fields[3]);
                TeamSportsAward teamWinner = parseTeamSportsPersonality(fields[4]);
                sportsAwards.add(new SportsAward(year, winner, secondPlace, thirdPlace, teamWinner));
            }
            //if file is not found
        } catch (FileNotFoundException e) {
            System.out.println("File not found: sports-personalities.txt on the path you specified");
        }
        return sportsAwards;
    }
    
   //more spliting for individual awards(winner, second and third) which destinguishes their name, nation and sport 
    private static IndividualSportsAward parseIndividualSportsPersonality(String field) {
        String[] parts = field.trim().split(",");
        String[] firstPart = parts[0].trim().split("\\(");
        String name = firstPart[0].trim();
        String homeNation = firstPart[1].substring(0, firstPart[1].length() - 1);
        String sport = parts[1].trim();
        return new IndividualSportsAward(name, homeNation, sport);
    }
    
    //splitting the last file of line for getting team name, nation, sport and captain and passing these details to teamSportAward class.
    private static TeamSportsAward parseTeamSportsPersonality(String field) {
        String[] parts = field.trim().split(",");
        String[] firstPart = parts[0].trim().split("\\(");
        String teamName = firstPart[0].trim();
        String homeNation = firstPart[1].substring(0, firstPart[1].length() - 1);
        String sport = parts[1].trim();
        String captain = parts[2].trim();
        return new TeamSportsAward(teamName, homeNation, sport, captain);
    }
    //sort menu
    private static void sortMenu(){
        System.out.println("\n\n-------------------------------------");
        System.out.println("Sort options");
        System.out.println("-------------------------------------");
        System.out.println("Sort awards won by sport............1");
        System.out.println("Sort awards won by sports person....2");
        System.out.println("Back to main menu...................0");
        System.out.println("-------------------------------------");
        System.out.print("Enter option > ");
    }

    //sorting method that takes sport award year as user input and sorts list in ascending or descending order
    private static void sortSportsAwards(List<SportsAward> sportsAwards) {

        //prompts the user to input what to do
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            //display sort menu
            sortMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    sortBySport(sportsAwards);
                    break;
                case 2:
                    sortBySportPerson(sportsAwards);
                    break;
                case 0:
                    System.out.println("Going back to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    break;
            }
        }

    }
    
    //sort by sport 
    private static void sortBySport(List<SportsAward> sportsAwards){
                
        //creating a hash set to store unique sport 
        Set<String> sportSet = new HashSet<String> (); 

        //looping over individual sport awards to get each sport name and putting it in a new set
        for (SportsAward Award : sportsAwards){    
            sportSet.add(Award.getWinner().getSport());
        }

        // Converting HashSet to Array
        String[] sports = sportSet.toArray(new String[sportSet.size()]);

        //hashMap for firstCount, second count, third count and total
        Map<String,Integer> firstPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> secondPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> thirdPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> totalCount = new HashMap<String,Integer>();

         //for second and third count if first count is same
         LinkedHashMap<String, Integer> secondMap = new LinkedHashMap<String,Integer>();
         LinkedHashMap<String, Integer> thirdMap = new LinkedHashMap<String,Integer>();

        //displaying output
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        //string formatter for output
        String formatter = "| %-25s | %-15s | %-15s | %-15s | %-15s |%n";
        System.out.format(formatter, "Sport", "1st Place(s)", "2nd Place(s)", "3rd Place(s)", "Total"); 
        System.out.println(" ---------------------------------------------------------------------------------------------------");

        //for loop to store first,second and third data
        for (int i = 0; i < sports.length; i++){
            String sport = sports[i];
            int firstCount, secondCount, thirdCount, total;
            firstCount = secondCount = thirdCount = total = 0;
            for (SportsAward Award: sportsAwards){
                //if element match the set item in getWinner
                if (sport.equals(Award.getWinner().getSport())) {
                    //first place data
                    firstCount = firstCount +  1;
                }
                if(sport.equals(Award.getSecondPlace().getSport())){
                    //second place data
                    secondCount = secondCount + 1;
                }
                if(sport.equals(Award.getThirdPlace().getSport())){
                    //third place data
                    thirdCount = thirdCount + 1;
                }
                total = firstCount + secondCount + thirdCount;
            }

            //putting the data into respective maps using sport name as key
            firstPlaceCount.put(sport,firstCount);
            secondPlaceCount.put(sport,secondCount);
            thirdPlaceCount.put(sport,thirdCount);
            totalCount.put(sport,total);

        }

        LinkedHashMap<String, Integer> sortedMap = firstPlaceCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        
        //second place count if first place are same
        for (int i = 0; i < sports.length - 1; i++) {
            if(firstPlaceCount.get(sports[i]) == firstPlaceCount.get(sports[i + 1])){
                secondMap = secondPlaceCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
        }
        //third place count 1st and 2rd cout are same
        for (int i = 0; i < sports.length -1; i++) { 
            int a = i + 1;
            if(sortedMap.get(sports[i]).equals(sortedMap.get(sports[a])) && secondMap.get(sports[i]).equals(secondMap.get(sports[a]))){
                thirdMap = thirdPlaceCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
        }

        for (Map.Entry<String,Integer> entry : sortedMap.entrySet()){
           
            if(secondMap.size() != 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdPlaceCount.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
            
            if(thirdMap.size() != 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdMap.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
            
            if(secondMap.size() == 0 && thirdMap.size() == 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdPlaceCount.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
    
        }
                
              
        //displaying output
        System.out.println(" ---------------------------------------------------------------------------------------------------");
    } 

    private static void sortBySportPerson(List<SportsAward> sportsAwards){
        //sort by sport person
        //creating a hash set to store unique sport 
        Set<String> sportSet = new HashSet<String> (); 

        //looping over individual sport awards to get each sport name and putting it in a new set
        for (SportsAward Award : sportsAwards){    
            sportSet.add(Award.getWinner().getName());
        }

        // Converting HashSet to Array
        String[] sports = sportSet.toArray(new String[sportSet.size()]);

        //hashMap for firstCount, second count, third count and total
        Map<String,Integer> firstPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> secondPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> thirdPlaceCount = new HashMap<String,Integer>();
        Map<String,Integer> totalCount = new HashMap<String,Integer>();

        //for second and third count if first count is same
        LinkedHashMap<String, Integer> secondMap = new LinkedHashMap<String,Integer>();
        LinkedHashMap<String, Integer> thirdMap = new LinkedHashMap<String,Integer>();

        //displaying output
        System.out.println(" ---------------------------------------------------------------------------------------------------");
        //string formatter for output
        String formatter = "| %-25s | %-15s | %-15s | %-15s | %-15s |%n";
        System.out.format(formatter, "Sport", "1st Place(s)", "2nd Place(s)", "3rd Place(s)", "Total"); 
        System.out.println(" ---------------------------------------------------------------------------------------------------");

        //for loop to store first,second and third data
        for (int i = 0; i < sports.length; i++){
            String sport = sports[i];
            int firstCount, secondCount, thirdCount, total;
            firstCount = secondCount = thirdCount = total = 0;
            for (SportsAward Award: sportsAwards){
                //if element match the set item in getWinner
                if (sport.equals(Award.getWinner().getName())) {
                    //first place data
                    firstCount = firstCount +  1;
                }
                if(sport.equals(Award.getSecondPlace().getName())){
                    //second place data
                    secondCount = secondCount + 1;
                }
                if(sport.equals(Award.getThirdPlace().getName())){
                    //third place data
                    thirdCount = thirdCount + 1;
                }
                total = firstCount + secondCount + thirdCount;
            }


            //putting the data into respective maps using sport name as key
            firstPlaceCount.put(sport,firstCount);
            secondPlaceCount.put(sport,secondCount);
            thirdPlaceCount.put(sport,thirdCount);
            totalCount.put(sport,total);

        }

        // arranging the map in descending order as per first place count 
        LinkedHashMap<String, Integer> sortedMap = firstPlaceCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
                
               

        //second place count if first place are same
        for (int i = 0; i < sports.length - 1; i++) {
            if(firstPlaceCount.get(sports[i]) == firstPlaceCount.get(sports[i + 1])){
                secondMap = secondPlaceCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
        }
        //third place count 1st and 2rd cout are same
        for (int i = 0; i < sports.length -1; i++) { 
            int a = i + 1;
            if(sortedMap.get(sports[i]).equals(sortedMap.get(sports[a])) && secondMap.get(sports[i]).equals(secondMap.get(sports[a]))){
                thirdMap = thirdPlaceCount.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            }
        }

        for (Map.Entry<String,Integer> entry : sortedMap.entrySet()){
           
            if(secondMap.size() != 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdPlaceCount.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
            
            if(thirdMap.size() != 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdMap.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
            
            if(secondMap.size() == 0 && thirdMap.size() == 0){
                System.out.format(formatter, entry.getKey(), sortedMap.get(entry.getKey()), secondMap.get(entry.getKey()),thirdPlaceCount.get(entry.getKey()),totalCount.get(entry.getKey()));
            }
    
        }

        //if firstplace count is equal
        
        //displaying output
        System.out.println(" ---------------------------------------------------------------------------------------------------");

    }
    
    //main method that runs for user interaction
    public static void main(String[] args) throws FileNotFoundException {
        List<SportsAward> sportsAwards = loadSportsAwardsFromFile();
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = -1;
            while (choice != 0) {
                //print the main menu
                printMenu();
                
                if(scanner.hasNextInt()){
                    choice = scanner.nextInt();
                }
                switch (choice) {
                    case 1:
                        listSportsAwards(sportsAwards);
                        break;
                    case 2:
                        selectSportsAward(sportsAwards);
                        break;
                    case 3:
                        sortSportsAwards(sportsAwards);
                        break;
                    case 0:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 3.");
                        break;
                }
            }
        }
    }

  
  
}


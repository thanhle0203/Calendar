/**
 * CS 151
 * Assignment #1
 * Class MyCalendarTester
 * @author Thanh Le
 * @version 1.0 2/16/21
 */
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//Class with a main method
public class MyCalendarTester {
    /**
     * Main program
     * @param args
     */
    public static void main(String[] args) {
        MyCalendar myCal = new MyCalendar();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        loadEvents(myCal, formatter);
        System.out.println("Loading is done!");
        //Print initial month calendar
        myCal.printMonthCalendar();

        LocalDate cal = LocalDate.now();    //set today date calendar

        Scanner sc = new Scanner(System.in); //get input Scanner object
        String option = " ";
        String input = " ";

        do {
            System.out.println("**************MAIN MENU**************");
            System.out.println("Enter a selection below: ");
            System.out.println("[V]iew by   [C]create   [G]o    [E]ent list     [D]elete    [Q]uit");
            option = sc.nextLine().toUpperCase();

            switch(option){
                case "V":
                    System.out.println("Choose a [D]ay or a [M]onth view");
                    String view = sc.nextLine().toUpperCase();
                    if (view.equals("D")){
                        System.out.println("Today's Date: ");
                        myCal.printDayCalendar();
                        do {
                            System.out.println("Enter [P] for the previous day, [N] for the next day, [G] for the go back to Menu");
                            input = sc.nextLine().toUpperCase();
                            if (input.equals("P")){
                                myCal.navigateDay(-1);
                                myCal.printDayCalendar();
                                myCal.printDayEventCalendar();
                            }else if (input.equals("N")) {
                                myCal.navigateDay(1);
                                myCal.printDayCalendar();
                                myCal.printDayEventCalendar();
                            }
                        } while(!input.equals("G"));
                    }else if (view.equals("M")){
                        myCal.printMonthCalendar();
                        myCal.printMonthEventCalendar();

                        do {
                            System.out.println("Enter [P] for the previous month, [N] for the next month, [G] for the go back to Menu");
                            input = sc.nextLine().toUpperCase();
                            if (input.equals("P")){
                                myCal.navigateMonth(-1);
                                myCal.printMonthCalendar();
                                myCal.printMonthEventCalendar();
                            }else if (input.equals("N")){
                                myCal.navigateMonth(1);
                                myCal.printMonthCalendar();
                                myCal.printMonthEventCalendar();
                            }
                        } while(!input.equals("G"));
                    }
                    break;

                case "C":
                    System.out.println("Enter a name of event: ");
                    String name = sc.nextLine();
                    try{
                        System.out.println("Enter Date in format (MM/DD/YYYY): ");
                        String inputDate = sc.nextLine();
                        formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                        LocalDate date = LocalDate.parse(inputDate, formatter);

                        try{
                            System.out.println("Enter Start time in 24 hour clock format(hh:mm): ");
                            String timeStartInput = sc.nextLine();
                            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");
                            LocalTime timeStart = LocalTime.parse(timeStartInput, formatterTime);

                            System.out.println("Enter Ending time in 24 hour clock format(hh:mm): ");
                            String timeEndInput = sc.nextLine();
                            LocalTime timeEnd = LocalTime.parse(timeEndInput, formatterTime);

                            myCal.setCalendar(date);
                            myCal.printDayEventCalendar();
                            myCal.addOneTimeEvent(new OneTimeEvent(name, date, timeStart, timeEnd));

                        }catch(IllegalArgumentException exp) {
                            System.out.println("Invalid Input Time" + exp.getMessage());
                        }

                    }catch(IllegalArgumentException exp) {
                        System.out.println("Invalid Input Date" + exp.getMessage());
                    }
                    break;

                case "G":
                    System.out.println("Enter Date in the format MM/dd/yyyy");
                    String dateInput = sc.nextLine();
                    formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    try{
                        LocalDate date = LocalDate.parse(dateInput, formatter);
                        myCal.setCalendar(date);
                        myCal.printDayEventCalendar();

                    }catch(IllegalArgumentException exp) {
                        System.out.println("Invalid Date");
                    }
                    break;
                case "E":
                    myCal.printOneTimeEventList();
                    System.out.println();
                    myCal.printRecurringTimeEventList();
                    break;
                case "D":
                    do{
                        System.out.println("Enter:\n" +
                                "   + [S]elected for removing ONETIME event with specified date and name\n" +
                                "   + [A]ll for removing all ONETIME event with specific date\n" +
                                "   + [DR] for removing RECURRING events with specific name\n"+
                                "   + [Q] for quit\n");
                        input = sc.nextLine().toUpperCase();
                        if (input.equals("S")){

                            try{
                                System.out.println("Enter date of the event in format (MM/DD/YYYY)");
                                String inputDate = sc.nextLine();
                                formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                                LocalDate dateEvent = LocalDate.parse(inputDate, formatter);

                                myCal.setCalendar(dateEvent);
                                myCal.printDayEventCalendar();

                                System.out.println("Enter name of the event");
                                String nameEvent = sc.nextLine();
                                myCal.removeOneTimeEvent(dateEvent, nameEvent);
                                System.out.println("Successful remove "+ nameEvent + " event.");

                            }catch(IllegalArgumentException exp){
                                System.out.println("Invalid Date " + exp.getMessage());
                            }catch (NullPointerException e){
                                System.out.println("No found Date " + e.getMessage());
                            }

                        }else if(input.equals("A")){
                            try{
                                System.out.println("Enter date of the event");
                                String inputDate = sc.nextLine();
                                formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                                LocalDate date = LocalDate.parse(inputDate, formatter);

                                myCal.setCalendar(date);
                                myCal.printDayEventCalendar();

                                myCal.removeAllOneTimeEvent(date);
                                System.out.println("Successful remove "+ "all events of " + inputDate + ".");

                            }catch(IllegalArgumentException exp){
                                System.out.println("Invalid date" + exp.getMessage());
                            }

                        }else if(input.equals("DR")){

                            System.out.println("Enter name of the Recurring event");
                            name = sc.nextLine();
                            myCal.removeRecurringEvent(name);
                            System.out.println("Successful remove "+ name + " events.");
                        }

                    }while(!input.equals("Q"));

                    break;

                default:
                    break;
            }

        }while(!option.equals("Q"));

        //Export all events to output file
        exportEvents(myCal);
        //Close file
        sc.close();


    }

    /**
     * Load input file of one time and recurring events to calendar
     * @param myCal
     * @param formatter
     */
    public static void loadEvents(MyCalendar myCal, DateTimeFormatter formatter){
        String inputFile = "events.txt";

        //Read in input file
        try {
            //Get pathname of input file named events.txt
            File file = new File("events.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            //Read file line by line
            while ((line = br.readLine()) != null) {

                String name = line;
                line = br.readLine();
                char firstChar = line.charAt(0);
                //Slit line into elements and put them on array named pairs
                String pairs[] = line.split("\\s+");

                /*
                * Check first character of a line. If first character lied in between A-Z, it
                *  will be Recurring Event, otherwise it will be One Time Event
                 */
                if(firstChar>='A'&&firstChar<='Z'){ //Recurring Event
                    String day = pairs[0];
                    formatter = DateTimeFormatter.ofPattern("M/d/yy");
                    LocalDate startDate = LocalDate.parse(pairs[3], formatter);
                    LocalDate endDate = LocalDate.parse(pairs[4], formatter);

                    formatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime startTime = LocalTime.parse(pairs[1], formatter);
                    LocalTime endTime = LocalTime.parse(pairs[2], formatter);

                    //Add a recurring time event to the calendar
                    myCal.addRecurringTimeEvent(new RecurringTimeEvent(day, name, startDate, endDate, startTime, endTime));

                    //Add a recurring time event to event of the calendar
                    myCal.addRecurringEvent(day, new Event(name, startDate, endDate, startTime, endTime));
                }else{
                    //OneTime Event
                    formatter = DateTimeFormatter.ofPattern("M/d/yy");
                    LocalDate date = LocalDate.parse(pairs[0], formatter);;

                    formatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime startTime = LocalTime.parse(pairs[1], formatter);
                    LocalTime endTime = LocalTime.parse(pairs[2], formatter);

                    //Add a onetime event to the calendar
                    myCal.addOneTimeEvent(new OneTimeEvent(name, date, startTime, endTime));

                    //Add a onetime event to event of the calendar
                    myCal.addEvent(new Event(name, date, date, startTime, endTime));
                }
            }
        }catch (FileNotFoundException exp){
            System.out.println(" File Not Found " + exp.getMessage());
        }catch (IOException exp){
            System.out.println("No Found Date in Event " + exp.getMessage());
        }

    }

    /**
     * Export all events in calendar to output.txt file
     * @param myCal - current calendar
     */
    public static void exportEvents(MyCalendar myCal){
        PrintStream printer = null;
        try {
            printer = new PrintStream(new File("output.txt"));

        }catch(FileNotFoundException exp){
            System.out.println("File not Found " + exp.getMessage());
        }

        //Assign printer to output stream
        System.setOut(printer);
        myCal.printOneTimeEventList();
        myCal.printRecurringTimeEventList();
    }


}

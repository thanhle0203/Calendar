/**
 * CS 151
 * Assignment #1
 * Class MyCalendar
 * @author Thanh Le
 * @version 1.0 2/16/21
 */
import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Class that defines an underlying data structure to hold events

/**
 * Class MyCalendar creates object events that hold local date and arraylist of events
 */
public class MyCalendar {
    //Get current calendar date
    LocalDate cal = LocalDate.now();

    //events object have key as LocalDate and value as Event class
    private TreeMap<LocalDate, ArrayList<Event>> events;

    {
        events = new TreeMap<LocalDate, ArrayList<Event>>();
    }
    //oneTimeEvents object have key as LocalDate and value as OneTimeEvent class
    private TreeMap<LocalDate, ArrayList<OneTimeEvent>> oneTimeEvents;
    {
        oneTimeEvents = new TreeMap<LocalDate, ArrayList<OneTimeEvent>>();
    }

    //recurringTimeEvents object have key as LocalDate and value as RecurringTimeEvent class
    private TreeMap<LocalDate, ArrayList<RecurringTimeEvent>> recurringTimeEvents;
    {
        recurringTimeEvents = new TreeMap<LocalDate, ArrayList<RecurringTimeEvent>>();
    }


    /**
     * Return date of calendar
     * @return date - date of calendar
     */
    public LocalDate getCalendar(){
        return cal;
    }

    /**
     * Reset current Date of calendar
     * @param cal - current date of calendar
     */
    public void setCalendar(LocalDate cal){
        this.cal = cal;
    }

    /**
     * Print full days of each week of the month in calendar
     */
    public void printMonthCalendar(){
        //Print Month and Year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        System.out.println(" " + formatter.format(cal));
        System.out.println("-----------------------------");

        //Print Days of Week
        System.out.println(" Su  Mon  Tu  We  Th  Fr  Sa");
        System.out.println("-----------------------------");
        LocalDate date = LocalDate.of(cal.getYear(), cal.getMonth(), 1 );

        //Starting first day of month
        int d = day(cal.getMonth().getValue(), 1, cal.getYear());

        //Print empty space if days haven't lied in the current month of the calendar
        for (int i = 0; i<d; i++)
            System.out.print("    ");

        /*Print day occurring in the month
         * Print {day} for current day of the month in calendar
         */
        for (int i=0; i<cal.lengthOfMonth(); i++) {
            if (date.equals(LocalDate.now())) {
                System.out.printf(" {%2d} ", date.getDayOfMonth());
            } else {
                System.out.printf(" %2d ", date.getDayOfMonth());

            }

            //Increasing to next day of month
            date = date.plusDays(1);

            //Go to next row of next week if the last day of week is Sunday
            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                System.out.println("\n");
        }
        System.out.println();


    }

    /**
     * Return day of calendar
     * @param month - month of calendar
     * @param day - day of calendar
     * @param year - day of calendar
     * @return d - return day
     */
    public static int day(int month, int day, int year){
        int y = year -(14-month)/12;
        int x = y + y/4 -y/100 + y/400;
        int m = month + 12*((14-month)/12) -2;
        int d = (day + x + (31*m)/12)%7;
        return d;
    }


    /**
     * Add one time event to event array list and check overlap date and time
     * @param e - return one time event list
     */
    public void addEvent(Event e){
        LocalDate startTime = e.getTime().getStartDate();

        //Check if the event overlaps with any other events
        if(!doesOverlap(startTime, e)){
            events.putIfAbsent(startTime, new ArrayList<Event>());
            events.get(startTime).add(e);
        }else{
            System.out.println("Time conflict! Could not add " + e.getName());
        }
    }

    /**
     * Add a recurring event to the event array list and check overlap date and time
     * @param days - days of recurring event list
     * @param e - recurring event object
     */
    public void addRecurringEvent(String days, Event e){
        ArrayList<LocalDate> dates = getDay(days, e.getTime().getStartDate(),
                e.getTime().getEndDate());

        for(LocalDate date: dates){
            if(!doesOverlap(date, e)){
                events.putIfAbsent(date, new ArrayList<Event>());
                events.get(date).add(e);
            }
        }
    }
    /**
     * Get date with specific days recognized according to first character day in recurring event
     * @param days - string of days in recurring event
     * @param startDate - start date in recurring event
     * @param endDate - end date in recurring event
     * @return date - return date in recurring event
     */
    public ArrayList<LocalDate> getDay(String days, LocalDate startDate, LocalDate endDate){

        ArrayList<DayOfWeek> daysRecurring= new ArrayList<DayOfWeek>();

        for(char day: days.toCharArray()){
            switch(day){
                case 'U':
                    daysRecurring.add(DayOfWeek.SUNDAY);
                    break;
                case 'M':
                    daysRecurring.add(DayOfWeek.MONDAY);
                    break;
                case 'T':
                    daysRecurring.add(DayOfWeek.TUESDAY);
                    break;
                case 'W':
                    daysRecurring.add(DayOfWeek.WEDNESDAY);
                    break;
                case 'R':
                    daysRecurring.add(DayOfWeek.THURSDAY);
                    break;
                case 'F':
                    daysRecurring.add(DayOfWeek.FRIDAY);
                    break;
                case 'S':
                    daysRecurring.add(DayOfWeek.SATURDAY);
                    break;
                default:
                    break;
            }
        }

        //Return date with day occurring in recurring time event
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        for(LocalDate date= startDate; !date.isAfter(endDate); date=date.plusDays(1)){
            if(daysRecurring.contains(date.getDayOfWeek())){
                dates.add(date);
            }
        }
        return dates;
    }

    /**
     * Add one time event to oneTimeEvents array list and check overlap date and time
     * @param e - onetime event object
     */
    public void addOneTimeEvent(OneTimeEvent e){
        LocalDate startTime = e.getTime().getStartDate();

        //Check if the event overlaps with any other events
        if(!doesOverlap(startTime, e)){
            oneTimeEvents.putIfAbsent(startTime, new ArrayList<OneTimeEvent>());
            oneTimeEvents.get(startTime).add(e);
        }else{
            System.out.println("Time conflict! Could not add " + e.getName()+ " event.");
        }
    }


    /**
     * Add recurring event to oneTimeEvents array list and check overlap date and time
     * @param e - recurring event object
     */
    public void addRecurringTimeEvent(RecurringTimeEvent e){

        LocalDate startTime = e.getTime().getStartDate();

        //Check if the event overlaps with any other events
        if(!doesOverlap(startTime, e)){
            recurringTimeEvents.putIfAbsent(startTime, new ArrayList<RecurringTimeEvent>());
            recurringTimeEvents.get(startTime).add(e);
        }else{
            System.out.println("Time conflict! Could not add " + e.getName() + "event");
            System.out.println("Please try another time.");
        }

    }

    /**
     * Check overlap date of event this object with other object
     * @param date - date of event
     * @param otherEvent - other event
     * @return true for overlap - false for not overlap
     */
    public boolean doesOverlap(LocalDate date, Event otherEvent){
        ArrayList<Event> dayEvents = events.get(date);
        if (dayEvents != null) {
            for (Event event: dayEvents){
                if(event.doesOverlap(otherEvent)==true){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check overlap date of OnetimeEvent object of this object with other object
     * @param date - date of one time event
     * @param otherEvent - other event
     * @return true for overlap - false for not overlap
     */
    public boolean doesOverlap(LocalDate date, OneTimeEvent otherEvent){
        ArrayList<OneTimeEvent> dayEvents = oneTimeEvents.get(date);
        if (dayEvents != null) {
            for (OneTimeEvent event: dayEvents){
                if(event.doesOverlap(otherEvent)==true){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check overlap date of RecurringTimeEvent object of this object with other object
     * @param date - date of recurring event
     * @param otherEvent - other event
     * @return true for overlap - false for not overlap
     */
    public boolean doesOverlap(LocalDate date, RecurringTimeEvent otherEvent){
        ArrayList<RecurringTimeEvent> dayEvents = recurringTimeEvents.get(date);
        if (dayEvents != null) {
            for (RecurringTimeEvent event: dayEvents){
                if(event.doesOverlap(otherEvent)==true){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove one time event at specific input date and name
     * @param date - date of event want to remove
     * @param name - name o event want to remove
     */
    public void removeOneTimeEvent(LocalDate date, String name){
        OneTimeEvent foundDateEvent;

        //Size of all date one time events
        int sizeDateEvent = oneTimeEvents.get(date).size();

        /*
        Check all date in sizeDateEvent, if found name of date event, remove it
         */
        for(int i=0; i<sizeDateEvent; i++){
            foundDateEvent = oneTimeEvents.get(date).get(i);
            if(foundDateEvent.sameEventName(name)==true){
                oneTimeEvents.get(date).remove(i);
                break;
            }
        }

        //After remove name event, size of events couldn't be same size
        if(oneTimeEvents.get(date).size()==sizeDateEvent){
            throw new IllegalArgumentException("Event not found in calendar");
        }

    }

    /**
     * Remove all one time events at input date
     * @param date - date of event want to remove
     */
    public void removeAllOneTimeEvent(LocalDate date){
        if(oneTimeEvents.get(date)!=null){
            oneTimeEvents.get(date).clear();
            System.out.println("Event " + oneTimeEvents.get(date) + " has been removed");
        }
    }

    /**
     * Remove all recurring event at input name
     * @param name
     */
    public void removeRecurringEvent(String name){
        if(recurringTimeEvents.get(name)!=null){
            recurringTimeEvents.get(name).clear();
        }
    }

    /**
     * Adjust month of calendar which go to next month or previous month
     * @param numberOfMonths - number of months want to go adjust
     */
    public void navigateMonth(int numberOfMonths){
        cal = cal.plusMonths(numberOfMonths);
    }

    /**
     * Adjust day of calendar which go to next day or previous day
     * @param numberOfDays
     */
    public void navigateDay(int numberOfDays){
        cal = cal.plusDays(numberOfDays);
    }

    /**
     * Print date of calendar in format when selection view day of calendar
     */
    public void printDayCalendar(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM d, yyyy");
        System.out.println(" " + formatter.format(cal));

    }

    /**
     * Print date of calendar in format and events happening in day of calendar when selection view day of calendar
     */
    public void printDayEventCalendar(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMMM d, yyyy");
        System.out.println(" " + formatter.format(cal));

        //Get events at current day
        ArrayList<Event> dayEvents = events.get(cal);
        if(dayEvents != null){
            //Sort events in order of time
            Collections.sort(dayEvents);
            //Print event details
            for (Event event: dayEvents){
                event.printEventList();
            }
        }
        System.out.println();
    }

    /**
     * Print date of calendar in format and events happening in month of calendar when selection view day of calendar
     */
    public void printMonthEventCalendar(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yy");
        System.out.println(" " + formatter.format(cal));
        System.out.println("-----------------------------");

        //Print Days of Week
        System.out.println(" Su  Mon  Tu  We  Th  Fr  Sa");
        System.out.println("-----------------------------");

        LocalDate date = LocalDate.of(cal.getYear(), cal.getMonthValue(), 1 );

        //Starting first day of month
        int d = day(cal.getMonth().getValue(), 1, cal.getYear());

        //Print empty space if days haven't lied in the current month of the calendar
        for (int i = 0; i<d; i++)
            System.out.print("    ");

        /*Print day occurring in the month
         * Print {day} for current day of the month in calendar
         */
        for (int i=0; i<cal.lengthOfMonth(); i++) {
            //System.out.println(event.printDateList());
            if (date.isEqual(printEventList())==true){

                System.out.printf(" {%2s} ", date.getDayOfMonth());
            } else {
                System.out.printf(" %2s ", date.getDayOfMonth());
            }

            //Increasing to next day of month
            date = date.plusDays(1);

            //Go to next row of next week if the last day of week is Sunday
            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                System.out.println("\n");

        }
        System.out.println();


    }

    /**
     * Return all local date in event list
     * @return
     */
    public LocalDate printEventList(){
        LocalDate event = LocalDate.of(1,1,1);
        //Get event and date of one time event in calendar
        for (Map.Entry<LocalDate, ArrayList<Event>> entry: events.entrySet()){
            Collections.sort(entry.getValue());

            if (entry!=null){
                //Print all values happening of the one time event in calendar
                for(Event e : entry.getValue()){

                    event = e.printDateList();

                }
            }
        }
        return event;
    }

    /**
     * Method check to two object have same date
     * @param other
     * @return
     */
    public boolean isEqual(Object other){
        return (this == other);
    }



    /**
     * Print one time event lists with format year, date, time, and name events
     */
    public void printOneTimeEventList(){

        System.out.println("ONE TIME EVENTS");

        //Get event and date of one time event in calendar
        for (Map.Entry<LocalDate, ArrayList<OneTimeEvent>> entry: oneTimeEvents.entrySet()){

            Collections.sort(entry.getValue());

            //Print year of one time event in calendar
            int year = entry.getKey().getYear();
            System.out.println(year);
            //Check the existence of year of the event
            if(entry.getKey().getYear()!=year){
                year = entry.getKey().getYear();
                System.out.println(entry.getValue());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM d");
            //Print all values happening of the one time event in calendar
            for(OneTimeEvent e : entry.getValue()){
                System.out.print(" " + formatter.format(entry.getKey()) + " ");
                e.printOneTimeEvent();
            }
        }

        System.out.println();
    }

    /**
     * Print recurring time event lists with format name, day of the week, time, and date events
     */
    public void printRecurringTimeEventList(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");
        System.out.println("RECURRING EVENTS");

        //Get event and date of one time event in calendar
        for (Map.Entry<LocalDate, ArrayList<RecurringTimeEvent>> entry: recurringTimeEvents.entrySet()){
            Collections.sort(entry.getValue());

            //Print all values happening of the one time event in calendar
            for(RecurringTimeEvent e : entry.getValue()){
                e.printRecurringTimeEvent();
            }
        }

        System.out.println();
    }

}



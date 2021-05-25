/**
 * CS 151
 * Assignment #1
 * Class RecurringTimeEvent
 * @author Thanh Le
 * @version 1.0 2/16/21
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class RecurringTimeEvent returns day, name, time of recurring time event in calendar
 *
 */
public class RecurringTimeEvent implements Comparable<RecurringTimeEvent> {
    String day;
    String name;
    TimeInterval time;

    /**
     * Constructs day, name, start and end Date, start and end Time of class RecurringTimeEvent
     * @param day - day of event
     * @param name - name of event
     * @param startDate - start date of event
     * @param endDate - end date of event
     * @param startTime - start time of event
     * @param endTime - end time of event
     */
    public RecurringTimeEvent(String day, String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime
            endTime) {
        this.day = day;
        this.name = name;
        this.time = new TimeInterval(startDate, endDate, startTime, endTime);
    }

    /**
     * Return day of recurring time event in calendar
     * @return day - day of event
     */
    public String getDay(){
        return day;
    }

    /**
     * Return name of recurring time event in calendar
     * @return name - name of event
     */
    public String getName(){
        return name;
    }

    /**
     * Return time of recurring time event in calendar
     * @return - time of event
     */
    public TimeInterval getTime(){
        return time;
    }

    /**
     * Compare this and others names of recurring events in calendar
     * @param othername
     * @return
     */
    public boolean sameEventName(String othername){
        if(this.getName().equals(othername))
            return true;
        return false;
    }

    /**
     * Compare time of this and other recurring events in calendar
     * @param otherTime
     * @return
     */
    public int compareTo(RecurringTimeEvent otherTime){
        return this.getTime().compareTo(otherTime.getTime());
    }

    /**
     * Check overlap of this and other recurring events in calendar
     * @param otherTime
     * @return
     */
    public boolean doesOverlap(RecurringTimeEvent otherTime){
        return this.getTime().doesOverlap(otherTime.getTime());
    }


    /**
     * Print time and date of recurring events in calendar
     */
    public void printRecurringTimeEvent(){
        /*
        Set format of an time and date event
         */
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("M/d/yy");

        System.out.println(" " + getName());

        System.out.println("  " + getDay() + " " + formatterTime.format(getTime().getStartTime()) + " - " +
                formatterTime.format(getTime().getEndTime()) + " " + formatterDate.format(time.getStartDate()) +
                " " + formatterDate.format(time.getEndDate()));
    }
}

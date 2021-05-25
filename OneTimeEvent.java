/**
 * CS 151
 * Assignment #1
 * Class OneTimeEvent
 * @author Thanh Le
 * @version 1.0 2/16/21
 *
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class one time event return name, time of events and
 * print date time format of events happening in calendar
 */
public class
OneTimeEvent implements Comparable<OneTimeEvent> {
    String name;
    TimeInterval time;

    /**
     * Constructs name, start date, end date, start time, end time
     * of one time event in calendar
     * @param name - name of event
     * @param date - date of event
     * @param startTime - start time of event
     * @param endTime - end time of event
     */
    public OneTimeEvent(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.name = name;
        this.time = new TimeInterval(date, startTime, endTime);
    }

    /**
     * Return name of one time events in calendar
     * @return name - name of event
     */
    public String getName(){
        return name;
    }

    /**
     * Return time of one time events in calendar
     * @return time - time of event
     */
    public TimeInterval getTime(){
        return time;
    }

    /**
     * Compare name of two events are same or not
     * @param othername
     * @return true for same event nam and false for different event name
     */
    public boolean sameEventName(String othername){
        if(this.getName().equals(othername))
            return true;
        return false;
    }

    /**
     * Compare time of two events are same or not
     * @param otherTime
     * @return true if two events same time and false if different time
     */
    public int compareTo(OneTimeEvent otherTime){
        return this.getTime().compareTo(otherTime.getTime());
    }

    /**
     * Check overlap of two events
     * @param otherTime
     * @return true if two events same time and false if different time
     */
    public boolean doesOverlap(OneTimeEvent otherTime){
        return this.getTime().doesOverlap(otherTime.getTime());
    }


    /**
     * Print time and name of one time event
     */
    public void printOneTimeEvent(){

        //Set format time of event;
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:mm");

        System.out.println("  " + formatterTime.format(getTime().getStartTime()) + " - " +
                formatterTime.format(getTime().getEndTime()) + " " + getName());
    }
}

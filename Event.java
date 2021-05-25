/**
 * CS 151
 * Assignment #1
 * Class Event
 * @author Thanh Le
 * @version 1.0 2/16/21
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class Event hold name, start and end date, start and end time of both one time event lists
 * and recurring time lists.
 */
public class Event implements Comparable<Event>{
    String name;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    TimeInterval time;

    /**
     * Constructs class Event holds name event, class TimeInterval
     * @param name - name of event
     * @param startDate - start date of event
     * @param endDate - end date of event
     * @param startTime - start time of event
     * @param endTime - end time of event
     */
    public Event(String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime
                 endTime){
        this.name = name;
        this.time = new TimeInterval(startDate, endDate, startTime, endTime);
    }
    /**
     * Constructs class Event holds name event, startDate, endDate
     * @param name - name of event
     * @param startDate - start date of event
     * @param endDate - end date of event
     */
    public Event(String name, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;

    }
    /**
     * Constructs class Event holds name event, startTime, endTime
     * @param name - name of event
     * @param startTime - start time of event
     * @param endTime - end time of event
     */
    public Event(String name, LocalTime startTime, LocalTime endTime){
        this.name = name;
        this.startTime= startTime;
        this.endTime = endTime;
    }

    /**
     * Return name of event object
     * @return name - name of event
     */
    public String getName(){
        return name;
    }

    /**
     * Return start date of event
     * @return startDate - start date event
     */
    public LocalDate getStartDate(){
        return startDate;
    }

    /**
     * Return end date of event
     * @return endDate - end date of event
     */
    public LocalDate getEndDate(){
        return endDate;
    }

    /**
     * Return start time of event
     * @return startTime - start time of event
     */
    public LocalTime getStartTime(){
        return startTime;
    }

    /**
     * Return end time of event
     * @return endTime - end time of event
     */
    public LocalTime getEndTime(){
        return endTime;
    }

    /**
     * Return time of event object
     * @return time - time of event
     */
    public TimeInterval getTime(){
        return time;
    }


    /**
     * Check if this name events with other name events
     * @param othername
     * @return true for same name - false for different name
     */
    public boolean sameEventName(String othername){
        if(this.getName().equals(othername))
            return true;
        return false;
    }

    /**
     * Compare this time events with other time events object
     * @param otherTime - otherTime of events
     * @return - true if same time, false if different time
     */
    public int compareTo(Event otherTime){
        return this.getTime().compareTo(otherTime.getTime());
    }

    /**
     * Check overlap of this time object event with other time objects
     * @param otherTime - other time of events object
     * @return true if overlap - false if not overlap
     */
    public boolean doesOverlap(Event otherTime){
        return this.getTime().doesOverlap(otherTime.getTime());
    }


    /**
     * Print event occurring in day of event in format name, time
     */
    public void printEventList(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        System.out.println(getName() + ": " + formatter.format(getTime().getStartTime())+"-"+
                formatter.format(getTime().getEndTime()));
    }

    public LocalDate printDateList(){
        LocalDate dateEvent = LocalDate.of(getTime().getStartDate().getYear(), getTime().getStartDate().getMonth(), getTime().getStartDate().getDayOfMonth());

        return dateEvent;

    }


}

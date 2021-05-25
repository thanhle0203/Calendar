/**
 * CS 151
 * Assignment #1
 * Class TimeInterval
 * @author Thanh Le
 * @version 1.0 2/16/21
 */

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Class TimeInterval checks the date and time conflict
 * between two dates and times.
 */
public class TimeInterval implements Comparable<TimeInterval>{
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startInstant;
    LocalTime endInstant;

    /*Constructor of a class time interval represents a period of time between two instants
     */
    public TimeInterval(LocalDate date, LocalTime startInstant, LocalTime endInstant){
        this.startDate = date;
        this.endDate = date;
        this.startInstant = startInstant;
        this.endInstant = endInstant;
    }
    /*Constructor of a class time interval represents a period of time between two instants
     */

    /**
     * Construct start and end Date, start and end Time of class TimeInterval
     * @param startDate
     * @param endDate
     * @param startInstant
     * @param endInstant
     */
    public TimeInterval(LocalDate startDate, LocalDate endDate, LocalTime startInstant, LocalTime endInstant){
        this.startDate = startDate;
        this.endDate = endDate;
        this.startInstant = startInstant;
        this.endInstant = endInstant;
    }

    /**
     * Return start date of LocalDate object
     * @return
     */
    public LocalDate getStartDate(){
        return startDate;
    }

    /**
     * Return end date of LocalDate object
     * @return
     */
    public LocalDate getEndDate(){
        return endDate;
    }

    /**
     * Return start time of LocalTime object
     * @return
     */
    public LocalTime getStartTime(){
        return startInstant;
    }

    /**
     * Return end time of LocalTime object
     * @return
     */
    public LocalTime getEndTime(){
        return endInstant;
    }

    /**
     * Check the overlap between TimeInterval objects using end Time
     * @param otherTime
     * @return
     */
    public boolean doesOverlap(TimeInterval otherTime){
        if(this.getStartDate().equals(otherTime.getStartDate())){
            return this.getStartTime().isBefore(otherTime.getEndTime())&&
                    otherTime.getStartTime().isBefore(this.getEndTime());
        }
        //Return False if object not on the same day
        return false;
    }

    /**
     * Compare objects of TimeInterval using start time
     * @param otherTime
     * @return
     */
    @Override
    public int compareTo(TimeInterval otherTime){
        return this.getStartTime().compareTo(otherTime.getStartTime());
    }




}


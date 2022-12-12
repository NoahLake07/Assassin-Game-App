package data;

public class Timeout {

    public static int DEFAULT_TIMEOUT_HOURS = 24;
    public static final int UNDEFINED = -1;

    Time timeStart, timeEnd;
    String timeoutDesc;
    int PID;

    /**
     * Makes a timeout using the current time, and sets the time end
     * to the default amount of hours from the current time.
     */
    public Timeout(){
        this(Time.getCurrentTime(),Time.getCurrentTime().addHours(DEFAULT_TIMEOUT_HOURS), null, UNDEFINED);
    }

    /**
     * Makes a timeout using the current time and a description, and the ending time is set to the default timeout hours after
     * the start time.
     * @param desc Timeout description
     */
    public Timeout(String desc){
        this(Time.getCurrentTime(),Time.getCurrentTime().addHours(DEFAULT_TIMEOUT_HOURS), desc, UNDEFINED);
    }

    /**
     * Makes a timeout using the current time and a description, and the ending time is set to the default timeout hours after
     * the start time.
     * @param desc Timeout description
     * @param PID player id of the player being effected by the timeout
     */
    public Timeout(String desc, int PID){
        this(Time.getCurrentTime(),Time.getCurrentTime().addHours(DEFAULT_TIMEOUT_HOURS), desc, PID);
    }

    public Timeout(Time start, Time end, String desc){
        timeStart = start;
        timeEnd = end;
        PID = UNDEFINED;
        timeoutDesc = desc;
    }

    /**
     * Creates a custom timeout log
     * @param start starting time
     * @param end ending time
     * @param desc description
     * @param pid player id
     */
    public Timeout(Time start, Time end, String desc, int pid){
        timeStart = start;
        timeEnd = end;
        PID = pid;
        timeoutDesc = desc;
    }

    public String getSaveString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Ø");
        sb.append(timeStart.getSaveString() + ",");
        sb.append(timeEnd.getSaveString() + ",");
        sb.append(timeoutDesc + ",");
        sb.append(PID);

        return sb.toString();
    }

}

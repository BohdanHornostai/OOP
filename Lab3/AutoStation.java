import java.io.Serializable;
import java.util.ArrayList;

public class AutoStation implements Serializable {
    private static int count = 0;
    private int id;
    private String name;
    private ArrayList<SchedulePost> schedule;

    {
        id = count++;
        schedule = new ArrayList<>();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("|~|~| #%3d | %s |~|~|%n", id, name));
        for (SchedulePost i : schedule) {
            result.append(i);
        }
        return new String(result);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSchedule(ArrayList<SchedulePost> schedule) {
        this.schedule = schedule;
    }

    public ArrayList<SchedulePost> getSchedule() {
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SchedulePost {

	private int raceNumber;
	private Time departureTime;
	private boolean daysOfWeek[];
	private int freeSeats;
	private ArrayList<RoutePoint> route;

	{
		raceNumber = 0;
		departureTime = new Time(0);
		daysOfWeek = new boolean[7];
		Arrays.fill(daysOfWeek, false);
		freeSeats = 0;
		route = new ArrayList<>();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(String.format("|- %4d (%10s - %10s) | dep: %tR | free seats: %2d | ", raceNumber,
				(route.size() > 0) ? route.get(0).getKey() : null,
				(route.size() > 0) ? route.get(route.size() - 1).getKey() : null, departureTime, freeSeats));
		for (int i = 0; i < 7; i++) {
			if (daysOfWeek[i]) {
				result.append(DayOfWeek.values()[i].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
				result.append(" ");
			}
		}
		result.append(String.format("%n"));
		return new String(result);
	}

	public String toDetalizedString() {
		StringBuilder result = new StringBuilder();
		result.append(String.format("|- %4d | dep: %tR | free seats: %2d | ", raceNumber, departureTime, freeSeats));
		for (int i = 0; i < 7; i++) {
			if (daysOfWeek[i]) {
				result.append(DayOfWeek.values()[i].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
				result.append(" ");
			}
		}
		result.append(String.format("%n"));
		for (RoutePoint i : route) {
			result.append(String.format("~|- %15s | %tR%n", i.getKey(), i.getValue()));
		}
		return new String(result);
	}

	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}

	public int getRaceNumber() {
		return raceNumber;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public void setDaysOfWeek(boolean daysOfWeek[]) {
		System.arraycopy(daysOfWeek, 0, this.daysOfWeek, 0, 7);
	}

	public boolean[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public void setRoute(ArrayList<RoutePoint> route) {
		this.route = route;
	}

	public ArrayList<RoutePoint> getRoute() {
		return route;
	}
}
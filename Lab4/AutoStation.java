
import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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
		result.append(String.format("#%d | %s%n", id, name));
		for (SchedulePost i : schedule) {
			result.append(i);
		}
		return new String(result);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == this.getClass())
			return this.id == ((AutoStation) obj).id;
		return false;
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

	public static class SchedulePost implements Serializable {

		private int raceNumber;
		private Time departureTime;
		private boolean daysOfWeek[];
		private int freeSeats;
		private ArrayList<Pair<String, Time>> route;

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
			result.append(String.format("%d (%s - %s) | dep: %tR | free seats: %d | ", raceNumber,
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

		public String toDetailedString() {
			StringBuilder result = new StringBuilder();
			result.append(String.format("%d | dep: %tR | free seats: %d | ", raceNumber, departureTime, freeSeats));
			for (int i = 0; i < 7; i++) {
				if (daysOfWeek[i]) {
					result.append(DayOfWeek.values()[i].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
					result.append(" ");
				}
			}
			result.append(String.format("%n"));
			for (Pair<String, Time> i : route) {
				result.append(String.format("- %s | %tR%n", i.getKey(), i.getValue()));
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

		public void setRoute(ArrayList<Pair<String, Time>> route) {
			this.route = route;
		}

		public ArrayList<Pair<String, Time>> getRoute() {
			return route;
		}

	}

}
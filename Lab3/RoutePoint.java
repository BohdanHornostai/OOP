import java.sql.Time;

public class RoutePoint {
	private String name;
	private Time departureTime;

	public RoutePoint(String name, Time departureTime) {
		this.name = name;
		this.departureTime = departureTime;
	}

	public RoutePoint() {
		name = null;
		departureTime = null;
	}

	public String getKey() {
		return name;
	}

	public Time getValue() {
		return departureTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}
}
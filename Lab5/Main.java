import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Path;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;



public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("uk", "UA"));
        Scanner in = new Scanner(System.in);
        LinkedList<AutoStation> autostations = new LinkedList<>();
        while (true) {
            String input;
            System.out.println("1 - add new auto station");
            System.out.println("2 - delete auto station");
            System.out.println("3 - clear list");
            System.out.println("4 - compact auto stations list");
            System.out.println("5 - auto stations list");
            System.out.println("6 - auto station menu");
            System.out.println("7 - serialize all auto stations to XML doc ");
            System.out.println("8 - deserialize all auto stations from XML doc ");
            System.out.println("9 - serialize all auto stations to file ");
            System.out.println("10 - deserialize all auto stations from file ");
            System.out.println("11 - sort by free seats ");
            System.out.println("0 - exit");
            byte command = in.nextByte();
            switch (command) {
                case 0:
                    System.out.println("bye!");
                    System.exit(0);
                case 1:
                    System.out.println("enter auto station's name in format \"Name (City)\":");
                    in.nextLine();
                    input = in.nextLine();
                    if (!ContainersHelper.isValidName(input)) {
                        System.err.println("wrong format!");
                        break;
                    }
                    autostations.add(new AutoStation());
                    autostations.get(autostations.size()-1).setName(input);
                    System.out.printf("auto station %s successfully added (id - %d)%n",
                            autostations.get(autostations.size()-1).getName(),
                            autostations.get(autostations.size()-1).getId());
                    break;
                case 2:
                    System.out.println("enter id:");
                    int deleteId = in.nextInt();
                    boolean removedById = false;
                    for (int i = 0; i < autostations.size(); i++) {
                        if (autostations.get(i).getId() == deleteId) {
                            autostations.remove(i);
                            removedById = true;
                            break;
                        }
                    }
                    if (!removedById)
                        System.err.println("not found");
                    break;
                case 3:
                    autostations.clear();
                    break;
                case 4:
                    if (autostations.size() == 0) {
                        System.err.println("list is empty");
                        break;
                    }
                    System.out.println("-=LIST=-");
                    for (int i = 0; i < autostations.size(); i++) {
                        System.out.printf("[%d] %s | id - %d | scheduled routes - %d%n", i, autostations.get(i).getName(),
                                autostations.get(i).getId(), autostations.get(i).getSchedule().size());
                    }
                    break;
                case 5:
                    System.out.println(autostations);
                    break;
                case 6:
                    System.out.println("enter id:");
                    int menuId = in.nextInt(), menuIndex = -1;
                    for (int i = 0; i < autostations.size(); i++) {
                        if (autostations.get(i).getId() == menuId) {
                            menuIndex = i;
                            break;
                        }
                    }
                    if (menuIndex != -1) {
                        AutoStation menuAutostation = autostations.get(menuIndex);
                        boolean exitFromAutoStationMenu = false;
                        do {
                            System.out.printf("| %s management menu |%n", menuAutostation.getName());
                            System.out.println("1 - auto station info");
                            System.out.println("2 - set id");
                            System.out.println("3 - set name");
                            System.out.println("4 - edit schedule");
                            System.out.println("5 - show schedule");
                            System.out.println("6 - show full schedule");
                            System.out.println("7 - return");
                            System.out.println("8 - exit");
                            byte menuCommand = in.nextByte();
                            switch (menuCommand) {
                                case 1:
                                    System.out.println(menuAutostation);
                                    break;
                                case 2:
                                    System.out.println("enter new id:");
                                    menuAutostation.setId(in.nextInt());
                                    break;
                                case 3:
                                    System.out.println("enter new name in format \"Name (City)\":");
                                    in.nextLine();
                                    input = in.nextLine();
                                    if (!ContainersHelper.isValidName(input)) {
                                        System.err.println("wrong format!");
                                        break;
                                    }
                                    menuAutostation.setName(input);
                                    break;
                                case 4:
                                    ArrayList<AutoStation.SchedulePost> scheduleToEdit = menuAutostation.getSchedule();
                                    boolean exitFromScheduleEditing = false;
                                    do {
                                        System.out.println("-schedule editing-");
                                        System.out.println("1 - show schedule");
                                        System.out.println("2 - clear schedule");
                                        System.out.println("3 - delete post");
                                        System.out.println("4 - add post");
                                        System.out.println("5 - return");
                                        System.out.println("6 - exit");
                                        byte scheduleEditingCommand = in.nextByte();
                                        switch (scheduleEditingCommand) {
                                            case 1:
                                                if (scheduleToEdit.size() == 0) {
                                                    System.err.println("schedule is empty");
                                                    break;
                                                }
                                                for (AutoStation.SchedulePost i : scheduleToEdit) {
                                                    System.out.println(i.toDetailedString());
                                                }
                                                break;
                                            case 2:
                                                scheduleToEdit = new ArrayList<>();
                                                menuAutostation.setSchedule(scheduleToEdit);
                                                break;
                                            case 3:
                                                System.out.println("enter number of post (starts from 1)");
                                                int schedulePostToDelete = in.nextInt();
                                                if (schedulePostToDelete > scheduleToEdit.size()
                                                        || schedulePostToDelete < 1) {
                                                    System.err.println("not found!");
                                                    break;
                                                }
                                                scheduleToEdit.remove(schedulePostToDelete);
                                                break;
                                            case 4:
                                                AutoStation.SchedulePost schedulePostToAdd = new AutoStation.SchedulePost();
                                                System.out.println("enter race number:");
                                                schedulePostToAdd.setRaceNumber(in.nextInt());
                                                while (true) {
                                                    System.out.println("enter departure time in format HH:MM");
                                                    in.nextLine();
                                                    try {
                                                        schedulePostToAdd.setDepartureTime(new Time(new
                                                                SimpleDateFormat("HH:mm")
                                                                .parse(in.nextLine()).getTime()));
                                                        break;
                                                    } catch (ParseException e) {
                                                        System.err.println("wrong format! try again");
                                                    }
                                                }
                                                System.out.println("enter number of free seats:");
                                                schedulePostToAdd.setFreeSeats(in.nextInt());
                                                boolean[] daysOfWeek = new boolean[7];
                                                Arrays.fill(daysOfWeek, false);
                                                System.out.println("enter number of days of week:");
                                                for (int i = 0, n = in.nextInt(); i < n; i++) {
                                                    System.out.println("1 - monday ... 7 - sunday");
                                                    byte temp = in.nextByte();
                                                    if (temp < 1 || temp > 7) {
                                                        i--;
                                                        System.err.println("wrong! again");
                                                    } else
                                                        daysOfWeek[temp-1] = true;
                                                }
                                                schedulePostToAdd.setDaysOfWeek(daysOfWeek);
                                                ArrayList<Pair<String, Time>> route = new ArrayList<>();
                                                System.out.println("enter route's points number:");
                                                int n = in.nextInt();
                                                in.nextLine();
                                                for (int i = 0; i < n; i++) {
                                                    try {
                                                        System.out.print("enter station name in format \"Name (City)\": ");
                                                        String name = in.nextLine();
                                                        if (!ContainersHelper.isValidName(name))
                                                            throw new ParseException(name, 0);
                                                        System.out.print("enter departure time HH:mm ");
                                                        String time = in.nextLine();
                                                        route.add(new Pair<>(name, new Time(new
                                                                SimpleDateFormat("HH:mm").parse(time).getTime())));
                                                    } catch (ParseException e) {
                                                        System.err.println("wrong format! try again");
                                                        i--;
                                                    }
                                                }
                                                schedulePostToAdd.setRoute(route);
                                                scheduleToEdit.add(schedulePostToAdd);
                                                break;
                                            case 5:
                                                exitFromScheduleEditing = true;
                                                break;
                                            case 6:
                                                System.out.println("bye!");
                                                System.exit(0);
                                            default:
                                                System.err.println("command not found");
                                        }
                                        menuAutostation.setSchedule(scheduleToEdit);
                                    } while (!exitFromScheduleEditing);
                                case 5:
                                    if (menuAutostation.getSchedule().size() == 0) {
                                        System.err.println("schedule is empty");
                                        break;
                                    }
                                    for (AutoStation.SchedulePost i : menuAutostation.getSchedule()) {
                                        System.out.println(i);
                                    }
                                    break;
                                case 6:
                                    if (menuAutostation.getSchedule().size() == 0) {
                                        System.err.println("schedule is empty");
                                        break;
                                    }
                                    for (AutoStation.SchedulePost i : menuAutostation.getSchedule()) {
                                        System.out.println(i.toDetailedString());
                                    }
                                    break;
                                case 7:
                                    exitFromAutoStationMenu = true;
                                    break;
                                case 8:
                                    System.out.println("bye!");
                                    System.exit(0);
                                default:
                                    System.err.println("unknown command");
                            }
                        } while (!exitFromAutoStationMenu);
                    } else
                        System.err.println("not found");
                    break;
                case 7:
                    if (autostations.size() == 0) {
                        System.err.println("list is empty!");
                        break;
                    }
                    XMLEncoder encoder = null;
                    try {
                    	Path p = ContainersHelper.isValidFormat();
                        if (p == null) throw new NullPointerException();
                        encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(p.toString())));
                        Integer size = autostations.size();
                        encoder.writeObject(size);
                        for (AutoStation i : autostations) {
                            encoder.writeObject(i);
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("file not found");
                        break;
                    } catch (NullPointerException e) {
                        System.err.println("path not found");
                        break;
                    } finally {
                        if (encoder != null)
                            encoder.close();
                    }
                    break;
                case 8:
                    XMLDecoder decoder = null;
                    try {
                    	Path p = ContainersHelper.isValidFormat();
                        if (p == null) throw new NullPointerException();
                        decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(p.toString())));
                        Integer size = (Integer) decoder.readObject();
                        autostations = new LinkedList<>() ;
                        for (int i = 0; i < size; i++) {
                            autostations.add((AutoStation) decoder.readObject());
                        }
                    } catch (FileNotFoundException e) {
                        System.err.println("file not found");
                        break;
                    } catch (NullPointerException e) {
                        System.err.println("path not found");
                        break;
                    } finally {
                        if (decoder != null)
                            decoder.close();
                    }
                    break;
                case 9:
                    ObjectOutputStream oos;
                    try {
                    	Path p = ContainersHelper.isValidF();
                        if (p == null) throw new NullPointerException();
                        oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(p.toString())));
                        oos.writeObject(autostations);
                        oos.flush();
                        oos.close();
                    } catch (FileNotFoundException e) {
                        System.err.println("file not found");
                        break;
                    } catch (IOException e) {
                        System.err.println("smth goes wrong");
                        break;
                    } catch (NullPointerException e) {
                        System.err.println("path not found");
                        break;
                    }
                    break;
                case 10:
                    ObjectInputStream ois;
                    try {
                    	Path p = ContainersHelper.isValidF();
                        if (p == null) throw new NullPointerException();
                        ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(p.toString())));
                        Object obj = ois.readObject();
                        LinkedList restore;
                        if (obj instanceof LinkedList) {
                            restore = (LinkedList) obj;
                            autostations.clear();
                            for (Object i : restore) {
                                autostations.add((AutoStation) i);
                            }
                        } else throw new ClassNotFoundException();
                        ois.close();
                    } catch (FileNotFoundException e) {
                        System.err.println("file not found");
                    } catch (IOException e) {
                        System.err.println("smth goes wrong");
                    } catch (NullPointerException e) {
                        System.err.println("path not found");
                    } catch (ClassNotFoundException e) {
                        System.err.println("wrong file format");
                    }
                    break;
                case 11:
                	for (AutoStation i : autostations) {
                		i.sort();
                	}
                
            }
        }
    }
}
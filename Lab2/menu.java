import java.util.ArrayList;
import java.util.Scanner;

public class menu {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ArrayList<container> listOfContainers = new ArrayList<>();
		int com;
		while (true) {
			System.out.println("1 - Create container");
			System.out.println("2 - Fill container");
			System.out.println("3 - Remove container");
			System.out.println("4 - Show container");
			System.out.println("5 - Menu");
			System.out.println("6 - Exit");
			com = in.nextInt();
			switch (com) {
			case 1:
				System.out.print("Enter container's max length: ");
				listOfContainers.add(new container(in.nextInt()));
				break;
			case 2:
				System.out.print("Enter container's index: ");
				int index = in.nextInt();
				if (index > listOfContainers.size() || index < 0) {
					System.out.println("Index isn't found");
					break;
				}
				System.out.println("Enter " + listOfContainers.get(index - 1).maxSize() + " strings ");
				in.nextLine();
				for (int i = 0, n = listOfContainers.get(index - 1).maxSize(); i < n; i++) {
					listOfContainers.get(index - 1).add(in.nextLine());
				}
				break;
			case 3:
				System.out.print("Enter container's index: ");
				int delIndex = in.nextInt();
				if (delIndex > listOfContainers.size() || delIndex < 1) {
					System.out.println("Index isn't found");
					break;
				}
				listOfContainers.get(delIndex - 1).clear();
				System.out.println("Done!");
				break;
			case 4:
				System.out.print("Enter container's index: ");
				int showIndex = in.nextInt();
				if (showIndex > listOfContainers.size() || showIndex < 1) {
					System.out.println("Index isn't found");
					break;
				}
				System.out.println(listOfContainers.get(showIndex - 1).toString());
				break;
			case 5:
				System.out.print("Enter container's index: ");
				int menuIndex = in.nextInt();
				if (menuIndex > listOfContainers.size() || menuIndex < 1) {
					System.out.println("Index isn't found");
					break;
				}
				System.out.println("1 - Add element");
				System.out.println("2 - Remove element");
				System.out.println("3 - Convert to array and iterate through");
				System.out.println("4 - Current size");
				System.out.println("5 - Max size");
				System.out.println("6 - Check string");
				System.out.println("7 - Write to file (serialize)");
				System.out.println("8 - Read from file (deserialize)");
				System.out.println("9 - Sort");
				System.out.println("10 - Iterate through container (foreach)");
				System.out.println("11 - Iterate through container (while)");
				System.out.println("12 - Exit from menu");
				com = in.nextInt();

				if (com == 1) {
					System.out.println("Enter element:");
					in.nextLine();
					listOfContainers.get(menuIndex - 1).add(in.nextLine());
				} else if (com == 2) {
					System.out.println("Enter element:");
					in.nextLine();
					listOfContainers.get(menuIndex - 1).remove(in.nextLine());
				} else if (com == 3) {
					for (String i : listOfContainers.get(menuIndex - 1).toArray()) {
						System.out.println(i);
					}
				} else if (com == 4) {
					System.out.println(listOfContainers.get(menuIndex - 1).size());
				} else if (com == 5) {
					System.out.println(listOfContainers.get(menuIndex - 1).maxSize());
				} else if (com == 6) {
					System.out.println("Enter string to check if it exist in container:");
					in.nextLine();
					System.out.println(listOfContainers.get(menuIndex - 1).contains(in.nextLine()));
				} else if (com == 7) {
					System.out.println("enter file name(name.format): ");
					in.nextLine();
					listOfContainers.get(menuIndex - 1).serialize(in.nextLine());
				} else if (com == 8) {
					System.out.println("enter file name(name.format): ");
					in.nextLine();
					listOfContainers.get(menuIndex - 1).deserialize(in.nextLine());
				} else if (com == 9) {
					listOfContainers.get(menuIndex - 1).sort();
					System.out.println("done!");
				} else if (com == 10) {
					for (String i : listOfContainers.get(menuIndex - 1)) {
						System.out.println(i);
					}
				} else if (com == 11) {
					iterator i = (iterator) listOfContainers.get(menuIndex - 1).iterator();
					while (i.hasNext()) {
						System.out.println(i.next());
					}
				} else if (com == 12) {
					break;
				}
				break;
			case 6:
				System.out.println("The program is closed");
				in.close();
				return;
			}

		}
	}
}
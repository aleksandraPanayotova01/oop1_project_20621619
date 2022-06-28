import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public void readMenu(){
        CheckInCheckOut checkInOutHotel = new CheckInCheckOut();
        Availability availability = new Availability();
        OpenCloseXmlFile d1 = new OpenCloseXmlFile();
        Save saveContent = new Save();
        Help helpMenu = new Help();
        Scanner s1 = new Scanner(System.in);
        String input;
        do {
            helpMenu.menu();
            input = s1.nextLine();
            String[] arrOfStr = input.split(" ");

            switch (arrOfStr[0]) {
                case ("open"):
                    try {
                        d1.readXml(arrOfStr[1]);
                        System.out.println();
                    }catch(Exception e){
                        System.out.println("Check the entered file's name!");
                    }
                    break;
                case ("close"):
                    d1.close();
                    break;
                case ("save"):
                    saveContent.save();
                    break;
                case ("saveas"):
                    try {
                        saveContent.saveAs(arrOfStr[1]);
                    }catch (Exception e){
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("help"):
                    helpMenu.help();
                    break;
                case ("checkin"):
                    try {
                        int roomNumber = Integer.parseInt(arrOfStr[1]);
                        LocalDate checkInDate = LocalDate.parse(arrOfStr[2]);
                        LocalDate checkOutDate = LocalDate.parse(arrOfStr[3]);

                        List<String> notes = new ArrayList<>();
                        notes.add(arrOfStr[4]);
                        notes.add(arrOfStr[5]);//add two notes
                        int numberOfGuests; // not a required parameter
                        if (arrOfStr.length >= 7) {
                            numberOfGuests = Integer.parseInt(arrOfStr[6]);
                            checkInOutHotel.checkIn(roomNumber, checkInDate, checkOutDate, notes, numberOfGuests, d1.getInputFile());
                        } else {
                            checkInOutHotel.checkIn(roomNumber, checkInDate, checkOutDate, notes, 0, d1.getInputFile());
                        }
                    } catch (Exception e) {
                        System.out.println("Check parameters!");
                    }

                    break;
                case ("checkout"):
                    try {
                        int numberOfRoom = Integer.parseInt(arrOfStr[1]);
                        checkInOutHotel.checkOut(numberOfRoom, d1.getInputFile());
                    } catch (Exception e) {
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("availability"):
                    try {
                        LocalDate dateOfAvailability;

                        if (arrOfStr.length == 2) {
                            dateOfAvailability = LocalDate.parse(arrOfStr[1]);
                        } else {
                            dateOfAvailability = LocalDate.now();
                        }
                        System.out.println("For date " + dateOfAvailability + " the free rooms are: ");
                        availability.checkAvailability(dateOfAvailability, d1.getInputFile());
                    } catch (Exception e) {
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("report"):
                    try {
                        Report report = new Report();
                        report.showReport(LocalDate.parse(arrOfStr[1]), LocalDate.parse(arrOfStr[2]));
                    } catch (Exception e) {
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("find"):
                    try {
                        FindRooms findRooms = new FindRooms();
                        findRooms.findBeds(Integer.parseInt(arrOfStr[1]), arrOfStr[2], arrOfStr[3], d1.getInputFile());
                    } catch (Exception e) {
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("unavailable"):
                    try {
                        Unavailable un1 = new Unavailable();
                        List<String> note = new ArrayList<>();
                        note.add(arrOfStr[4]);
                        note.add(arrOfStr[5]);
                        un1.unavailableRoom(Integer.parseInt(arrOfStr[1]), LocalDate.parse(arrOfStr[2]), LocalDate.parse(arrOfStr[3]),
                                note, d1.getInputFile());
                    }catch(Exception e){
                        System.out.println("Check parameters!");
                    }
                    break;
                case ("exit"):
                    d1.exit();
                    break;
                default:
                    System.out.println("Please enter an available option!");
            }
        } while (!input.equals("exit"));
    }

}


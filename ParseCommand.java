import java.io.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ParseCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 12L;
    private final String[] args;
    private final String filename;
    private final ArrayList<String> s = new ArrayList<>();
    private final Hashtable<String, Product> hashtable = new Hashtable<>();
    private final Date date = new Date();
    private final Scanner in = new Scanner(System.in);
    public ParseCommand(String theFilename, String[] theArgs){
        filename=theFilename;
        args=theArgs;
    }
    public void execute() throws RecursionExeption, IllegalVarValue, IOException {
        s.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = "";
            while ((line = reader.readLine()) != null) {
                s.add(line);
            }

            s.remove(0);
            s.remove(s.size() - 1);
            for (int j = 0; j < s.size(); j += 17) {
                try {
                    Scanner scanner = new Scanner(s.get(j));
                    String buffer = scanner.findInLine("\\d+");
                    Long id = Long.parseLong(buffer);
                    hashtable.forEach((k, v) -> {
                        if (v.getId().equals(id))
                            try {
                                throw new IllegalVarValue("Данные невозможно считать (ID не уникальны)");
                            } catch (IllegalVarValue e) {
                                System.out.println(e.getMessage());
                            }
                    });

                    scanner = new Scanner(s.get(j + 1));
                    String name = scanner.findInLine("\\:\\s*\\\"\\w*\\\"");
                    scanner = new Scanner(name);
                    name = scanner.findInLine("\\w+");
                    if ((name.equals("")) | (name == null))
                        throw new IllegalVarValue("Данные невозможно считать (Имя неверно или null)");

                    scanner = new Scanner(s.get(j + 3));
                    buffer = scanner.findInLine("\\d{1,}");
                    if (buffer == null) {
                        throw new NullPointerException("Данные невозможно считать (x = null)");
                    }
                    Integer x = Integer.parseInt(buffer);

                    scanner = new Scanner(s.get(j + 4));
                    buffer = scanner.findInLine("\\d{1,}");
                    int y = Integer.parseInt(buffer);
                    if (y > 775)
                        throw new IllegalVarValue("Y не должен быть больше 775");

                    scanner = new Scanner(s.get(j + 6));
                    buffer = scanner.findInLine("\\d{2}\\-\\d{2}\\-\\d{4}");
                    if (buffer == null) {
                        throw new NullPointerException("Date = null!");
                    }
                    Date date = ParseDate.parseDate(buffer);


                    scanner = new Scanner(s.get(j + 7));
                    buffer = scanner.findInLine("\\d{1,}");
                    Long price = null;
                    if (buffer != null) {
                        price = Long.parseLong(buffer);
                    }

                    scanner = new Scanner(s.get(j + 8));
                    String partNumber = scanner.findInLine("\\:\\s*\\\"\\w*\\\"");
                    scanner = new Scanner(partNumber);
                    partNumber = scanner.findInLine("\\w+");
                    if (!Checkers.CheckPartNumber.check(hashtable, partNumber)) {
                        throw new IllegalVarValue("Повторяющееся значение номера части или номер части пуст!");
                    }

                    scanner = new Scanner(s.get(j + 9));
                    String unitOfMeasure = scanner.findInLine("\\:\\s*\\\"\\w*\\\"");
                    scanner = new Scanner(unitOfMeasure);
                    unitOfMeasure = scanner.findInLine("\\w+");
                    if (!unitOfMeasure.equals("SQUARE_METERS") && !unitOfMeasure.equals("PCS") && !unitOfMeasure.equals("LITERS") && !unitOfMeasure.equals("MILLILITERS") && !unitOfMeasure.equals("GRAMS"))
                        throw new IllegalVarValue("Данные невозможно считать (UoM не входит в enum!)");
                    Organization organization = new Organization();

                    scanner = new Scanner(s.get(j + 11));
                    buffer = scanner.findInLine("\\d{1,}");
                    long organId = Long.parseLong(buffer);

                    scanner = new Scanner(s.get(j + 12));
                    buffer = scanner.findInLine("\\w+");
                    if (buffer == null) {
                        throw new IllegalVarValue("Данные невозможно считать (NameOfOrganization == null!)");
                    }
                    String nameOfOrganization = buffer;

                    scanner = new Scanner(s.get(j + 13));
                    buffer = scanner.findInLine("\\d{1,}\\.\\d{1,}");
                    if (buffer == null)
                        throw new NullPointerException("Данные невозможно считать (AnnualTurnover<=0 или нечитаем!)");
                    else if (Float.parseFloat(buffer) <= 0)
                        throw new IllegalVarValue("Данные невозможно считать (AnnualTurnover<=0 или нечитаем!)");
                    float income = Float.parseFloat(buffer);

                    scanner = new Scanner(s.get(j + 14));
                    String type = scanner.findInLine("\\w+");
                    if (!type.equals("COMMERCIAL") && !type.equals("PUBLIC") && !type.equals("TRUST") && !type.equals("PRIVATE_LIMITED_COMPANY") && !type.equals("OPEN_JOINT_STOCK_COMPANY"))
                        throw new IllegalVarValue("Данные невозможно считать (Тип производителя нечитаем!)");
                    scanner = new Scanner(s.get(j + 16));
                    buffer = scanner.findInLine("\\:\\s+\\\"\\w+\\\"");
                    scanner = new Scanner(buffer);
                    String key = scanner.findInLine("\\w+");

                    organization.stringToEnum(type);
                    organization.setAnnualTurnover(income);
                    organization.setName(nameOfOrganization);
                    organization.setId(organId);
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(x);
                    coordinates.setY(y);
                    Product product = new Product(id, name, coordinates, price, date, partNumber, unitOfMeasure, organization, key);
                    hashtable.put(key, product);
                } catch (IllegalVarValue | NumberFormatException | NullPointerException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Произошла ошибка! Чтение из файла невозможно!");
                    TimeUnit.SECONDS.sleep(5);
                    System.exit(0);
                }
            }
        } catch (OutOfMemoryError | FileNotFoundException e) {
            System.out.println("Неверный адрес файла!");
            System.exit(0);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка парсинга!");
            Main.main(args);
        }
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return hashtable;
    }
}
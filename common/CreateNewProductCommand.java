package common;

import mainClient.java.*;

import java.io.IOException;
  
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

//TODO: создание продукта не в команде, но с передачей его в команду
public class CreateNewProductCommand implements Command, Serializable {
    private static final long serialVersionUID = 5L;
    private Hashtable<String, Product> table;
    private final String key;
    private Product product;
    private Organization organization;
    public CreateNewProductCommand(String theKey) {
        key = theKey;
    }
    public Product getProduct() {
        return product;
    }
    public void executeClient() throws IllegalArgumentException, InputMismatchException {
        while (true) {
            try {
                Scanner in = new Scanner(System.in);
                Coordinates coordinates = new Coordinates();
                String unit;
                String partNumber = null;
                Long price;
                Integer x;
                int y;
                Date dateNow = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String name;

                while (true) {
                    try {
                        System.out.println("Пожалуйста, введите название");
                        name = in.nextLine();
                        if (name == null | name == "") {
                            throw new IllegalVarValue();
                        }
                        break;
                    } catch (IllegalVarValue | IllegalArgumentException e) {
                        System.out.println("Неверное имя!");
                    }
                }
                while (true) {
                    try {
                        System.out.println("Введите значение X");
                        x = Integer.parseInt(in.nextLine());
                        if (x == null) {
                            throw new NullPointerException();
                        }
                        break;
                    } catch (IllegalArgumentException | InputMismatchException | NullPointerException e) {
                        System.out.println("Неверное значение X! (Нажмите ENTER)");
                        in.nextLine();
                    }
                }
                while (true) {
                    try {
                        System.out.println("Введите значение Y");
                        y = Integer.parseInt(in.nextLine());
                        if (y > 775) {
                            throw new IllegalVarValue();
                        }
                        break;
                    } catch (IllegalArgumentException | InputMismatchException | IllegalVarValue e) {
                        System.out.println("Неверное значение Y! (Нажмите ENTER)");
                        in.nextLine();
                    }
                }
                coordinates.setX(x);
                coordinates.setY(y);
                while (true) {
                    try {
                        System.out.println("Введите значение цены");
                        price = Long.parseLong(in.nextLine());
                        if ((price <= 0) | (price == null)) {
                            throw new IllegalVarValue();
                        }
                        break;
                    } catch (IllegalArgumentException | InputMismatchException | IllegalVarValue e) {
                        System.out.println("Неверное значение цены! (Нажмите ENTER)");
                        in.nextLine();
                    }
                }
                while (true) {
                    try {
                        System.out.println("Введите единицу измерения. Варианты: \n" +
                                "SQUARE_METERS,\n" +
                                "    PCS,\n" +
                                "    LITERS,\n" +
                                "    MILLILITERS,\n" +
                                "    GRAMS\n");
                        unit = in.next();
                        if (!unit.equals("SQUARE_METERS") && !unit.equals("PCS") && !unit.equals("LITERS") && !unit.equals("MILLILITERS") && !unit.equals("GRAMS"))
                            throw new IllegalVarValue();
                        break;
                    } catch (IllegalArgumentException | IllegalVarValue e) {
                        System.out.println("Неверное значение единицы измерения!");
                        in.nextLine();
                    }
                }
                in.nextLine();
                while (true) {
                    try {
                        System.out.println("Введите номер части");
                        partNumber = in.nextLine();
                        if (!Checkers.CheckPartNumber.check(table, partNumber))
                            throw new IllegalVarValue();
                        else
                            break;
                    } catch (IllegalArgumentException | NullPointerException | IllegalVarValue e) {
                        System.out.println("Номер части не уникален или null");
                    }
                }
                CreateNewOrganization creator = new CreateNewOrganization(table);
                creator.executeClient();
                organization = creator.returnCommand();
                product = new Product(0L, name, coordinates, price, dateNow, partNumber, unit, organization, key);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Нажато Ctrl+D, программа завершена!");
                System.exit(0);
            }
        }
    }

    @Override
    public void execute() throws IOException, RecursionExeption, IllegalVarValue, ClassNotFoundException {
        organization.setId(Organization.checkId(table));
        product.setId(Product.checkId(table));
        table.put(key, product);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table = productHashtable;
    }
}

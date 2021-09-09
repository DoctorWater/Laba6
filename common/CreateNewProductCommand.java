package common;

import mainClient.java.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateNewProductCommand implements Command, Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private Hashtable<String, Product> table;
    private final String key;
    public CreateNewProductCommand(String theKey){
        key=theKey;
    }

    public void execute() throws IllegalArgumentException, InputMismatchException {
        Scanner in = new Scanner(System.in);
        Coordinates coordinates = new Coordinates();
        String unit;
        String partNumber = null;
        Long price;
        Integer x;
        int y;
        Date dateNow=new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String name;
        while (true) {
            try {
                System.out.println("Пожалуйста, введите название");
                name = in.nextLine();
                if (name == null | name=="") {
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
            }
            catch (IllegalArgumentException|InputMismatchException|NullPointerException e){
                System.out.println("Неверное значение X!");
                in.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("Введите значение Y");
                y = Integer.parseInt(in.nextLine());
                if (y>775) {
                    throw new IllegalVarValue();
                }
                break;
            }
            catch (IllegalArgumentException | InputMismatchException | IllegalVarValue e){
                System.out.println("Неверное значение Y!");
                in.nextLine();
            }
        }
        coordinates.setX(x);
        coordinates.setY(y);
        while (true) {
            try {
                System.out.println("Введите значение цены");
                price = Long.parseLong(in.nextLine());
                if ((price<=0) | (price==null)) {
                    throw new IllegalVarValue();
                }
                break;
            }
            catch (IllegalArgumentException | InputMismatchException | IllegalVarValue e){
                System.out.println("Неверное значение цены!");
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
            }
            catch (IllegalArgumentException | IllegalVarValue e){
                System.out.println("Неверное значение единицы измерения!");
                in.nextLine();
            }
        }
        in.nextLine();
        try {
            while(true) {
                System.out.println("Введите номер части");
                partNumber = in.nextLine();
                if (!Checkers.CheckPartNumber.check(table, partNumber))
                    throw new IllegalVarValue();
                else
                    break;
            }
        }
        catch(IllegalArgumentException | NullPointerException | IllegalVarValue e){
            System.out.println("Номер части не уникален или null");
        }
        catch (NoSuchElementException e) {
            System.out.println("Нажато Ctrl+D, программа завершена!");
            System.exit(0);
        }
        CreateNewOrganization creator = new CreateNewOrganization(table);
        creator.execute();
        Organization organization = creator.returnCommand();
        Product product = new Product(Product.checkId(table),name,coordinates,price,dateNow,partNumber,unit,organization,key);
        table.put(key,product);
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table=productHashtable;
    }
}

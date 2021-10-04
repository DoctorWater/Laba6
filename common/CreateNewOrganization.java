package common;
import mainClient.java.IllegalVarValue;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreateNewOrganization implements Serializable {
    private static final long serialVersionUID = 24L;
    private final Hashtable<String, Product> table;
    private final Organization organization = new Organization();
    public CreateNewOrganization(Hashtable<String, Product> theTable){
        table=theTable;
    }
    public void executeClient() throws IllegalArgumentException, InputMismatchException {
        while (true) {
            try {
                Scanner in = new Scanner(System.in);
                String name;
                while (true) {
                    try {
                        System.out.println("Пожалуйста, введите название производителя");
                        name = in.nextLine();
                        if (name == null | name.equals("")) {
                            throw new IllegalVarValue();
                        }
                        break;
                    } catch (IllegalArgumentException | NullPointerException | IllegalVarValue e) {
                        System.out.println("Неверное имя!");
                    }
                }
                float annualTurnover;
                while (true) {
                    try {
                        System.out.println("Введите значение оборота");
                        annualTurnover = in.nextFloat();
                        if (annualTurnover <= 0) {
                            throw new IllegalVarValue();
                        }
                        break;
                    } catch (IllegalArgumentException | InputMismatchException | IllegalVarValue e) {
                        System.out.println("Неверное значение оборота!");
                        in.nextLine();
                    }
                }
                String type;
                while (true) {
                    try {
                        System.out.println("Введите единицу измерения. Варианты: \n" +
                                "COMMERCIAL,\n" +
                                "PUBLIC,\n" +
                                "TRUST,\n" +
                                "PRIVATE_LIMITED_COMPANY,\n" +
                                "OPEN_JOINT_STOCK_COMPANY");
                        type = in.next();
                        if (!type.equals("COMMERCIAL") && !type.equals("PUBLIC") && !type.equals("TRUST") && !type.equals("PRIVATE_LIMITED_COMPANY") && !type.equals("OPEN_JOINT_STOCK_COMPANY"))
                            throw new IllegalVarValue();
                        break;
                    } catch (IllegalArgumentException | IllegalVarValue e) {
                        System.out.println("Неверное значение единицы измерения!");
                        in.nextLine();
                    } catch (NoSuchElementException e) {
                        System.out.println("Нажато Ctrl+D, программа завершена!");
                        System.exit(0);
                    }
                }
                organization.stringToEnum(type);
                organization.setName(name);
                organization.setAnnualTurnover(annualTurnover);
                break;
            }
            catch (Exception e){
                System.out.println("При создании организации-производителя произошла ошибка!");
            }
        }
    }
    public Organization returnCommand (){
        return organization;
    }
}


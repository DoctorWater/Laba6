import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

public class Product {

    public Product(Long id, String name, Coordinates coordinates, Long price, Date creationDate, String partNumber, String unitOfMeasure, Organization manufacturer, String key) {
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate=creationDate;
        this.price=price;
        this.partNumber=partNumber;
        this.unitOfMeasure = UnitOfMeasure.valueOf(unitOfMeasure);
        this.manufacturer=manufacturer;
        this.key=key;
    }

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Строка не может быть пустой, Длина строки не должна быть больше 86, Значение этого поля должно быть уникальным, Поле может быть null
    private UnitOfMeasure unitOfMeasure; //Поле может быть null
    private Organization manufacturer; //Поле не может быть null
    private String key;

    public Long getId() { return id; }


    public static Long checkId(Hashtable<String, Product> table) {
        Random random = new Random();
        final Long[] id = new Long[1];
        id[0] = random.nextLong();
        table.forEach((k, v)->{
            if (v.getId().equals(id[0]))
                id[0] = random.nextLong();

        });
        return id[0];
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getKey() { return key; }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public Long getPrice() {
        return price;
    }
    public String getPartNumber() {
        return partNumber;
    }
    public Organization getManufacturer() {
        return manufacturer;
    }
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }
    public Integer getX(){ return coordinates.getX();}
    public int getY(){ return coordinates.getY();}
    public String getUnitOfMeasureString(){return unitOfMeasure.toString();}
    public Organization getOrganization (){return manufacturer;}
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public void stringToEnum(String s) {
        this.unitOfMeasure=unitOfMeasure.valueOf(s);
    }
}

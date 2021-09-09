package mainClient.java;

import common.Product;

import java.io.Serial;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Random;

public class Organization implements StructureInterface, Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private Long id;
    private String name;
    private Float annualTurnover;
    private OrganizationType type;
    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public String getType() {
        return type.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    public void stringToEnum(String type) {
        this.type = OrganizationType.valueOf(type);
    }
    public static Long checkId(Hashtable<String, Product> table) {
        Random random = new Random();
        final Long[] id = new Long[1];
        id[0] = random.nextLong();
        table.forEach((k, v)->{
            if (v.getOrganization().getId()== id[0])
                id[0] = random.nextLong();
        });
        return id[0];
    }
}

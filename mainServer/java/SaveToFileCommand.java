package mainServer.java;

import common.Command;
import common.Product;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Hashtable;


public class
 SaveToFileCommand implements Command, Serializable {
     
    private static final long serialVersionUID = 19L;
    private final String filename;
    private Hashtable<String, Product> table;
    public SaveToFileCommand(String theFilename){
        filename=theFilename;
    }
    public void execute()
    {

        SimpleDateFormat formatForDate = new SimpleDateFormat("dd-MM-yyyy");
        final int[] counter = {0};
        try {
            FileWriter fstream1 = new FileWriter(filename);
            BufferedWriter out1 = new BufferedWriter(fstream1);
            out1.write("");
            out1.close();
        } catch (Exception e)
        {System.err.println("Error in file cleaning: " + e.getMessage());}
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("{"+ "\n");
            table.forEach((k, v)->{
                try {
                    counter[0]++;
                    writer.write("\"id\": " + "\"" + v.getId() + "\","+ "\n");
                    writer.write("\"name\": " + "\"" + v.getName() + "\","+ "\n");
                    writer.write("\"coordinates\": ["+ "\n");
                    writer.write("\"" + v.getX() + "\","+ "\n");
                    writer.write("\"" + v.getY() + "\""+ "\n");
                    writer.write("],"+ "\n");
                    writer.write("\"date\": " + "\"" + formatForDate.format(v.getCreationDate()) + "\","+ "\n");
                    writer.write("\"price\": " + "\"" + v.getPrice() + "\","+ "\n");
                    writer.write("\"partNumber\": " + "\"" + v.getPartNumber() + "\","+ "\n");
                    writer.write("\"unitOfMeasure\": " + "\"" + v.getUnitOfMeasure() + "\","+ "\n");
                    writer.write("\"manufacturer\": ["+ "\n");
                    writer.write("\"" + v.getManufacturer().getId() + "\","+ "\n");
                    writer.write("\"" + v.getManufacturer().getName() + "\","+ "\n");
                    writer.write("\"" + v.getManufacturer().getAnnualTurnover() + "\","+ "\n");
                    writer.write("\"" + v.getManufacturer().getType() + "\""+ "\n");
                    writer.write("],"+ "\n");
                    if (counter[0]==table.size())
                        writer.write("\"key\": " + "\"" + v.getKey() + "\""+ "\n");
                    else
                        writer.write("\"key\": " + "\"" + v.getKey() + "\","+ "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            writer.write("}");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Hashtable<String, Product> returnTable() {
        return table;
    }

    @Override
    public void setProductHashtable(Hashtable<String, Product> productHashtable) {
        this.table=productHashtable;
    }
}

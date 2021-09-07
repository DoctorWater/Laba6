import java.io.Serializable;
import java.util.Hashtable;

public class HelpCommand implements Command, Serializable {
    private Hashtable<String, Product> table;
    public HelpCommand(Hashtable<String, Product> table){
        this.table=table;
    }
    public void execute(){
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "history : вывести последние 11 команд (без их аргументов)\n" +
                "count_greater_than_part_number partNumber : вывести количество элементов, значение поля partNumber которых больше заданного\n" +
                "filter_greater_than_unit_of_measure unitOfMeasure : вывести элементы, значение поля unitOfMeasure которых больше заданного\n" +
                "print_field_descending_price : вывести значения поля price всех элементов в порядке убывания");
    }

    @Override
    public Hashtable<String, Product> returnTable() {
        return table;
    }

}

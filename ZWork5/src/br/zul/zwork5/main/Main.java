package br.zul.zwork5.main;

import br.zul.zwork5.log.ZLog;
import br.zul.zwork5.log.ZLogger;
import br.zul.zwork5.reflection.ZPackage;
import br.zul.zwork5.util.ZList;
import java.io.IOException;

/**
 *
 * @author luiz.silva
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ZLog.setDefaultPrintDateTimeOnConsole(true);
        ZLogger logger = new ZLogger(Main.class);
        logger.info.println("ZWork 4.0 (2019)");
        logger.info.println("Developed by Zul");
        if (testListClasses()){
            ZLog.i(Main.class).println("Tudo ok!");
        } else {
            ZLog.i(Main.class).println("Erro!");
            ZPackage.fromClass(TestEx1.class).classStream(true).forEach(c->{
                System.out.println(c.getName());
            });
        }
    }

    private static boolean testListClasses() throws IOException, ClassNotFoundException {
        ZList<Class<?>> expClassList = ZList.asList(TestEx1.class, TestEx1.class);
        ZList<Class<?>> classList = ZPackage.fromClass(TestEx1.class).classStream(true).collect(ZList.getCollector()).map(c->c.getType());
        return expClassList.equals(classList);
    }
    
}

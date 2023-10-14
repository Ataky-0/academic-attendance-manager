package org.academic;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class App 
{
  public static void main( String[] args )
  {
    System.setProperty("file.encoding", "UTF-8");
    Map<String,Runnable> functionsMap = new HashMap<>(); // Cria um hashmap para simular um metatable em Lua :)

    functionsMap.put("--help",Help::print); // Adiciona funções ligadas à strings (argumentos)
    functionsMap.put("--version",Help::printVersion);
    functionsMap.put("menu",() -> {
      try {
        Menu.init();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    if (args.length > 0 && functionsMap.containsKey(args[0])){ // Lida com os argumentos
      Runnable funcRunnable = functionsMap.get(args[0]);
      funcRunnable.run();
    } else {
      Help.print();
    }
  }
}
package org.academic;
import java.util.HashMap;
import java.util.Map;

public class App 
{
  public static void main( String[] args )
  {
    Map<String,Runnable> functionsMap = new HashMap<>(); // Cria um hashmap para simular um metatable em Lua :)

    functionsMap.put("--help",Help::print); // Adiciona funções ligadas à strings (argumentos)
    functionsMap.put("--version",Help::printVersion);
    functionsMap.put("menu",Menu::init);

    if (args.length > 0 && functionsMap.containsKey(args[0])){ // Lida com os argumentos
      Runnable funcRunnable = functionsMap.get(args[0]);
      funcRunnable.run();
    } else {
      Help.print();
    }
  }
}
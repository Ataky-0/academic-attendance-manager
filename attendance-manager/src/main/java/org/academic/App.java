package org.academic;
import java.util.HashMap;
import java.util.Map;

public class App 
{
  public static void main( String[] args )
  {
    Map<String,Runnable> functionsMap = new HashMap<>();

    functionsMap.put("--help",Help::print);
    functionsMap.put("--version",Help::printVersion);

    if (args.length > 0 && functionsMap.containsKey(args[0])){
      Runnable funcRunnable = functionsMap.get(args[0]);
      funcRunnable.run();
    } else {
      Help.print();
    }
  }
}


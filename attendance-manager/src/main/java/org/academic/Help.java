package org.academic;

public class Help
{
  public static String version = "1.0-ALPHA";
  public static void print(){
    System.out.println("Uso: attma [OPÇÕES]");
    System.out.println("Opções:");
    System.out.println("  --help         Exibe mensagem de ajuda");
    System.out.println("  --version      Exibe versão do programa");
    System.out.println("  menu           Inicia TUI para interagir com o sistema");
  }
  public static void printVersion(){
    System.out.println("Versão: "+version);
  }
}

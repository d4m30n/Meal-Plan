package menu;

import java.util.Scanner;

public class Menu{
  //sets the output mode 
  //0 - normal output
  //1 - output fitness with results
  //2 - debug mode
  private static int MODE = 0;
  
  public static int getMode(){return MODE;}
  
  public static void setMode(){
    while(true){
      System.out.println();
      System.out.println("0) Normal Output");
      System.out.println("1) Normal Output with Best Fitness");
      System.out.println("2) Debug Mode");
      System.out.print("Select Mode: ");
      try{
        Scanner sc = new Scanner(System.in);
        int useroption = sc.nextInt();
        if(useroption < 0 || useroption > 3){continue;}
        MODE = useroption;
        System.out.println();
        break;
      }
      catch(Exception e){continue;}
    }
  }
  
  public static void pause(){
    try{
      System.in.read();
    }
    catch (Exception e){}
  }
}

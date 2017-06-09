package people;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class People{

  //private static veriables
  private static int numberOfPeople = 0;
  
  public static int getNumberOfPeople(){return numberOfPeople;}
  
  //private local veriables
  private String name;
  private String dislikes[];
  //private int code;

  //public methrods

  /**
   *  Constructor takes a string array and splits it up into name and
   *  dislikesdiants.
   *  @param people string array of dislikesdiants
   *  @param code the assigned byte code
   **/
  public People(String[] people) throws Exception{
    dislikes = new String[people.length-1];
    //this.code = code;
    if(people == null){throw new Exception("people String array is null");}
    this.name = people[0];
    for(int i = 1; i < people.length; i++){
      dislikes[i-1] = people[i];
    }
    if(dislikes.length == 0){throw new Exception("No dislikesadents for people");}
    numberOfPeople++;
  }

  /**
   *  Gets the assigned byte code
   **/
  //public int getCode(){return this.code;}
  /**
   *  Gets the name of the people
   **/
  public String getname(){return this.name;}
  public String[] getDislikes(){return this.dislikes;}

  /**
   *  Prints out the list of dislikes.
   **/
  public void printdislikesdiants(){
    System.out.println();
    System.out.println("people: "+name);
    for(String item : dislikes){
      System.out.println(item);
    }
  }
  
  /**
   *  A methord used to load in a list of recipes and there
   *  ingrediants from a CSV file given in the arguments.
   *  @param csvFile The location of the csv file.
   **/
  public static ArrayList<People> loadPeople(String csvFile)
  throws IOException, FileNotFoundException{
    //create a new arrayList for the new recipes.
    ArrayList<People> people = new ArrayList<>();
    //load in the csv file and read line by line
    BufferedReader br = new BufferedReader(new FileReader(csvFile));
    String line = "";
    while((line = br.readLine()) != null){
      //if there are no ingrediants the constructor will not create
      //class
      try{
        People tmp = new People(line.split(","));
        //pass the csv file the processing is handled in the 
        //constructor of the class.
        people.add(tmp);
      }
      catch(Exception e){System.err.println(e);}
    }
    br.close();
    return people;
  }
}

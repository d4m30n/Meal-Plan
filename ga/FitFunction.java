package ga;

import org.jgap.IChromosome;
import org.jgap.Gene;
import org.jgap.FitnessFunction;

import recipes.Recipe;
import people.People;

import menu.Menu;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class FitFunction extends FitnessFunction{
  private static final int HIGHCLASH = 10;
  private HashMap<Integer,Recipe> recipelist;
  private IChromosome subject;
  
  public FitFunction(HashMap<Integer,Recipe> recipelist){
    this.recipelist = recipelist;
  }
  
  protected Recipe getName(int id){
    return recipelist.get(id);
  }
  
  @Override
  protected double evaluate(IChromosome subject){
    HashMap<Integer,Integer> daysSeen = new HashMap<>();
    int ingreClash = 0;
    int totalIngre = 0;
    int multipleClash = 0;
    boolean match = false;
    for(int j = 0;j < subject.size(); j++){
      Gene gene = subject.getGene(j);
      int id = (int) gene.getAllele();
      
      for(int i = 0; i < daysSeen.size(); i++){
        //increce the total by 2 if a replicated recipe is found.
        if(daysSeen.get(id) != null){
          match = true;
          if(daysSeen.get(id) > 5){multipleClash += 1;}
          else{multipleClash += HIGHCLASH;}
          daysSeen.put(id,0);
          break;
        }
      }
      
      for(Map.Entry<Integer,Integer> a : daysSeen.entrySet()){
        a.setValue(a.getValue() + 1);
      }
      if(match == false){
        daysSeen.put(id,0);
      }
      match = false;
      
      ingreClash += recipelist.get(id).getClashes();
      totalIngre += recipelist.get(id).getNumIngre();
    }
    double nmulClash = (double) multipleClash/(subject.size()*HIGHCLASH);
    double nClash = (double) ingreClash*1/(totalIngre*People.getNumberOfPeople());
    if(Menu.getMode() == 2){
      System.err.println("People:"+People.getNumberOfPeople());
      System.err.println("CSize:"+subject.size()+"\tMClash:"+multipleClash+"\tNClash:"+nmulClash);
      System.err.println("ISize:"+totalIngre+"\tIClash:"+ingreClash+"\tNClash:"+nClash);
      System.err.println("Total:"+nmulClash+nClash);
      Menu.pause();
      System.err.println();
    }
    return nmulClash + nClash;
  }
}

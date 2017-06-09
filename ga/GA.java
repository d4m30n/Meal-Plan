package ga;

import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.Genotype;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.BaseGeneticOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.SwappingMutationOperator;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.AveragingCrossoverOperator;
import org.jgap.impl.DefaultConfiguration;

import java.util.List;
import java.util.Random;

import menu.Menu;

import recipes.Recipe;

public class GA{
  //private veriables
  private int days; //holds number of day you want recipes for
  private FitFunction ff; //The fitness function to use
  private int NUMBER_OF_EVOLUTIONS; //how meny evolutions to use
  
  public GA(int days,FitFunction ff, int NUMBER_OF_EVOLUTIONS){
    this.days = days;
    this.ff = ff;
    this.NUMBER_OF_EVOLUTIONS = NUMBER_OF_EVOLUTIONS;
  }
  
  /**
   *  the main entrence for the user
   *  it dose throw and exception that is passed up form the private
   *  classes that are used below.
   **/
  public Recipe[] start() throws InvalidConfigurationException{
    //gets the best chromosome
    IChromosome bestFound = evolve(configGenotype());
    Recipe[] result = new Recipe[days];
    for(int i = 0; i < result.length; i++){
      Gene gene = bestFound.getGene(i);
      result[i] = ff.getName((int) gene.getAllele());
    }
    return result;
  }
  
  /**
   *  This configures all the required parameters that need to be run
   *  for the project to work such as the chromosomes and population.
   *  @return the Genotype for the population.
   **/
  private Genotype configGenotype() throws InvalidConfigurationException{
    Configuration.reset();
    //sets up a new GA configuration
    Configuration gaConf = new DefaultConfiguration();
    Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
    //Sets the fitness evaluator so that lower is better
    gaConf.setFitnessEvaluator(new DeltaFitnessEvaluator());
    
    //clears the default operator
    gaConf.getGeneticOperators().clear();
    BaseGeneticOperator option = null;
    switch(Menu.getMutation()){
      case 0: option = new SwappingMutationOperator(gaConf);break;
      case 1: option = new CrossoverOperator(gaConf);break;
      case 2: option = new AveragingCrossoverOperator(gaConf);break;
      default: throw new InvalidConfigurationException("Program Not Setup");
    }
    gaConf.addGeneticOperator(option);
    
    //keep the fittest value found
    gaConf.setPreservFittestIndividual(true);
    gaConf.setKeepPopulationSizeConstant(false);
    
    gaConf.setPopulationSize(1000);
    Genotype genotype;
    
    //create the tmplate for a chromosome
    IChromosome chromosome = new Chromosome(gaConf, new IntegerGene(gaConf), days);
    gaConf.setSampleChromosome(chromosome);
    gaConf.setFitnessFunction(ff);
    
    genotype = Genotype.randomInitialGenotype(gaConf);
    List chromosomes = genotype.getPopulation().getChromosomes();
    //set the values for the chromosome here.
    //not sure how this should be set up
    Random rand = new Random();
    for(Object ch : chromosomes){
      IChromosome chrom = (IChromosome) ch;
      for(int j = 0;j < chrom.size(); j++){
        Gene gene = chrom.getGene(j);
        gene.setAllele(rand.nextInt(Recipe.getNumRecipes())); //change this to change the value
      }
    }
    return genotype;
  }
  
  /**
   *  The evolution loop that runs the GA configuration.
   *  @param genotype The population to evolve.
   *  @return the best chromosome in the population
   **/
  private IChromosome evolve(Genotype genotype){
    //loops till the end of GA evolution
    for(int i = 0; i<NUMBER_OF_EVOLUTIONS;i++){
      genotype.evolve();
      List chromosomes = genotype.getPopulation().getChromosomes();
    }
    IChromosome best = genotype.getFittestChromosome();
    if(Menu.getMode() == 1){
      System.out.println("Best Fitness Value:"+best.getFitnessValue());
    }
    //returns the best chromosome to be interpriated
    return best;
  }
}

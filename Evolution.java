import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Evolution {
	
	public Individual[] pop;
	public String optimalAttributes;
	int numAttributes;
	
	public Evolution(int population, int numAttributes){
		pop = new Individual[population];
		this.numAttributes = numAttributes;
		optimalAttributes = generateAttributes(numAttributes);
		System.out.println("0: " + optimalAttributes + " -- Optimal attributes");
		generatePopulation(population, numAttributes);
		determineFitness(pop);
	}
	
	/*
	 * generations - the number of generations to move forward by
	 * cullPercent - the amount of the original population that will be removed
	 * 
	 * Creates a new population array by taking individuals that will not be culled 
	 * and splicing attributes at an index
	 */
	public void incrementGeneration(int generations, int cullPercent){
		sortMembers();
		Individual[] nextGen = new Individual[pop.length];
		
		int livingPopMembers = (int) (pop.length * (1.0 - (cullPercent / 100.0)));
		
		for(int n = 0; n < generations; n++){
			sortMembers();
			for(int i = 0; i < pop.length; i++){
				int individual1 = (int)(Math.random() * (livingPopMembers));
				int individual2 = (int)(Math.random() * (livingPopMembers));
				int spliceIndex = (int) (Math.random() * numAttributes);
				
				String nextAttributeStr = pop[individual1].getAttributes().substring(0, spliceIndex);
				nextAttributeStr = nextAttributeStr + pop[individual2].getAttributes().substring(spliceIndex, numAttributes);
				Individual nextIndividual = new Individual(nextAttributeStr);
				nextIndividual.mutate(1);
				nextGen[i] = nextIndividual;
			}
			pop = nextGen.clone();
			determineFitness(pop);
			System.out.println("Gen " + n + " fitness: " + populationAverageFitness());
		}
	}
	
	public double populationAverageFitness(){
		int totalFitness = 0;
		for(int i = 0; i < pop.length; i++){
			totalFitness += pop[i].getFitnessInt();
		}
		return totalFitness / pop.length; 
	}
	
	public void determineFitness(Individual[] pop){
		for(int i = 0; i < pop.length; i++){
			determineFitness(pop[i]);
		}
	}
	
	//Determines fitness score of an individual
	//Score is based on number of bits that match the optimal attributes
	public int determineFitness(Individual indi){
		int fitness = 0;
		String indiAttr = indi.getAttributes();
		for(int i = 0; i < numAttributes; i++){
			if(optimalAttributes.charAt(i) == indiAttr.charAt(i)){
				fitness++;
			}
		}
		indi.setFitness(fitness);
		return fitness;
	}
	
	public String fittestMember(){
		int fittest = 0;
		int fitness = 0;
		for(int i = 0; i < pop.length; i++){
			if(pop[i].getFitnessInt() > fitness){
				fitness = pop[i].getFitnessInt();
				fittest = i;
			}
		}
		System.out.println("Fittest: " + fittest + " -- " + pop[fittest].getAttributes() + " -- Fitness: "+ pop[fittest].getFitnessInt());
		return pop[fittest].getAttributes();
	}
	
	public void listMembers(){
		int fittestIndex = -1;
		int fittest = -1;
		for(int i = 0; i < pop.length; i++){
			Individual indi = pop[i];
			if(indi.getFitnessInt() > fittest){ 
				fittest = indi.getFitnessInt();
				fittestIndex = i;
			}
			System.out.println(i+1 + ": " + indi.getAttributes() + " -- " + indi.getFitnessStr());
		}
		System.out.println("Fittest member: #" + fittestIndex + " -- Fitness: " + fittest);
	}
	
	private void generatePopulation(int population, int numAttributes){
		for(int i = 0; i < population; i++){
			Individual indi = new Individual(generateAttributes(numAttributes));
			indi.getAttributes();
			pop[i] = indi;
		}
	}
	
	private String generateAttributes(int numAttributes){
		StringBuilder attr = new StringBuilder();
		for(int j = 0; j < numAttributes; j++){
			attr = attr.append(String.valueOf((int)(Math.random() * 2)));
		}
		return attr.toString();
	}
	
	public void sortMembers(){
		ArrayList<Individual> popList = new ArrayList<Individual>(Arrays.asList(pop));
		Collections.sort(popList, new FitnessComparator());
		//for(int i = 0; i < popList.size(); i++){ System.out.println(popList.get(i).fitness); }
		pop = new Individual[popList.size()];
		pop = popList.toArray(pop);
	}
	
	class FitnessComparator implements Comparator<Individual> {
	    @Override
	    public int compare(Individual a, Individual b) {
	        if(a.fitness < b.fitness){
	        	return 1;
	        }
	        else if(a.fitness == b.fitness){
	        	return 0;
	        }
	        else{
	        	return -1;
	        }
	        
	    }
	}
	
}


public class Individual{
	
	public int fitness = -1;
	private char[] attributes;
	
	//String of 0's and 1's representing attributes
	public Individual(String attributeStr){
		attributes = attributeStr.toCharArray();
	}
	
	//Double from 1 to 100 representing % chance
	public void mutate(double mutateChance){
		if(mutateChance < 0 || mutateChance > 100){
			System.out.println("Enter a valid mutatation value from 0-100");
		}
		else{
			double mutate = Math.random() * 100;
			if(mutate < mutateChance){
				int mutateIndex = (int) (Math.random() * attributes.length);
				//System.out.println("Individual has mutated at index " + mutateIndex);
			}
		}
	}
	
	//Returns all attributes
	public String getAttributes(){
		String attr = String.valueOf(attributes);
		//System.out.println(attr);
		return attr;
	}
	
	//Changes the attribute at the index to the opposite
	public void changeAttribute(int attributeIndex){
		if(attributes[attributeIndex] == 0){
			attributes[attributeIndex] = '1';
		} else{
			attributes[attributeIndex] = '0';
		}
	}
	
	//
	public void setFitness(int fitness){
		this.fitness = fitness;
	}
	
	public String getFitnessStr(){
		if(fitness < 0){
			return "fitness not determined";
		} else{ 
			return String.valueOf(fitness);
		}
	}
	
	public int getFitnessInt(){
		return fitness;
	}

}

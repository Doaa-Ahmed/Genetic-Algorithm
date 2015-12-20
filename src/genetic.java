/*Author: Doaa' Ahmed*/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class genetic {

static int popSize= 5;
static int chromlength=10;
static chrom[] population= new chrom [popSize];
static chrom[] newpop= new chrom[popSize];
static double mutRate=0.1;
static double xoRate= 0.5;
static int run_num= 30;

//get s1 and s2 from chromosome
static void getss(chrom c){
	
	if (c.getB().length()<10){
		int k= 10-c.getB().length();
		char[] st= new char[k];
		for (int i=0; i<k;i++)
			st[i]='0';
		String s=String.valueOf(st);
			//System.out.println(s);
		StringBuilder sb= new StringBuilder();
		sb.append(s);
		sb.append(c.getB());
		c.setB( sb.toString());
		int x1= Integer.parseInt(c.getB(), 10);
		c.setC( BtoD(x1));
	}
	String s_1= c.getB().substring(0, 5);
	String s_2= c.getB().substring(5);
	
	int x1= Integer.parseInt(s_1, 10);
	int x2= Integer.parseInt(s_2, 10);
	 c.setS1(BtoD(x1));  // to decimal 
	 c.setS2(BtoD(x2));

	}

// set function of each chromosome
static void setf(chrom c){
	//10^6-(625-(s1-25)^2) *(1600-(s2-10)^2)*sin((s1)*pi/10)*sin((s2)*pi/10)
	int s1= c.getS1();
	int s2= c.getS2();
	c.setF( Math.pow(10, 6)-(625-Math.pow((s1-25), 2)) *(1600-Math.pow((s2-10),2))*Math.sin((s1)*Math.PI/10)*Math.sin((s2)*Math.PI/10));
}
// set probability of each chromosome 
static void setp (chrom[] ch){
	double total=0.0;
	for (int i=0; i< popSize; i++)
		total+= ch[i].getF();
	for(int i=0; i<popSize; i++)
		ch[i].setProb( ch[i].getF()/total);
}
// calculate the commulative probability of each chromosome in the population  
static void setcump (chrom[] pop){
	double cum_p=0.0;
	for (int i=0; i< popSize ; i++)
	{
		pop[i].setCum_prob(cum_p+pop[i].getProb());
		cum_p=pop[i].getCum_prob();
	}
}
/*****************ROULETTE WHEEL*******************/
// function generates random numbers as the popsize ,, then see where it lies
static void RouletteWheel(){
	
double[]R = new double[popSize];
for (int i=0; i< popSize; i++)
{
R[i] = (double) (Math.random() * (1 - 0)) + 0;
}
int j=0;
while (j<popSize){
   for (int i=0; i< popSize; i++)
   {
	   if(i+1<popSize){
	 if (R[j]>population[i].getProb()&& R[j]< population[i+1].getProb())
	 {
		newpop[j].setC(population[i+1].getC());
		break;
	 }
	 else ;
	   }
   }
   j++;
}
}
// function of pre-Xover
static void precrossover(){
	double[]R = new double[popSize];
	int count=0; // counts parents
	for (int i=0; i< popSize; i++)
	{
		R[i]= (double) (Math.random() * (1 - 0)) + 0;
        if (R[i]<xoRate)
        	{
        	newpop[i].setParent(true);  
        	count++;
        	}
	}
	int[] parent= new int[count]; // keeps the indices of the parent chromosomes
	for (int j=0, i=0; j<popSize; j++)
	{
		if (newpop[j].isParent())
		{
			parent[i]= j;
			i++;
		}
	}
	//generate random numbers as count; between 1 to length of chromosomes -1 // this is for the cutting
	int[] c= new int[count]; // array of random numbers
	for (int i=0; i<count; i++)
	{
	c[i]= (int) (Math.random() * ((chromlength-1) - 0)) + 0;
	}
		for (int k=0; k< count; k++)
		{
			if (k==count-1)
				{newpop[parent[k]].setB(crossover (c[k],newpop[parent[k]],newpop[parent[0]]).getB());
				newpop[parent[k]].setC(crossover (c[k],newpop[parent[k]],newpop[parent[0]]).getC());
				}
				
			else
			{
				newpop[parent[k]].setB(crossover (c[k],newpop[parent[k]],newpop[parent[k+1]]).getB());
			newpop[k].setC(crossover (c[k],newpop[k],newpop[k+1]).getC());
			}			
		}
}
// function of crossover 
static chrom crossover(int cut, chrom c1, chrom c2){
	chrom child = new chrom(0,0,0,"0000000000",0.0);	

	if (c1.getB().length()<10){
		int k= 10-c1.getB().length();
		char[] st= new char[k];
		for (int i=0; i<k;i++)
			st[i]='0';
		String s=String.valueOf(st);
			//System.out.println(s);
		StringBuilder sb= new StringBuilder();
		sb.append(s);
		sb.append(c1.getB());
		c1.setB( sb.toString());
		int x1= Integer.parseInt(c1.getB(), 10);
		c1.setC( BtoD(x1));
	}
	if (c2.getB().length()<10){
		int k= 10-c2.getB().length();
		char[] st= new char[k];
		for (int i=0; i<k;i++)
			st[i]='0';
		String s=String.valueOf(st);
			//System.out.println(s);
		StringBuilder sb= new StringBuilder();
		sb.append(s);
		sb.append(c2.getB());
		c2.setB( sb.toString());
		int x1= Integer.parseInt(c2.getB(), 10);
		c2.setC( BtoD(x1));
	}
		
	String s_1= (c1.getB()).substring(0, cut);
	String s_2= (c2.getB()).substring(cut);
	
	StringBuilder sb= new StringBuilder();
	sb.append(s_1);
	sb.append(s_2);
	child.setB( sb.toString());
	int x1= Integer.parseInt(child.getB(), 10);
	child.setC( BtoD(x1));
	return child;
}
// Mutation
public static void mutation (){
	int total_gen= chromlength*popSize;
	
	int numMutations =(int) mutRate * total_gen;
	int[] r =new int[numMutations];
	int[] chr= new int[numMutations];
	int[] gene=new int [numMutations];
	for (int i=0; i<numMutations; i++)
	{
		
		r[i]= (int) (Math.random() * (total_gen - 1)) + 1;;
	// to know which chromosome which gene
	chr[i] = (int) Math.ceil(r[i]/chromlength);
	gene[i]= ((r[i]-1)% chromlength);
	
	double ran= (double) (Math.random() * (1- 0)) + 0;
	//
	String my = newpop[chr[i]].getB();
	char[] myChars = my.toCharArray();
	if (ran<0.5)	
	myChars[gene[i]] = '0';
	else 
		myChars[gene[i]] = '1';
	newpop[chr[i]].setB(String.valueOf(myChars));	
	int x1= Integer.parseInt(newpop[chr[i]].getB(), 10);
	newpop[chr[i]].setC(BtoD(x1));  // to decimal 
		 	
	}
	
}
// get the decimal value of s1 and s2
static int BtoD(int binary){ 
	int decimal = 0;
	int power = 0;
	while(true){
		if(binary == 0){
			break;
			}
		else {
			int tmp = binary%10; 
			decimal += tmp*Math.pow(2, power); 
			binary = binary/10; power++;
			} 
		} 
	return decimal; 
		} 

// get the binary of the generated chromosome
static String DtoB(int n) {
    if (n == 0) {
        return "0";
    }
    String binary = "";
    while (n > 0) {
        int rem = n % 2;
        binary = rem + binary;
        n = n / 2;
    }
    return binary;
}
/***********************************************  MAIN  ***********************************************/
			public static void main (String[] args){
	//1- initialize population
				int r = 0;
				for (int i=0; i<popSize; i++)
				{
					 r=(int) (Math.random() * (1023 - 0)) + 0;
			population[i]=  new chrom(0,0,r,"0000000000",0.0);
			population[i].setB(DtoB(r));	
			//2- getss
			getss(population[i]);

				}
				int iterations=0;
				
				while (iterations<10){
					System.out.println("\n");
					System.out.println("____________Iteration number " + (iterations+1) + " ____________");
				for (int i=0; i<popSize; i++)
				{		
	//3- get f of each 
				setf(population[i]);
			/////// Introduce new population
				newpop[i]=new chrom(population[i].getS1(),population[i].getS2(),population[i].getC(),population[i].getB(),population[i].getF());
				}
	//4- set probability of each chromosome
				setp(population);
	// 5- set the cummulative probability of each chromosome
				setcump(population);
				
	//6- Roulette wheel selection
				
				RouletteWheel();
    //7- CrossOver
				precrossover ();
    //8- Mutation
				mutation ();
				
				for (int i=0; i<popSize; i++)
				{	
				getss(newpop[i]); 
				setf(newpop[i]);
				System.out.print("old fitness of " + i + " is: " +population[i].f + " ");
				System.out.print ("---\n");
				System.out.print("new fitness of " + i + " is: " +newpop[i].f + " ");
				System.out.print ("---\n");
			 if (newpop[i].f>population[i].f)
					{
				 population[i].setS1(newpop[i].getS1());
					population[i].setS2(newpop[i].getS2());
					}
				}
				
				iterations++;
				}	
				int best=0;
				for (int i=0; i<popSize; i++)
					{if (population[i].getF()>population[best].getF())
						best=i;
					}
System.out.println("           <<<<<<Best Fitness is at " + population[best].getS1()+ " and " +population[best].getS2()+ " and equal: "+ population[best].getF()+" >>>>>>>");
				
				
				
try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("D:/D memory/Doaa' Ahmed/GeneticAlg/src/myfile.txt", true)))) {
    out.println( population[best].getF()+ " "+population[best].getS1()+ " " +population[best].getS2());
}catch (IOException e) {
    
}			
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
}
}

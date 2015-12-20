/*Author: Doaa' Ahmed*/
public class chrom {
int s1;
int s2=0;
 int c=0; // its value
 String b; // value in binary
double f=0.0; // function
double prob=0.0;
double cum_prob=0.0;
boolean parent=false;
public chrom(int i, int j, int k, String string, double d) {
	setS1(i);
	setS2 (j);
	setB (string);
	setC(k);
	setF(d);
	
	// TODO Auto-generated constructor stub
}
//int index; // its index in the population
public int getS1() {
	return s1;
}
public void setS1(int s1) {
	this.s1 = s1;
}
public int getS2() {
	return s2;
}
public void setS2(int s2) {
	this.s2 = s2;
}
public int getC() {
	return c;
}
public void setC(int c) {
	this.c = c;
}
public String getB() {
	return b;
}
public void setB(String b) {
	this.b = b;
}
public double getF() {
	return f;
}
public void setF(double f) {
	this.f = f;
}
public double getProb() {
	return prob;
}
public void setProb(double prob) {
	this.prob = prob;
}
public double getCum_prob() {
	return cum_prob;
}
public void setCum_prob(double cum_prob) {
	this.cum_prob = cum_prob;
}
public boolean isParent() {
	return parent;
}
public void setParent(boolean parent) {
	this.parent = parent;
}
}

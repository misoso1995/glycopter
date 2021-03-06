/*
 * 6 August 2016
 * Glycopter project
 * This class creates bond objects between 2 atoms if they are close enough to be bonded (coded in molecule class).
 */

package resources;


public class Bond {

	int bondID;
	static int noBonds;
	Atom atom1, atom2;
	
	public Bond(Atom a1, Atom a2){
		bondID = noBonds;
		incBonds();
		atom1 = a1;
		atom2 = a2;
	}
	
	public static void incBonds(){
		noBonds ++;
	}
	
	public int getBonds(){
		return noBonds;
	}
	
	public int getBondID(){
		return bondID;
	}
	
	public Atom getAtom1(){
		return atom1;
	}
	
	public Atom getAtom2(){
		return atom2;
	}
	
	public String toString(){
		return "atom1: " + atom1.id + ", atom2: " + atom2.id; 
	}
}

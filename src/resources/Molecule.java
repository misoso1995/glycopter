/*
 * 6 August 2016
 * Glycopter project
 * this is the main class that coordinates the molecule and all the different aspects of the molecule. It has
 * lists for the atoms, the bonds, the non-bonding interactions, and the dihedral angles. 
 */

package resources;

import java.util.ArrayList;


public class Molecule {

	double distance;
	ArrayList<Atom> atoms;
	ArrayList<Bond> bondList;
	ArrayList<Interaction> interactionList;
	ArrayList<DihedralAngle> dihedralList;
	Bond bond;
	Interaction interaction;
	Atom atom1;
	Atom atom2;
	
	public Molecule(ArrayList<Atom> input){
		distance = 0;
		bondList = new ArrayList<Bond>();
		dihedralList = new ArrayList<DihedralAngle>();
		atoms = input;
		bond = null;
		atom1 = null;
		atom2 = null;
		identifyBonds(input);
		identifyDihedrals(input);
		/*
		System.out.println("atoms");
		for (Atom a: atoms ){
			System.out.println(a.id);
		}
		System.out.println("bonds");
		for (Bond b: bondList ){
			System.out.println(b);
		}
		*/
		System.out.println("dihedrals");
		for (DihedralAngle d: dihedralList ){
			System.out.println(d);
		}
		
	}
	
	// I wasn't sure how we check what type of atom it is (Will need to look at Michelle's code for that)
	public void identifyBonds(ArrayList<Atom> atoms){
		
		for (int i = 0; i < (atoms.size() - 1); i++){
			atom1 = atoms.get(i);
			
			for (int j = i+1; j < atoms.size(); j++){
				
				atom2 = atoms.get(j);
			
				//Get the distance between the 2 atoms
				distance = Math.sqrt(Math.pow((atom2.getX() - atom1.getX()), 2) + Math.pow((atom2.getY() - atom1.getY()), 2) + Math.pow((atom2.getZ() - atom1.getZ()),  2));
				
				// check if distance is right for a C-C bond
				// C-C bonds range from 1.20-1.54 Angstrom

				if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("C") && distance <= 1.54){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
					addToBondsList(atom1, atom2);
				}
				
				// check if distance is right for a C-O bond
				// C-O bonds range from 1.43-2.15 Angstrom
				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("O") && distance <= 2.15){
			

					bond = new Bond(atom1, atom2);
					bondList.add(bond);
					addToBondsList(atom1, atom2);
				}
				
				// check if distance is right for a C-H bond
				// C-H bonds range from 1.06-1.12 Angstrom

				else if (atom1.getAtomType().equals("C") && atom2.getAtomType().equals("H") && distance <= 1.12){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
					addToBondsList(atom1, atom2);
				}
				
				// check if distance is right for a O-H bond
				// O-H bond is approximately 0.96 Angstrom

				else if (atom1.getAtomType().equals("O") && atom2.getAtomType().equals("H") && distance <= 0.96){
					bond = new Bond(atom1, atom2);
					bondList.add(bond);
					addToBondsList(atom1, atom2);
				}
				
				/*else if it is not a bond, then just create the non-bonded interaction to be used
				 * for the non-bonding energy calculations
				 */
				else{
					interaction = new Interaction(atom1, atom2, distance);
					interactionList.add(interaction);
				}
			}
		}
	}

	public void identifyDihedrals(ArrayList<Atom> atomList){
		DihedralAngle dihedral;
		// Determine 4 atoms in dihedral
		for (Bond bond : bondList){ // iterate through middle bond, therefore between a2 and a3
			Atom a2 = bond.atom1;
			Atom a3 = bond.atom2;
			// check if each atom is bonded to another atom:
			if(a2.getBonds().size() > 1 && a3.getBonds().size() > 1){
				for(Atom first : a2.getBonds()){
					if (first == a3){continue;} // check atom is in current bond
					for(Atom last : a3.getBonds()){
						if (last == a2){continue;} // check atom is in current bond
						dihedral = new DihedralAngle(first, a2, a3, last);
						dihedralList.add(dihedral);
					}
				}
			}
		}
	}
	
	public void addToBondsList(Atom a1, Atom a2){
		if(!(a1.getBonds().contains(a2))){ // check if atom already in list
			a1.getBonds().add(a2);
		}
		if(!(a2.getBonds().contains(a1))){ // check if atom already in list
			a2.getBonds().add(a1);
		}
	}
}

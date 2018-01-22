package communities;
/*
* nissan cefetz 304929995
* yuval nagar  305302937
* */

import java.util.ArrayList;
import java.util.List;


public class cammunity
{
	List<Member> memberList ;
	double gmac;

	public cammunity() {memberList =new ArrayList<Member>();}

	public cammunity(List<Member> memberList, double gmac) {
		super();
		memberList =new ArrayList<Member>();
		this.gmac = gmac;
	}

	public void addMember(Member newMember) {
		 memberList.add(newMember);	 
   	} 
 	 


   	public double totalTaxes(){
		double sum=0;
		for(Member m : memberList){
			sum+=m.texes();
		}
	return sum;
	}


	public double getMoney(Member m) {
		return m.getMax()/2;		
	}


	public List<Double> valList() {
		memberList.sort((x,y)->x.valKind.compareTo(y.valKind));
		
		List<Double> g=new ArrayList<Double>();
			for (Member m : memberList) {
			g.add(m.velHours());	
			}
			return g;
		}
		
}

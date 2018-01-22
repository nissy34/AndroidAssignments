package communities;
/*
* nissan cefetz 304929995
* yuval nagar  305302937
* */
import java.sql.Date;

public class Married extends Member{

	int partnerId;
	int kidsUnder18;

	public Married(int id, String gender, String name, String addrs, Date birthDay, double toraH, double workH,
				   double income, valnteer valKind, int partnerId, int kidsUnder18) throws Exception {
		super(id, gender, name, addrs, birthDay, toraH, workH, income, valKind);
		this.partnerId = partnerId;
		this.kidsUnder18 = kidsUnder18;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getKidsUnder18() {
		return kidsUnder18;
	}

	public void setKidsUnder18(int kidsUnder18) {
		this.kidsUnder18 = kidsUnder18;
	}



	@Override
	public double texes() {
		if(kidsUnder18 >= 8 && toraH >=4*7 || toraH >=6*7)
	     return 0;
		return getIncome()/10;
	}

	@Override
	public double getMax() {
		return ((kidsUnder18+toraH)/income) *1000;
	}

	@Override
	public double velHours() {
		if(kidsUnder18 >= 8 && toraH >=4*7 || toraH >=6*7)
			return 2;
		else if (kidsUnder18 <= 8 && toraH <=4*7 || toraH <=6*7) 
			return 4;
	
		return 5;		
	}


	
	
}

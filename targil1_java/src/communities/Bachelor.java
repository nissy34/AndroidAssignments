package communities;
/*
* nissan cefetz 304929995
* yuval nagar  305302937
* */
import java.sql.Date;

public class Bachelor extends Member {

	
	@Override
	public double texes() {
		if(this.toraH >= 10*7)
			return 0;
		return this.getIncome()/10;
	}

	@Override
	public double getMax() {
		return 0;
	}

	@Override
	public double velHours() {
		if(texes()==0)
			return 2;
		return 5;
	}
	 
	int eduYears;
	Boolean isIndependent;
	public int getEduYears() {
		return eduYears;
	}

	public void setEduYears(int eduYears) {
		this.eduYears = eduYears;
	}

	public Boolean getIsIndependent() {
		return isIndependent;
	}

	public void setIsIndependent(Boolean isIndependent) {
		this.isIndependent = isIndependent;
	}



	public Bachelor(int id, String gender, String name, String addrs, Date birthDay, double toraH, double workH,
			double income, valnteer valKind, int eduYears, Boolean isIndependent) throws Exception {
		super(id, gender, name, addrs, birthDay, toraH, workH, income, valKind);
		this.eduYears = eduYears;
		this.isIndependent = isIndependent;
	}

}

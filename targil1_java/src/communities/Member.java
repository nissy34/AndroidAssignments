package communities;
/*
* nissan cefetz 304929995
* yuval nagar  305302937
* */
import java.sql.Date;


public abstract class Member implements ObligationsRights
{
	
	@Override
	public abstract double texes() ;

	@Override
	public abstract double getMax();

	@Override
	public abstract double velHours();
	
	int id ;
	String gender;
	String name;
	String addrs;
	Date birthDay;
	double toraH;
	double workH;
	double income;
	valnteer valKind;
	
	
	public Member(int id, String gender, String name, String addrs, Date birthDay, double toraH, double workH,
			double income, valnteer valKind) throws Exception {
		
			
		super();
		
		if(toraH + workH <= (24*7)*2/3)
			throw new Exception("you'r a B-A-T-L-A-N");
		
		this.id = id;
		this.gender = gender;
		this.name = name;
		this.addrs = addrs;
		this.birthDay = birthDay;
		this.toraH = toraH;
		this.workH = workH;
		this.income = income;
		this.valKind = valKind;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddrs() {
		return addrs;
	}
	public void setAddrs(String addrs) {
		this.addrs = addrs;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public double getToraH() {
		return toraH;
	}
	public void setToraH(double toraH) {
		this.toraH = toraH;
	}
	public double getWorkH() {
		return workH;
	}
	public void setWorkH(double workH) {
		this.workH = workH;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public valnteer getValKind() {
		return valKind;
	}
	public void setValKind(valnteer valKind) {
		this.valKind = valKind;
	}

}

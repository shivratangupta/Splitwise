class IllegalSplitException extends Exception{

}

class Utils{
	public static double roundOff(double value){
		return ((long)(value * 100)) / 100.0d;
	}

	public static boolean isApproxEqual(double v1, double v2){
		return Math.abs(v1 - v2) / (Math.min(v1, v2)) < 1e-10;
	}
}


class User{
	private String name, email, phoneNumber, address, hashPass;
	private long uid;

	private static long NEW_UID = 0;

	public User(String name, String email, String hashPass){
		this.uid = NEW_UID++;
		this.name = name;
		this.email = email;
		this.hashPass = hashPass;
	}

	// getters and setters
	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}
}


abstract class Expense{
	private long uid;  // not user id. unique id
	private String name, notes;
	private Date created;
	private Geolocation location;
	private List<Images> images;

	private double totalAmount;
	private User paidBy, createdBy; 
	private List<Split> splits;

	private static long NEW_UID = 0;

	// Builder Pattern
	private Expense(Builder ob){
		this.uid = NEW_UID++;
		this.name = ob.name;
		this.totalAmount = ob.totalAmount;
		this.createdBy = ob.createdBy;
		this.notes = notes;
		this.created = ob.created;
		this.location ob.location;
		this.paidBy = ob.paidBy;
		this.images = ob.images;
		this.splits = ob.splits;
	}

	public static class Builder{
		private String name, notes;
		private Date dateCreated;
		private Geolocation location;
		private List<Images> images;
		private double totalAmount;
		private User paidBy, createdBy;
		private List<Split> splits;     // runtime polymorphism

		public Builder(String name, double totalAmount, User createdBy){
			this.name = name;
			this.totalAmount = totalAmount;
			this.createdBy = createdBy;
		}

		// setters
		public Builder setName(String name){
			this.name = name;
		}

		public Builder setNotes(String notes){
			this.notes = notes;
		}

		public Builder setDateCreated(Date dateCreated){
			this.dateCreated = dateCreated;
		}

		public Builder setLocation(Geolocation location){
			this.location = location;
		}

		public Builder setImages(List<Images> images){
			this.images = images;
		}

		public Builder setTotalAmount(double totalAmount){
			this.totalAmount = totalAmount;
		}

		public Builder setPaidBy(User paidBy){
			this.paidBy = paidBy;
		}

		public Builder setCreatedBy(User createdBy){
			this.createdBy = createdBy;
		}

		public Builder setSplits(List<Split> splits){
			this.splitAmongst = splitAmongst;
		}

		public Expense build(){
			return new Expense();
		}
	}

	// getters
	public String getName(){
		return name;
	}

	public String getNotes(){
		return notes;
	}

	public Date getDateCreated(){
		return dateCreated;
	}

	public Geolocation getLocation(){
		return location;
	}

	public List<Images> getImages(){
		return images;
	}

	public double getTotalAmount(){
		return totalAmount;
	}

	public User getPaidBy(){
		return paidBy;
	}

	public User getCreatedBy(){
		return createdBy;
	}

	public List<Split> getSplits(){
		return splits;
	}

	// validate amount
	boolean validate(){
		double sum = 0;
		for(Split s : splits)
			sum += s.amount;

		return sum == totalAmount;
	}

	// recalculation
	abstract void recalculate(){

	}

	// set splits
	public void setSplits(List<Split> splits){
		this.splits = splits;
		recalculate();
	}


	// add split
	public void addSplits(Split s){
		splits.add(s);
		recalculate();
	}

	// remove split
	public void removeSplit(Split s){
		splits.remove(s);
		recalculate();
	}
}

class EqualExpense extends Expense{
	public EqualExpense(String name, double totalAmount, User createdBy){
		super(name, totalAmount, createdBy);
	}

	@Override
	public void recalculate(){
		int numUsers = splits.size();
		double sum = 0;
		double amount;
		for(Splits s : splits){
			amount = Utils.roundOff(totalAmount / numUsers);
			s.setAmount(amount);
			sum += s.getAmount();
		}

		if(sum != totalAmount){
			splits.get(0).setAmount(splits.get(0).getAmount() + totalAmount - sum);
		}
	}

	@Override
	public boolean validate(){
		super.validate();
		// add extra logic here
	}
}

class PercentExpense extends Expense{
	@Override
	public void recalculate(){
		int numUsers = splits.size();
		double sum = 0;
		double amount;
		for(Splits s : splits){
			if(!(s instanceof PercentSplit)){
				throw new IllegalSplitException("PercentExpense must have PercentSplits only");
			}
			PercentSplit ps = (PercentSplit) s;
			amount = Utils.roundOff(totalAmount * getPercent() / numUsers);
			s.setAmount(amount);
			sum += s.getAmount();
		}

		if(sum != totalAmount){
			splits.get(0).setAmount(splits.get(0).getAmount() + totalAmount - sum);
		}
	}

	@Override
	public boolean validate(){
		super.validate();
		// add extra logic here
	}
}

class PartsExpense extends Expense{

}

class ExactAmountExpense extends Expense{

}


abstract class Split{
	User user;
	double amount; // part, actual money, percentage
	String notes;
}

class EqualSplit extends Split{

}

class PercentSplit extends Split{
	double percent;
}

class PartsSplit extends Split{

}

class ExactAmountSplit extends Split{

}
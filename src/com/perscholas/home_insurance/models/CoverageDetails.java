package com.perscholas.home_insurance.models;

public class CoverageDetails {
 
	private int MonthlyPremuim;
	private int DwellingCoverage;
	private int DetachedStructorsl;
	private int PersonalProperty;
	private int Additional;
	private int  Medical;
	private int deductible;
	private String quote_id;
	
		
	
	


	public CoverageDetails() {
		
	}


//	public CoverageDetails(String monthlyPremuim, String dwellingCoverage, String detachedStructorsl,
//			String personalProperty, String additional, String medical, String deductible, String quote_id) {
//		super();
//		MonthlyPremuim = monthlyPremuim;
//		DwellingCoverage = dwellingCoverage;
//		DetachedStructorsl = detachedStructorsl;
//		PersonalProperty = personalProperty;
//		Additional = additional;
//		Medical = medical;
//		this.deductible = deductible;
//		this.quote_id = quote_id;
//	}



	public CoverageDetails(Object monthlyP, Object coverage, Object detachedStructors, Object personalProperty,
			Object living, Object aLC, Object deductible) {
		super();
		MonthlyPremuim = (int) monthlyP;
		DwellingCoverage = (int) coverage;
		DetachedStructorsl = (int) detachedStructors;
		PersonalProperty = (int) personalProperty;
		Additional = (int) living;
		Medical = (int) aLC;
		this.deductible = (int) deductible;
		
		
		
	}


	public int getMonthlyPremuim() {
		return MonthlyPremuim;
	}


	public void setMonthlyPremuim(int monthlyPremuim) {
		MonthlyPremuim = monthlyPremuim;
	}


	public int getDwellingCoverage() {
		return DwellingCoverage;
	}


	public void setDwellingCoverage(int dwellingCoverage) {
		DwellingCoverage = dwellingCoverage;
	}


	public int getDetachedStructorsl() {
		return DetachedStructorsl;
	}


	public void setDetachedStructorsl(int detachedStructorsl) {
		DetachedStructorsl = detachedStructorsl;
	}


	public int getPersonalProperty() {
		return PersonalProperty;
	}


	public void setPersonalProperty(int personalProperty) {
		PersonalProperty = personalProperty;
	}


	public int getAdditional() {
		return Additional;
	}


	public void setAdditional(int additional) {
		Additional = additional;
	}


	public int getMedical() {
		return Medical;
	}


	public void setMedical(int medical) {
		Medical = medical;
	}


	public int getDeductible() {
		return deductible;
	}


	public void setDeductible(int deductible) {
		this.deductible = deductible;
	}


	public String getQuote_id() {
		return quote_id;
	}


	public void setQuote_id(String i) {
		this.quote_id = i;
	}
	
	
	
}

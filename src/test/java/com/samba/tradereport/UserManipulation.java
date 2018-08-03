package com.samba.tradereport;

import static com.samba.tradereport.TestConstants.ADDRESS_LINE_1;
import static com.samba.tradereport.TestConstants.ADDRESS_POSTCODE;
import static com.samba.tradereport.TestConstants.CONTACT_CHOICES;
import static com.samba.tradereport.TestConstants.DATE_OF_BIRTH_DAY;
import static com.samba.tradereport.TestConstants.DATE_OF_BIRTH_MONTH;
import static com.samba.tradereport.TestConstants.DATE_OF_BIRTH_YEAR;
import static com.samba.tradereport.TestConstants.EMAIL;
import static com.samba.tradereport.TestConstants.EXEMPTION_BSA;
import static com.samba.tradereport.TestConstants.EXEMPTION_DWP;
import static com.samba.tradereport.TestConstants.FORENAME;
import static com.samba.tradereport.TestConstants.PCN_AMOUNT;
import static com.samba.tradereport.TestConstants.PCN_NUMBER;
import static com.samba.tradereport.TestConstants.SURNAME;
import static com.samba.tradereport.TestConstants.TELEPHONE;
import static com.samba.tradereport.TestConstants.TOWN;
import static com.samba.tradereport.TestConstants.VALID_EXEMPT_CERT_NUMBER;
import static com.samba.tradereport.TestConstants.YES;
import static com.samba.tradereport.TestConstants.YES_STRING;
import static uk.nhs.nhsbsa.lrs.pecs.model.ExCat.BSA_PECS_D;
import static uk.nhs.nhsbsa.lrs.pecs.model.ExCat.DWP_PECS_K;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import com.samba.tradereport.model.UserData;

import uk.nhs.nhsbsa.compositedate.CompositeDate;
import uk.nhs.nhsbsa.lrs.pecs.model.Address;
import uk.nhs.nhsbsa.lrs.pecs.model.ExemptionRecord;
import uk.nhs.nhsbsa.lrs.pecs.model.Patient;
import uk.nhs.nhsbsa.lrs.pecs.model.PenaltyCharge;

public class UserManipulation {
	public static UserData createDWPValidUserData() {
		UserData validUserData = new UserData();
		validUserData.setPcnNumber(PCN_NUMBER);
		validUserData.setPcnAmount(PCN_AMOUNT);
		validUserData.setDateOfBirth(new CompositeDate(DATE_OF_BIRTH_YEAR, DATE_OF_BIRTH_MONTH, DATE_OF_BIRTH_DAY));
		PenaltyCharge pc = new PenaltyCharge();
		Patient patient = new Patient();
		patient.setGender(null);
		pc.setExcat(DWP_PECS_K);
		pc.setPatient(patient);
		validUserData.setExemptionConfirmation(YES);
		validUserData.setPenaltyCharge(pc);
		validUserData
				.setDateOfBirthUpdated(new CompositeDate(DATE_OF_BIRTH_YEAR, DATE_OF_BIRTH_MONTH, DATE_OF_BIRTH_DAY));
		validUserData.setDwpExemption(null);
		return validUserData;
	}

	public static ExemptionRecord createExemptionRecord() {
		ExemptionRecord exemption = new ExemptionRecord();
		exemption.setCertificateNumber("1234567890");
		exemption.setForename("Steve");
		exemption.setSurname("Rogers");
		exemption.setDob("01-05-1900");
		exemption.setExemptionType("PPC");
		exemption.setPostCode("NE1 1EN");
		exemption.setValidFrom("10-03-2017");
		exemption.setValidTo("09-03-2020");
		exemption.setPartners(null);
		return exemption;
	}

	public static UserData createInvalidUserData() {
		UserData inValidUserData = createValidUserData();
		CompositeDate dateOfBirth = null;
		inValidUserData.setDateOfBirth(dateOfBirth);
		return inValidUserData;
	}

	public static UserData createValidUserData() {
		UserData validUserData = new UserData();
		validUserData.setPcnNumber(PCN_NUMBER);
		validUserData.setPcnAmount(PCN_AMOUNT);
		Address address = new Address();
		address.setLine1(ADDRESS_LINE_1);
		address.setPostcode(ADDRESS_POSTCODE);
		Patient patient = new Patient();
		patient.setForename(FORENAME);
		patient.setSurname(SURNAME);
		patient.setAddress(address);
		patient.setGender(null);
		PenaltyCharge pc = new PenaltyCharge();
		pc.setPatient(patient);
		pc.setExcat(BSA_PECS_D);
		pc.setChargeableValue(860);
		pc.setPenalty(4300);
		pc.setTotal(5160);
		pc.setDentalData(new ArrayList<>());
		validUserData.setPenaltyCharge(pc);
		validUserData.setAddressConfirmation(YES);
		validUserData.setDateOfBirth(new CompositeDate(DATE_OF_BIRTH_YEAR, DATE_OF_BIRTH_MONTH, DATE_OF_BIRTH_DAY));
		validUserData
				.setDateOfBirthUpdated(new CompositeDate(DATE_OF_BIRTH_YEAR, DATE_OF_BIRTH_MONTH, DATE_OF_BIRTH_DAY));
		validUserData.setContactChoices(CONTACT_CHOICES);
		validUserData.setEmail(EMAIL);
		validUserData.setTelephone(TELEPHONE);
		validUserData.setExemptionConfirmation(YES);

		validUserData.setDwpExemption(EXEMPTION_DWP);
		validUserData.setBsaExemption(EXEMPTION_BSA);
		validUserData.setLastName(SURNAME);
		validUserData.setFirstName(FORENAME);
		validUserData.setPregnancyConfirmation(YES_STRING);
		validUserData.setTown(TOWN);
		validUserData.setPostcode(ADDRESS_POSTCODE);
		validUserData.setMedicalConditionsConfirmation(YES_STRING);
		validUserData.setNameConfirmation(YES);
		validUserData.setDobConfirmation(YES);
		validUserData.setIllnessConfirmation(YES_STRING);
		validUserData.setBuilding(ADDRESS_LINE_1);
		validUserData.setExemptionCertificateNumber(VALID_EXEMPT_CERT_NUMBER);
		return validUserData;
	}

	public static void setUserDateOfBirth(UserData user, Period periodAgo) {
		LocalDate currentDate = LocalDate.now();
		LocalDate testDate = currentDate.minus(periodAgo);
		user.setDateOfBirth(new CompositeDate(testDate));
	}

	public static void setUserUpdateDateOfBirth(UserData user, Period periodAgo) {
		LocalDate currentDate = LocalDate.now();
		LocalDate testDate = currentDate.minus(periodAgo);
		user.setDateOfBirthUpdated(new CompositeDate(testDate));
	}
}

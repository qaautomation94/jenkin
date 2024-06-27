package com.mscs.emr.automation.testData;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.mscs.emr.automation.functional.BaseTestPage;
import com.mscs.emr.automation.utils.DateUtils;

public class TestDataGenerator {
	private static final Calendar CURRENT_CALENDAR = Calendar.getInstance();

    //region PatientData

    /**
     * Generates patient with random data
     */
    public static PatientData generateRandomPatient() {
        return new PatientData(MockDataUtils.generateFirstName(), MockDataUtils.generateLastName(), MockDataUtils.generateUniqueMrnNumber(),
                MockDataUtils.generateRandomPastDate(CURRENT_CALENDAR.get(Calendar.YEAR)), MockDataUtils.getRandomGender());
    }

    public static PatientData generateMalePatient() {
        return new PatientData(MockDataUtils.generateFirstName(), MockDataUtils.generateLastName(), MockDataUtils.generateUniqueMrnNumber(),
                MockDataUtils.generateRandomPastDate(CURRENT_CALENDAR.get(Calendar.YEAR)), "G2TestValues.PATIENT_GENDER_MALE");
    }

    public static PatientData generateFemalePatient() {
        return new PatientData(MockDataUtils.generateFirstName(), MockDataUtils.generateLastName(), MockDataUtils.generateUniqueMrnNumber(),
                MockDataUtils.generateRandomPastDate(CURRENT_CALENDAR.get(Calendar.YEAR)), "G2TestValues.PATIENT_GENDER_FEMALE");
    }

    //endregion

    //region AppointmentData

    /**
     * Generates Appointment with designated start time and duration
     */
    public static AppointmentData generateAppointment(String startTime, String duration) {
        return new AppointmentData(MockDataUtils.generateWord(5, 10) + MockDataUtils.createUniqueNumber(13),
                "G2TestValues.TYPE_MD", BaseTestPage.adminUser, BaseTestPage.adminUserPassword,
                DateUtils.getTodaysDate(), startTime, duration);
    }

    public static AppointmentData generateAppointment(String resourceType, String resourceName, String location, String startTime, String duration) {
        return new AppointmentData(MockDataUtils.generateWord(5, 10) + MockDataUtils.createUniqueNumber(13),
                resourceType, resourceName, location,
                DateUtils.getTodaysDate(), startTime, duration);
    }
    //endregion

    //region MedicationData
    /**
     * Returns blank Medication data object.
     * Use either the existing methods for typical medication templates
     * Or use setters to set all necessary medication data
     */
    public static MedicationData getMedication(String name, Number doseAmount, String doseUnit, String doseForm,
                                               String clinicalRoute, String method, String frequency, String quickSig,
                                               String startTime, String startAmPm, String stopTime, String stopAmPm, String adminCode,
                                               String adminCodeDescription, String fillMethod, Number dispense, String dispenseUnit, Number refills,
                                               Number serumCreatinineAmount) {
        return new MedicationData(name, doseAmount, doseUnit, doseForm, clinicalRoute, method, frequency, quickSig, startTime, startAmPm, stopTime, stopAmPm,
                adminCode, adminCodeDescription, fillMethod, dispense, dispenseUnit, refills, serumCreatinineAmount);
    }

    public static MedicationData getMedication() {
        return new MedicationData(null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null);
    }
    //endregion

    //region ProblemData
    /**
     * returns ProblemData object//TODO: update this comment
     */
    public static ProblemData getProblem(String name, String icd10Code, String icd9Code, boolean isPrincipalDiagnosis,
                                         String dateOfDiagnosis, String resolutionDate, String status, String notes) {
        return new ProblemData(name, icd10Code, icd9Code, isPrincipalDiagnosis, dateOfDiagnosis, resolutionDate, status, notes);
    }

    public static ProblemData getProblem() {
        return new ProblemData("", null,null, new Random().nextBoolean(), "", "", null, "");
    }

    public static ProblemData generateRandomProblem() {
        return new ProblemData(
                "",
                null,
                null,
                new Random().nextBoolean(),
                "",
                "",
                null,
                MockDataUtils.generateSentence()
                );
    }
    //endregion

    public static class PatientData {
        private String firstName;
        private String lastName;
        private String mrn;
        private String dob;
        private String gender;

        public PatientData(String firstName, String lastName, String mrn, String dob, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.mrn = mrn;
            this.dob = dob;
            this.gender = gender;
        }

        //region Getters

        public String getFirstName() { return firstName; }

        public String getLastName() { return lastName; }

        public String getFullName() { return String.format("%s, %s", lastName, firstName); }

        public String getMrn() { return mrn; }

        public String getDob() { return dob; }

        public String getGender() { return gender; }

        //endregion

        //region Setters
        public PatientData setFirstName(String firstName) { this.firstName = firstName; return this; }

        public PatientData setLastName(String lastName) { this.lastName = lastName; return this; }

        public PatientData setMrn(String mrn) { this.mrn = mrn; return this; }

        public PatientData setDob(String dob) { this.dob = dob; return this; }

        public PatientData setGender(String gender) { this.gender = gender; return this; }
        //endregion
    }

    public static class AppointmentData {
        private String reason;
        private String type;
        private String resourceName;
        private String location;
        private String date;
        private String startTime;
        private String duration;

        private AppointmentData(String reason, String type, String resourceName, String location, String date, String startTime, String duration) {
            this.reason = reason;
            this.type = type;
            this.resourceName = resourceName;
            this.location = location;
            this.date = date;
            this.startTime = StringUtils.deleteWhitespace(startTime);
            this.duration = duration;
        }

        //region Getters
        public String getReason() { return reason; }

        public String getType() { return type; }

        public String getResourceFullName() { return resourceName; }

        public String getResourceFirstName() {
            return resourceName.split(",")[1].trim();
        }

        public String getResourceLastName() {
            return resourceName.split(",")[0].trim();
        }

        public String getLocation() { return location; }

        public String getDate() { return date; }

        public String getStartTime() { return startTime; }

        public String getDuration() { return duration; }
        //endregion

        //region Setters
        public AppointmentData setReason(String reason) {this.reason = reason; return this;}

        public AppointmentData setType(String type) {this.type = type; return this;}

        public AppointmentData setResourceName(String resourceName) {this.resourceName = resourceName; return this;}

        public AppointmentData setLocation(String location) {this.location = location; return this;}

        public AppointmentData setDate(String date) {this.date = date; return this;}

        public AppointmentData setStartTime(String startTime) {this.startTime = startTime; return this;}

        public AppointmentData setDuration(String duration) {this.duration = duration; return this;}
        //endregion
    }

    public static class MedicationData {
        private String name;
        private Number doseAmount;
        private String doseUnit;
        private String doseForm;
        private String clinicalRoute;
        private String method;
        private String frequency;
        private String quickSig;
        private String startTime;
        private String stopTime;
        private String adminCode;
        private String adminCodeDescription;
        private String fillMethod;
        private Number dispense;
        private String dispenseUnit;
        private Number refills;
        private Number serumCreatinineAmount;

        private MedicationData(String name, Number doseAmount, String doseUnit, String doseForm, String clinicalRoute,
                               String method, String frequency, String quickSig, String startTime, String startAmPm, String stopTime, String stopAmPm,
                               String adminCode, String adminCodeDescription, String fillMethod, Number dispense, String dispenseUnit, Number refills,
                               Number serumCreatinineAmount) {
            this.name = name;
            this.doseAmount = doseAmount;
            this.doseUnit = doseUnit;
            this.doseForm = doseForm;
            this.clinicalRoute = clinicalRoute;
            this.method = method;
            this.frequency = frequency;
            this.quickSig = quickSig;
            this.startTime = startTime == null ? null : String.format("%s %s", startTime, startAmPm);
            this.stopTime = stopTime == null ? null : String.format("%s %s", stopTime, stopAmPm);
            this.adminCode = adminCode;
            this.adminCodeDescription = adminCodeDescription;
            this.fillMethod = fillMethod;
            this.dispense = dispense;
            this.dispenseUnit = dispenseUnit;
            this.refills = refills;
            this.serumCreatinineAmount = serumCreatinineAmount;
        }

        //region Getters
        public String getName() { return this.name; }

        public Number getDoseAmount() { return this.doseAmount; }

        public String getDoseUnit() { return this.doseUnit; }

        public String getDoseForm() { return this.doseForm; }

        public String getClinicalRoute() { return this.clinicalRoute; }

        public String getMethod() { return this.method; }

        public String getFrequency() { return this.frequency; }

        public String getQuickSig() { return this.quickSig; }

        public String getStartTime() { return this.startTime; }

        public String getStopTime() { return this.stopTime; }

        public String getAdminCode() { return this.adminCode; }

        public String getAdminCodeDescription() { return this.adminCodeDescription; }

        public String getFillMethod() { return this.fillMethod; }

        public Number getDispense() { return this.dispense; }

        public String getDispenseUnit() { return this.dispenseUnit; }

        public Number getRefills() { return this.refills; }

        public Number getSerumCreatinineAmount() { return this.serumCreatinineAmount; }
        //endregion

        //region Setters
        public MedicationData setName(String name) { this.name = name; return this; }

        public MedicationData setDoseAmount(Number doseAmount) { this.doseAmount = doseAmount; return this; }

        public MedicationData setDoseUnit(String doseUnit) { this.doseUnit = doseUnit; return this; }

        public MedicationData setDoseForm(String doseForm) { this.doseForm = doseForm; return this; }

        public MedicationData setClinicalRoute(String clinicalRoute) { this.clinicalRoute = clinicalRoute; return this; }

        public MedicationData setMethod(String method) { this.method = method; return this; }

        public MedicationData setFrequency(String frequency) { this.frequency = frequency; return this; }

        public MedicationData setQuickSig(String quickSig) { this.quickSig = quickSig; return this; }

        public MedicationData setStartTime(String startTime, String amPm) { this.startTime = String.format("%s %s", startTime, amPm); return this; }

        public MedicationData setStopTime(String stopTime, String amPm) { this.stopTime = String.format("%s %s", stopTime, amPm); return this; }

        public MedicationData setAdminCode(String adminCode) { this.adminCode = adminCode; return this; }

        public MedicationData setAdminCodeDescription(String adminCodeDescription) {this.adminCodeDescription = adminCodeDescription; return this; }

        public MedicationData setFillMethod(String fillMethod) { this.fillMethod = fillMethod; return this;}

        public MedicationData setDispense(Number dispense) { this.dispense = dispense; return this;}

        public MedicationData setDispenseUnit(String dispenseUnit) { this.dispenseUnit = dispenseUnit; return this;}

        public MedicationData setRefills(Number refills) { this.refills = refills; return this;}

        public MedicationData setSerumCreatinineAmount(Number serumCreatinineAmount) { this.serumCreatinineAmount = serumCreatinineAmount; return this;}
        
    }

    public static class ProblemData {
        private String name;
        private String icd10Code;
        private String icd9Code;
        private boolean isPrincipalDiagnosis;
        private String dateOfDiagnosis;
        private String resolutionDate;
        private String status;
        private String notes;

        private ProblemData(String name, String icd10Code, String icd9Code, boolean isPrincipalDiagnosis, String dateOfDiagnosis,
                            String resolutionDate, String status, String notes) {
            this.name = name;
            this.icd10Code = icd10Code;
            this.icd9Code = icd9Code;
            this.isPrincipalDiagnosis = isPrincipalDiagnosis;
            this.dateOfDiagnosis = dateOfDiagnosis;
            this.resolutionDate = resolutionDate;
            this.status = status;
            this.notes = notes;
        }

        //region Getters
        public String getName() { return this.name; }

        public String getIcd10Code() { return this.icd10Code; }

        public String getIcd9Code() { return this.icd9Code; }

        public boolean getIsPrincipalDiagnosis() { return this.isPrincipalDiagnosis; }

        public String getDateOfDiagnosis() { return this.dateOfDiagnosis; }

        public String getResolutionDate() { return this.resolutionDate; }

        public String getStatus() { return this.status; }

        public String getNotes() { return this.notes; }
        //endregion

        //region Setters
        public ProblemData setName(String name) { this.name = name; return this; }

        public ProblemData setIcd10Code(String icd10Code) { this.icd10Code = icd10Code; return this; }

        public ProblemData setIcd9Code(String icd9Code) { this.icd9Code = icd9Code; return this; }

        public ProblemData setIsPrincipalDiagnosis(boolean isPrincipalDiagnosis) { this.isPrincipalDiagnosis = isPrincipalDiagnosis; return this; }

        public ProblemData setDateOfDiagnosis(String dateOfDiagnosis) { this.dateOfDiagnosis = dateOfDiagnosis; return this; }

        public ProblemData setResolutionDate(String resolutionDate) { this.resolutionDate = resolutionDate; return this; }

        public ProblemData setStatus(String status) { this.status = status; return this; }

        public ProblemData setNotes(String notes) { this.notes = notes; return this; }
        //endregion

        //region Typical Problem methods
        public ProblemData colonCancerActive() { //TODO: possible statuses: Active, Inactive, Resolved
            this.name = "G2TestValues.PROBLEM_COLONCANCER";
            this.icd10Code = "G2TestValues.ICD10_C180_COLONCANCER";
            this.isPrincipalDiagnosis = new Random().nextBoolean();
            this.dateOfDiagnosis = MockDataUtils.generateRandomPastDate(0);
            this.status = "G2TestValues.ACTIVE";
            this.notes = MockDataUtils.generateSentence();
            return this;
        }

        //endregion
    }
}

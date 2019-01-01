package com.classtune.ndc.apiresponse.profile_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("batch_id")
    @Expose
    private Object batchId;
    @SerializedName("registration_number")
    @Expose
    private Object registrationNumber;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("name_in_bengali")
    @Expose
    private String nameInBengali;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("date_of_service")
    @Expose
    private String dateOfService;
    @SerializedName("date_of_last_promotion")
    @Expose
    private String dateOfLastPromotion;
    @SerializedName("nationality_id")
    @Expose
    private String nationalityId;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("date_of_marriage")
    @Expose
    private Object dateOfMarriage;
    @SerializedName("emergency_name")
    @Expose
    private String emergencyName;
    @SerializedName("emergency_relation")
    @Expose
    private String emergencyRelation;
    @SerializedName("emergency_phone")
    @Expose
    private String emergencyPhone;
    @SerializedName("emergency_mobile_1")
    @Expose
    private String emergencyMobile1;
    @SerializedName("emergency_mobile_2")
    @Expose
    private String emergencyMobile2;
    @SerializedName("food_preferred")
    @Expose
    private String foodPreferred;
    @SerializedName("food_restriction")
    @Expose
    private String foodRestriction;
    @SerializedName("service_award_1")
    @Expose
    private String serviceAward1;
    @SerializedName("service_award_2")
    @Expose
    private String serviceAward2;
    @SerializedName("hobbies")
    @Expose
    private String hobbies;
    @SerializedName("expertise")
    @Expose
    private String expertise;
    @SerializedName("spouse_fullname")
    @Expose
    private Object spouseFullname;
    @SerializedName("spouse_nickname")
    @Expose
    private Object spouseNickname;
    @SerializedName("spouse_date_of_marriage")
    @Expose
    private Object spouseDateOfMarriage;
    @SerializedName("spouse_date_of_birth")
    @Expose
    private Object spouseDateOfBirth;
    @SerializedName("spouse_educational_qualification")
    @Expose
    private Object spouseEducationalQualification;
    @SerializedName("spouse_blood_group")
    @Expose
    private Object spouseBloodGroup;
    @SerializedName("name_of_father")
    @Expose
    private String nameOfFather;
    @SerializedName("name_of_mother")
    @Expose
    private String nameOfMother;
    @SerializedName("date_of_arrival")
    @Expose
    private Object dateOfArrival;
    @SerializedName("date_of_arrival_family_member")
    @Expose
    private Object dateOfArrivalFamilyMember;
    @SerializedName("arrival_family_member_number")
    @Expose
    private Object arrivalFamilyMemberNumber;
    @SerializedName("duration_of_stay_family_member")
    @Expose
    private Object durationOfStayFamilyMember;
    @SerializedName("other_info")
    @Expose
    private Object otherInfo;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("faculty_id")
    @Expose
    private String facultyId;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("rank_name")
    @Expose
    private Object rankName;
    @SerializedName("service_type_name")
    @Expose
    private String serviceTypeName;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("blood_group_name")
    @Expose
    private String bloodGroupName;
    @SerializedName("religion_name")
    @Expose
    private String religionName;
    @SerializedName("gender_name")
    @Expose
    private String genderName;
    @SerializedName("batch_course_name")
    @Expose
    private String batchCourseName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("address")
    @Expose
    private Address address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getBatchId() {
        return batchId;
    }

    public void setBatchId(Object batchId) {
        this.batchId = batchId;
    }

    public Object getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Object registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNameInBengali() {
        return nameInBengali;
    }

    public void setNameInBengali(String nameInBengali) {
        this.nameInBengali = nameInBengali;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getDateOfLastPromotion() {
        return dateOfLastPromotion;
    }

    public void setDateOfLastPromotion(String dateOfLastPromotion) {
        this.dateOfLastPromotion = dateOfLastPromotion;
    }

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Object getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(Object dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getEmergencyMobile1() {
        return emergencyMobile1;
    }

    public void setEmergencyMobile1(String emergencyMobile1) {
        this.emergencyMobile1 = emergencyMobile1;
    }

    public String getEmergencyMobile2() {
        return emergencyMobile2;
    }

    public void setEmergencyMobile2(String emergencyMobile2) {
        this.emergencyMobile2 = emergencyMobile2;
    }

    public String getFoodPreferred() {
        return foodPreferred;
    }

    public void setFoodPreferred(String foodPreferred) {
        this.foodPreferred = foodPreferred;
    }

    public String getFoodRestriction() {
        return foodRestriction;
    }

    public void setFoodRestriction(String foodRestriction) {
        this.foodRestriction = foodRestriction;
    }

    public String getServiceAward1() {
        return serviceAward1;
    }

    public void setServiceAward1(String serviceAward1) {
        this.serviceAward1 = serviceAward1;
    }

    public String getServiceAward2() {
        return serviceAward2;
    }

    public void setServiceAward2(String serviceAward2) {
        this.serviceAward2 = serviceAward2;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Object getSpouseFullname() {
        return spouseFullname;
    }

    public void setSpouseFullname(Object spouseFullname) {
        this.spouseFullname = spouseFullname;
    }

    public Object getSpouseNickname() {
        return spouseNickname;
    }

    public void setSpouseNickname(Object spouseNickname) {
        this.spouseNickname = spouseNickname;
    }

    public Object getSpouseDateOfMarriage() {
        return spouseDateOfMarriage;
    }

    public void setSpouseDateOfMarriage(Object spouseDateOfMarriage) {
        this.spouseDateOfMarriage = spouseDateOfMarriage;
    }

    public Object getSpouseDateOfBirth() {
        return spouseDateOfBirth;
    }

    public void setSpouseDateOfBirth(Object spouseDateOfBirth) {
        this.spouseDateOfBirth = spouseDateOfBirth;
    }

    public Object getSpouseEducationalQualification() {
        return spouseEducationalQualification;
    }

    public void setSpouseEducationalQualification(Object spouseEducationalQualification) {
        this.spouseEducationalQualification = spouseEducationalQualification;
    }

    public Object getSpouseBloodGroup() {
        return spouseBloodGroup;
    }

    public void setSpouseBloodGroup(Object spouseBloodGroup) {
        this.spouseBloodGroup = spouseBloodGroup;
    }

    public String getNameOfFather() {
        return nameOfFather;
    }

    public void setNameOfFather(String nameOfFather) {
        this.nameOfFather = nameOfFather;
    }

    public String getNameOfMother() {
        return nameOfMother;
    }

    public void setNameOfMother(String nameOfMother) {
        this.nameOfMother = nameOfMother;
    }

    public Object getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Object dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public Object getDateOfArrivalFamilyMember() {
        return dateOfArrivalFamilyMember;
    }

    public void setDateOfArrivalFamilyMember(Object dateOfArrivalFamilyMember) {
        this.dateOfArrivalFamilyMember = dateOfArrivalFamilyMember;
    }

    public Object getArrivalFamilyMemberNumber() {
        return arrivalFamilyMemberNumber;
    }

    public void setArrivalFamilyMemberNumber(Object arrivalFamilyMemberNumber) {
        this.arrivalFamilyMemberNumber = arrivalFamilyMemberNumber;
    }

    public Object getDurationOfStayFamilyMember() {
        return durationOfStayFamilyMember;
    }

    public void setDurationOfStayFamilyMember(Object durationOfStayFamilyMember) {
        this.durationOfStayFamilyMember = durationOfStayFamilyMember;
    }

    public Object getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(Object otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getRankName() {
        return rankName;
    }

    public void setRankName(Object rankName) {
        this.rankName = rankName;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBloodGroupName() {
        return bloodGroupName;
    }

    public void setBloodGroupName(String bloodGroupName) {
        this.bloodGroupName = bloodGroupName;
    }

    public String getReligionName() {
        return religionName;
    }

    public void setReligionName(String religionName) {
        this.religionName = religionName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getBatchCourseName() {
        return batchCourseName;
    }

    public void setBatchCourseName(String batchCourseName) {
        this.batchCourseName = batchCourseName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
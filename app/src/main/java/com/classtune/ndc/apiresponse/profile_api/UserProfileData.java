package com.classtune.ndc.apiresponse.profile_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileData {

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
    private Object mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("rank_id")
    @Expose
    private String rankId;
    @SerializedName("service_number")
    @Expose
    private String serviceNumber;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("batch_id")
    @Expose
    private Object batchId;
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
    private Object dateOfLastPromotion;
    @SerializedName("nationality_id")
    @Expose
    private String nationalityId;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("date_of_marriage")
    @Expose
    private Object dateOfMarriage;
    @SerializedName("name_of_father")
    @Expose
    private String nameOfFather;
    @SerializedName("name_of_mother")
    @Expose
    private String nameOfMother;
    @SerializedName("spouse_fullname")
    @Expose
    private Object spouseFullname;
    @SerializedName("food_preferred")
    @Expose
    private Object foodPreferred;
    @SerializedName("food_restriction")
    @Expose
    private Object foodRestriction;
    @SerializedName("service_award_1")
    @Expose
    private Object serviceAward1;
    @SerializedName("service_award_2")
    @Expose
    private Object serviceAward2;
    @SerializedName("hobbies")
    @Expose
    private Object hobbies;
    @SerializedName("rank_name")
    @Expose
    private String rankName;
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
    @SerializedName("current_location")
    @Expose
    private String currentLocation;
    @SerializedName("degree_name")
    @Expose
    private Object degreeName;

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

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Object getBatchId() {
        return batchId;
    }

    public void setBatchId(Object batchId) {
        this.batchId = batchId;
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

    public Object getDateOfLastPromotion() {
        return dateOfLastPromotion;
    }

    public void setDateOfLastPromotion(Object dateOfLastPromotion) {
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

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
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

    public Object getSpouseFullname() {
        return spouseFullname;
    }

    public void setSpouseFullname(Object spouseFullname) {
        this.spouseFullname = spouseFullname;
    }

    public Object getFoodPreferred() {
        return foodPreferred;
    }

    public void setFoodPreferred(Object foodPreferred) {
        this.foodPreferred = foodPreferred;
    }

    public Object getFoodRestriction() {
        return foodRestriction;
    }

    public void setFoodRestriction(Object foodRestriction) {
        this.foodRestriction = foodRestriction;
    }

    public Object getServiceAward1() {
        return serviceAward1;
    }

    public void setServiceAward1(Object serviceAward1) {
        this.serviceAward1 = serviceAward1;
    }

    public Object getServiceAward2() {
        return serviceAward2;
    }

    public void setServiceAward2(Object serviceAward2) {
        this.serviceAward2 = serviceAward2;
    }

    public Object getHobbies() {
        return hobbies;
    }

    public void setHobbies(Object hobbies) {
        this.hobbies = hobbies;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
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

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Object getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(Object degreeName) {
        this.degreeName = degreeName;
    }

}

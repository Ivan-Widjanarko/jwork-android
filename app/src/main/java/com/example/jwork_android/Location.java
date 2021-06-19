package com.example.jwork_android;

/**
 * Class for Location
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class Location
{
    private String province, city, description;

    /**
     * Constructor for objects of class Location
     * @param province Recruiter's province
     * @param city Recruiter's city
     * @param description Recruiter location's description
     */
    public Location(String province, String city, String description)
    {
        this.province = province;
        this.city = city;
        this.description = description;
    }

    /**
     * Method for get recruiter's province
     * @return    Recruiter's province
     */
    public String getProvince(){
        return province;
    }

    /**
     * Method for get recruiter's city
     * @return    Recruiter's city
     */
    public String getCity(){
        return city;
    }

    /**
     * Method for get recruiter location's description
     * @return    Recruiter's description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Method for set recruiter's province
     * @param province Recruiter's province
     */
    public void setProvince(String province){
        this.province = province;
    }

    /**
     * Method for set recruiter's city
     * @param city Recruiter's city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Method for set recruiter location's description
     * @param description Recruiter location's description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Method for print recruiter location's detail
     * @return details
     */
    public String toString(){
        String value =  "===================== LOCATION =====================" + "\n" +
                "Province = " + province + "\n" +
                "City = " + city + "\n" +
                "Description = " + description + "\n";
        return value;
    }
}

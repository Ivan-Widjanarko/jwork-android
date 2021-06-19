package com.example.jwork_android;

/**
 * Class for Recruiter
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class Recruiter
{
    private int id;
    private String name, email, phoneNumber;
    private Location location;

    /**
     * Constructor for objects of class Recruiter
     * @param id Recruiter's ID
     * @param name Recruiter's name
     * @param email Recruiter's email
     * @param phoneNumber Recruiter's phone number
     * @param location Recruiter's location
     */
    public Recruiter(int id, String name, String email, String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    /**
     * Method for get recruiter's ID
     * @return    Recruiter's ID
     */
    public int getId()
    {
        return id;
    }

    /**
     * Method for get recruiter's name
     * @return    Recruiter's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Method for get recruiter's email
     * @return    Recruiter's email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Method for get recruiter's phone number
     * @return    Recruiter's phone number
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Method for get recruiter's location
     * @return    Recruiter's location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Method for set recruiter's ID
     * @param id Recruiter's ID
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Method for set recruiter's email
     * @param email Recruiter's email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Method for set recruiter's name
     * @param name Recruiter's name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Method for set recruiter's phone number
     * @param phoneNumber Recruiter's phone number
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method for set recruiter's location
     * @param location Recruiter's location
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Method for print recruiter's detail
     * @return details
     */
    public String toString(){
        String value =  "===================== RECRUITER =====================" + "\n" +
                "Id = " + id + "\n" +
                "Nama = " + name + "\n" +
                "PhoneNumber = " + phoneNumber + "\n" +
                "Location = " + location.getDescription() + "\n";
        return value;
    }
}


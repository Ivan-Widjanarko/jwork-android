package com.example.jwork_android;

/**
 * Class for Job
 *
 * @author Ivan Widjanarko
 * @version 27-05-2021
 */
public class Job
{
    private int id, fee;
    private String name;
    private Recruiter recruiter;
    private String category;

    /**
     * Constructor for objects of class Job
     * @param id Recruiter's ID
     * @param name Jobs's name
     * @param recruiter Recruiter Information
     * @param fee Recruiter's fee
     * @param category Job's category
     */
    public Job(int id, String name, Recruiter recruiter, int fee, String category){
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
    }

    /**
     * method for getId
     * @return    Recruiter's ID
     */
    public int getId(){
        return id;
    }

    /**
     * method for getName
     * @return    Jobs's name
     */
    public String getName(){
        return name;
    }

    /**
     * method for getFee
     * @return    Recruiter's fee
     */
    public int getFee(){
        return fee;
    }

    /**
     * method for getCategory
     * @return   Job's category
     */
    public String getCategory(){
        return category;
    }

    /**
     * method for getRecruiter
     * @return    Recruiter Information
     */
    public Recruiter getRecruiter(){
        return recruiter;
    }

    /**
     * method for setId
     * @param id Recruiter's ID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * method for setName
     * @param name Jobs's name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * method for setRecruiter
     */
    public void setRecruiter(){
        this.recruiter = recruiter;
    }

    /**
     * method for setFee
     * @param fee Recruiter's fee
     */
    public void setFee(int fee){
        this.fee = fee;
    }

    /**
     * method for setCategory
     * @param category Job's category
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**method for print detail*/
    public String toString(){
        String value =  "===================== JOB =====================" + "\n" +
                "Id = " + id + "\n" +
                "Nama = " + name + "\n" +
                "Recruiter = " + recruiter.getName() + "\n" +
                "City = " + getRecruiter().getLocation().getCity() + "\n" +
                "Fee = " + fee + "\n" +
                "Category = " + category.toString() + "\n";
        return value;
    }
}

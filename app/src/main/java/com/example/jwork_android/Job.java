package com.example.jwork_android;

/**
 * Class for Job
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class Job
{
    private int id, fee;
    private String name;
    private Recruiter recruiter;
    private String category;

    /**
     * Constructor for objects of class Job
     * @param id Job's ID
     * @param name Jobs's name
     * @param recruiter Recruiter
     * @param fee Job's fee
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
     * Method for get job's ID
     * @return    job's ID
     */
    public int getId(){
        return id;
    }

    /**
     * Method for get job's name
     * @return    Jobs's name
     */
    public String getName(){
        return name;
    }

    /**
     * Method for get job's fee
     * @return    job's fee
     */
    public int getFee(){
        return fee;
    }

    /**
     * Method for get job's category
     * @return   Job's category
     */
    public String getCategory(){
        return category;
    }

    /**
     * Method for get recruiter
     * @return    Recruiter
     */
    public Recruiter getRecruiter(){
        return recruiter;
    }

    /**
     * Method for set job's ID
     * @param id Job's ID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Method for set job's name
     * @param name Jobs's name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method for set recruiter
     * @param recruiter Recruiter
     */
    public void setRecruiter(Recruiter recruiter){
        this.recruiter = recruiter;
    }

    /**
     * Method for set job's fee
     * @param fee Job's fee
     */
    public void setFee(int fee){
        this.fee = fee;
    }

    /**
     * Method for set job's category
     * @param category Job's category
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Method for print job's detail
     * @return details
     */
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

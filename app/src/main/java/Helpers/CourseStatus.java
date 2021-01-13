package Helpers;

public class CourseStatus {

    public String[] getAllCourseStatus(){
        String[] courseStatus = new String[4];
        courseStatus[0].equals("In Progress");
        courseStatus[1].equals("Completed");
        courseStatus[2].equals("Dropped");
        courseStatus[3].equals("Plan To Take");
        return courseStatus;
    }
}

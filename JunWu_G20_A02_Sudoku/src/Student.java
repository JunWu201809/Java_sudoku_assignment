import java.util.ArrayList;
import java.util.List;

public class Student {
	private int studentNumber;
	private String studentName;
	private int numCourses;
	private Course[] courses;

	public Course findCourse(String courseNum) {
	    int foundIndex = -1;
	    int bottom = 0;
	    int top = numCourses - 1;
	    int middle;
	    while (bottom <= top && foundIndex == -1)
	    {
	      middle = (top + bottom) / 2;
	      if (courseNum.compareToIgnoreCase(courses[middle].getCourseNumber()) < 0)
	        top = middle - 1;
	      else if (courseNum.compareToIgnoreCase(courses[middle].getCourseNumber()) > 0)
	        bottom = middle + 1;
	      else
	        foundIndex = middle;
	    }
	    if(foundIndex !=-1)
	    return courses[foundIndex];
	    else
			return null;
	}
	
	public void sortByFinalMark()
	{
		for (int i = 0; i < numCourses; i++) {
			if(courses[i].getFinalMark() < courses[i+1].getFinalMark())
				swapCourses(i,i+1);
		}
	}

	public void swapCourses(int first, int second) {
		Course tempCourse = courses[first];
		courses[first] = courses[second];
		courses[second] = tempCourse;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getNumCourses() {
		return numCourses;
	}

	public void setNumCourses(int numCourses) {
		this.numCourses = numCourses;
	}

}

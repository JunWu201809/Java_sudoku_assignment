import java.util.ArrayList;
import java.util.List;

public class School {
	private Student[] students;
	private int numStudents = 0;
	
	public Student findStudent(int studentNum)
	{
		for (int i = 0; i < numStudents; i++) {
			Student student = students[i];
			if(student.getStudentNumber() == studentNum)
				return student;
		}
		return null;
	}
}

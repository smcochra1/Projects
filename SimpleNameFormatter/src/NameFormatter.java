// ------------------------------------------------------------
// DESCRIPTION: asks user for full name, then prints their initials, 
//				Last name and middle initial, and first and last name. 
//-------------------------------------------------------------
import java.util.Scanner;
public class NameFormatter{

public static void main(String[] args) {
Scanner in = new Scanner(System.in);
System.out.println("What are your first, middle and last names? ");
String name[] = in.nextLine().toLowerCase().split(" ");
String fname, lname, mname="", initials, lfname;
if(name.length<3) {
	throw new IndexOutOfBoundsException("Invalid Entry! Must enter first, middle, and last name.");
}
	if(name.length==3){
		fname = name[0].toUpperCase().charAt(0)+name[0].substring(1);
		mname = name[1].toUpperCase().charAt(0)+name[1].substring(1);
		lname = name[2].toUpperCase().charAt(0)+name[2].substring(1);
		initials = fname.charAt(0)+"."+lname.charAt(0)+"."+mname.charAt(0)+".".toUpperCase();
		lfname = lname+", "+fname+" "+mname.charAt(0)+".";
	}else{
		fname = name[0].toUpperCase().charAt(0)+name[0].substring(1);
		lname = name[1].toUpperCase().charAt(0)+name[1].substring(1);
		initials = fname.charAt(0)+"."+lname.charAt(0)+".".toUpperCase();
		lfname = lname+", "+fname;
	}


System.out.println(" Initials : "+initials);
System.out.println("Last Name First : "+lfname);
System.out.println("First and Last : "+fname+" "+lname);
	}
}
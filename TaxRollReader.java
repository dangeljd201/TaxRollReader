

import java.util.*;
import java.io.*;
import java.nio.file.*; 
import java.lang.*;
import java.awt.*;


public class TaxRollReader {

	public static void main (String [] args) throws FileNotFoundException {

		//If there wasn't a file input in args, provide error message
        if(args.length < 1)  {
            System.out.println("Did not input a file. Try again with a file name.");
		    System.exit(1);
   		}

   		ArrayList<String> parcels = new ArrayList <String>();
        
        String info = "";
        int added = 0;

        //gets the data
	    Scanner reader = new Scanner(new FileInputStream(args[0]));

	    Scanner in = new Scanner(System.in);
        boolean end = false;
        boolean finished = false;
        int entries = 0;


        ArrayList<String> mailInfoFirstLine = new ArrayList <String>();
        ArrayList<String> mailInfoSecondLine = new ArrayList <String>();
        ArrayList<String> mailInfoThirdLine = new ArrayList <String>();
        ArrayList<String> mailInfoFourthLine = new ArrayList <String>();
        ArrayList<String> mailInfoFifthLine = new ArrayList <String>();
        ArrayList<String> mailInfoSixthLine = new ArrayList <String>();
        ArrayList<String> mailInfoSeventhLine = new ArrayList <String>();
        ArrayList<String> mailInfoEigthLine = new ArrayList <String>();



        System.out.println("Enter the file number");

        String fileNumber = in.nextLine();
        String year = "20" + fileNumber.substring(0, 2);

        //What if
        //I asked the user if the job book was open
        //They confirmed
        //Then I went into the job book, got the next available job number
        //Told them what the job number was
        //And updated it in the job book using the data from the tax rolls
        //You'd have to ask
        //What parcel is ours first
        //Make that index [0]
        //And have that be special



        //So for work, it will be 
        //String path = "Z:\\" + year + "\\" + year + " Drawings" + "\\" + fileNumber;

        String path = "C:\\" + year + "\\" + year + " Drawings" + "\\" + fileNumber;

        File file = new File(path);

        boolean bool = file.mkdir();

        if(bool)

            System.out.println("Directory created successfully");

        else

            System.out.println("Sorry couldn't create specified directory");
        
        try {

            //For work its going to be 
            //Path temp = Files.copy(Paths.get("Z:\\BORDERS(100sc).dwg"),  Paths.get(path + "\\" + fileNumber + ".dwg"));

            Path temp = Files.copy(Paths.get("C:\\BORDERS(100sc).dwg"),  Paths.get(path + "\\" + fileNumber + ".dwg"));

            if(temp != null) 

                System.out.println("File renamed and moved successfully"); 

            else

                System.out.println("Failed to move the file"); 


        } catch (IOException ex) {

            System.out.println(ex.toString());

        }


        PrintStream deedOutline = new PrintStream(new File(path + "\\" + "DeedOutline.txt"));
        PrintStream mailingAddress = new PrintStream(new File(path + "\\" + "MailingAddress.txt"));
                
        while (finished == false) {

            //Get Section number
            System.out.println("Enter the parcels you're looking for. Start with the SECTION number and then press enter.");
            String sectionNumber = in.nextLine() + "-";

            //Get Block number
            System.out.println("Next, enter the BLOCK number and the press enter.");
            String blockNumber = in.nextLine() + "-";

            //Get every parcel Number
            System.out.println("Now, enter every PARCEL number you need, each one followed by enter. When you're finished, type done and hit enter.");
            while(end == false) {

            	entries++;
            	String parcelNumber = in.nextLine();
            	if (parcelNumber.equalsIgnoreCase("done")){

            		entries--;
            		end = true;

            	}
            	else

            		parcels.add(sectionNumber + blockNumber + parcelNumber);

            }

            System.out.println("Do you have any additional sections? (\"yes\" or \"no\")");
            String answer = in.nextLine();

            if (answer.equalsIgnoreCase("yes")) {

                finished = false;
                end = false;

            }

            else 

                finished = true;

        }

        int line = 0;
        String nextLine;
        boolean complete = false;
        boolean found = false;
        boolean name = false;
        int count = 0;
        int total = 0;
        int start = 0;


        //Prints the data
        while(reader.hasNextLine()) {

            if (start == 1) {

                start = 0;

            }

            int i = 0;
        	Scanner sc = new Scanner(reader.nextLine());
            while(sc.hasNext()) {

                String next = sc.next();

                if (parcels.contains(next)) {

                    deedOutline.println();
                    mailingAddress.println();

                    deedOutline.println(next);

                    while (i < 1){
                        nextLine = reader.nextLine();

                        if ((nextLine.contains("*******************************************************************************************************") )) {

                            Scanner sc2 = new Scanner(nextLine);

                            while (sc2.hasNext()){

                                if (parcels.contains(sc2.next())) {

                                    start = 2;
                                    break;

                                }

                            }

                            i = 5;
                            complete = false;
                            found = false;
                            name = false;

                            if (total < 1) {

                                mailInfoFirstLine.add(null);

                            }

                            if (total < 2) {

                                mailInfoSecondLine.add(null);

                            }

                            if (total < 3) {

                                mailInfoThirdLine.add(null);

                            }

                            if (total < 4) {

                                mailInfoFourthLine.add(null);

                            }

                            if (total < 5) {

                                mailInfoFifthLine.add(null);

                            }

                            if (total < 6) {

                                mailInfoSixthLine.add(null);

                            }

                            if (total < 7) {

                                mailInfoSeventhLine.add(null);

                            }

                            if (total < 8) {

                                mailInfoEigthLine.add(null);

                            }

                            count = 0;
                            total = 0;

                        }

                        else {

                            if (nextLine.contains("BOOK")) {

                                deedOutline.println(nextLine.substring(nextLine.indexOf("BOOK")));

                            }  

                            
                            if (nextLine.contains("ACRES") && found == false) {

                                String acreageString = nextLine.substring(nextLine.indexOf("ACRES"));
                                Scanner acreageScanner = new Scanner(acreageString);
                                double acreage = 0;
                                while (acreageScanner.hasNext() && found == false) {

                                    if (acreageScanner.hasNextDouble()) {
                                        acreage = acreageScanner.nextDouble();
                                        found = true;
                                    }
                                    if (acreageScanner.hasNext())
                                        acreageScanner.next();
                                }

                                if (acreage > 0) {

                                    deedOutline.println("Acres: " + acreage);

                                }
                            }                           

                            ArrayList<String> list = new ArrayList<String>();

                            Scanner s = new Scanner(nextLine).useDelimiter("\t");
                            String nextS = s.next();

                            if (parcels.contains(nextS)) {

                                start = 1;
                            
                            }

                            if (start > 1){

                                start--;

                            }

                            //This will print the address
                            if (!parcels.contains(nextS) && complete == false && !nextS.equals("") && !nextS.equals("\t") && start == 1) {

                                //This is so that only 1 line is printed under the address
                                try {

                                    int zipCode = Integer.parseInt(nextS.substring(nextS.length() - 5, nextS.length()));
                                    mailingAddress.println(nextS);
                                    

                                    if (count == 0)  {

                                        mailInfoFirstLine.add(nextS);

                                    }

                                    if (count == 1)  {

                                        mailInfoSecondLine.add(nextS);

                                    }

                                    if (count == 2)  {

                                        mailInfoThirdLine.add(nextS);

                                    }

                                    if (count == 3)  {

                                        mailInfoFourthLine.add(nextS);

                                    }

                                    if (count == 4)  {

                                        mailInfoFifthLine.add(nextS);

                                    }

                                    if (count == 5)  {

                                        mailInfoSixthLine.add(nextS);

                                    }

                                    if (count == 6)  {

                                        mailInfoSeventhLine.add(nextS);

                                    }

                                    if (count == 7)  {

                                        mailInfoEigthLine.add(nextS);

                                    }
                                    
                                    complete = true;

                                    count++;
                                    total++;

                                } catch (Exception e) {

                                    mailingAddress.println(nextS);

                                    if (count == 0)  {

                                        mailInfoFirstLine.add(nextS);

                                    }

                                    if (count == 1)  {

                                        mailInfoSecondLine.add(nextS);

                                    }

                                    if (count == 2)  {

                                        mailInfoThirdLine.add(nextS);

                                    }

                                    if (count == 3)  {

                                        mailInfoFourthLine.add(nextS);

                                    }

                                    if (count == 4)  {

                                        mailInfoFifthLine.add(nextS);

                                    }

                                    if (count == 5)  {

                                        mailInfoSixthLine.add(nextS);

                                    }

                                    if (count == 6)  {

                                        mailInfoSeventhLine.add(nextS);

                                    }

                                    if (count == 7)  {

                                        mailInfoEigthLine.add(nextS);

                                    }

                                    count++;
                                    total++;

                                    if (name == false && !nextS.equals("")) {

                                        deedOutline.println(nextS);
                                        name = true;

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(mailInfoFirstLine); 
        System.out.println(mailInfoSecondLine);
        System.out.println(mailInfoThirdLine);
        System.out.println(mailInfoFourthLine);
        System.out.println(mailInfoFifthLine);
        System.out.println(mailInfoSixthLine);
        System.out.println(mailInfoSeventhLine);
        System.out.println(mailInfoEigthLine);

	}
}
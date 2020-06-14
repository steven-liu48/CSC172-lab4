//Xiaoxiang "Steven" Liu
//xliu102@u.rochester.edu
//585-201-2325
//MW 6:15PM - 7:30PM

//Lab Partner:
//Winnie Wan
//wwan5@u.rochester.edu
//MW 3:30PM - 4:45PM

//Lab 4, DNA Strings in Linked List

//The file DNAList.java and Lab4Test1.txt are for homework.
//We built the code question by question as required. 
//Put the compressed file into the tester to see the result.

import java.io.*;

public class DNAList {
	
	//Check DNA/RNA by reading the letters
	public static boolean check(String str,boolean isDNA) {
		for(int i=0;i<str.length();i++) {
			if(isDNA) {
				if(!str.substring(i, i+1).equals("A")
					&&!str.substring(i, i+1).equals("C")
					&&!str.substring(i, i+1).equals("G")
					&&!str.substring(i, i+1).equals("T")) {
					return false;
				}
			}
			if(!isDNA) {
				if(!str.substring(i, i+1).equals("A")
					&&!str.substring(i, i+1).equals("C")
					&&!str.substring(i, i+1).equals("G")
					&&!str.substring(i, i+1).equals("U")) {
					return false;
				}
			}
		}
		return true;
	}
	
	//Turn a string into a linked list
	public static LList<String> turnToList(String str) {
		LList<String> list = new LList<String>();
		for(int i=0;i<str.length();i++) {
			list.append(str.substring(i, i+1));
		}
		return list;
	}
	
	//Main method including the file reader
	public static void main(String[] args) throws IOException{
		
		//First String, length of the Sequence 
		//"50"
		//Create an array of Seq to store data
		Seq[] arr = new Seq[Integer.parseInt(args[0])];
		
		//Second String 
		//"command.txt"
		File file = new File(args[1]); 
		
		//File Reader
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		//String for file reading
		String st;
		
		//Loop for reading files
		while ((st = br.readLine()) != null) {
			//Create a new String to hold the command
			String input = st;
			
			//Split the commands into an Array
			String[] split = input.split(" ");
			
			//Insert
			if(split[0].equals("insert")&&split.length==4) {
				
				//DNA
				if((split[2].equals("DNA")&&check(split[3],true))) {
					//Pos is not out of bounds
					try {
						//Initialize arr with parameters (an LinkedList and type)
						arr[Integer.parseInt(split[1])] = new Seq(turnToList(split[3]),Seq.Type.DNA);
					}catch(Exception e) {
						System.out.println("Array out of bounds");
					}
				}else {
					
				//RNA
					if((split[2].equals("RNA")&&check(split[3],false))) {
						//Pos is not out of bounds
						try {
							//Initialize arr with parameters (an LinkedList and type)
							arr[Integer.parseInt(split[1])] = new Seq(turnToList(split[3]),Seq.Type.RNA);
						}catch(Exception e) {
							System.out.println("Array out of bounds");
						}
						
				//RNA and seq is null
					}else {
						if((split[2].equals("RNA")&&split[3].equals("null"))) {
							//Pos is not out of bounds
							try {
								//Put a Seq into arr with parameters (an LinkedList and type)
								arr[Integer.parseInt(split[1])]=new Seq(null,Seq.Type.RNA);
							}catch(Exception e) {
								System.out.println("Array out of bounds");
							}
						
				//DNA and seq is null
						}else {
							if((split[2].equals("DNA")&&split[3].equals("null"))) {
								//Pos is not out of bounds
								try {
									//Put a Seq into arr with parameters (an LinkedList and type)
									arr[Integer.parseInt(split[1])]=new Seq(null,Seq.Type.DNA);
								}catch(Exception e) {
									System.out.println("Array out of bounds");
								}
				//Seq elements do not match the type and other errors
							}else {
								System.out.println("Error occurred while inserting");
							}
						}
					}
				}
			}
			

			
			
			
			//Remove
			if(split[0].equals("remove")) {
				try {
					//Check if position is not null and pos is not type none
					if(arr[Integer.parseInt(split[1])] != null && arr[Integer.parseInt(split[1])].getType() != Seq.Type.NONE) {
						//Set the Seq to empty
						arr[Integer.parseInt(split[1])] = new Seq(null,Seq.Type.NONE);
					}
					else {
						//Required output
						System.out.println("No sequence to remove at specified position");
					}
				}catch(Exception e) {
					System.out.println("Array out of bounds");
				}
			}
			
			
			
			
			
			//Print without a second argument
			if(split[0].equals("print")&&split.length==1) {
				for(int i=0;i<arr.length;i++) {
					//current now is not null
					if(arr[i]!=null && arr[i].getList()!=null) {
						//current row is DNA
						if(arr[i].getType() == Seq.Type.DNA) {
							System.out.println(i + "       " + "DNA     "+arr[i].getList().toString());
						}
						//current row is DNA
						if(arr[i].getType() == Seq.Type.RNA) {
							System.out.println(i + "       " + "RNA     "+arr[i].getList().toString());
						}
					}
				}
			}
			
			
			
			
			
			//Print with a second argument
			if(split[0].equals("print")&&split.length==2) {
				String output = "";
				//Position is not null and type is not none
				if(arr[Integer.parseInt(split[1])] != null
				&& arr[Integer.parseInt(split[1])].getType() != Seq.Type.NONE
				&& arr[Integer.parseInt(split[1])].getList() != null) {
					//DNA
					if(arr[Integer.parseInt(split[1])].getType() == Seq.Type.DNA) {
						output += "DNA     " + arr[Integer.parseInt(split[1])].getList().toString();
					}
					//RNA
					if(arr[Integer.parseInt(split[1])].getType() == Seq.Type.RNA) {
						output += "RNA     " + arr[Integer.parseInt(split[1])].getList().toString();
					}
				}else {
					output += "No sequence to print at specified position";
				}
				System.out.println(output);
			}
			
			
			
			
			
			//Clip
			if(split[0].equals("clip")) {
				int pos=Integer.parseInt(split[1]);
				int start=Integer.parseInt(split[2]);
				int end=Integer.parseInt(split[3]);
				
				try {
				//Precondition check of clip
				if(start > end) {
						arr[pos].setList(new LList<String>());
				}else if(end > arr[pos].getList().length()) {
					System.out.println("End index is out of bounds");
				}else if(start<0 || start > arr[pos].getList().length()){
					System.out.println("Start index is out of bounds");
				}else {
					//Start is higher than end
					//The result is a sequence containing no characters.
					int originalLength = arr[pos].getList().length();
					if(start > end) {
						arr[pos].setList(new LList<String>());
					//Start is not higher
					//Clip everything else off
					}else {					
						for(int i = end; i < originalLength; i++) {
							arr[pos].getList().moveToPos(end + 1);
							arr[pos].getList().remove();
						}
						
						for(int i = 0; i < start; i++) {
							arr[pos].getList().moveToPos(0);
							arr[pos].getList().remove();
						}
					}
				}
				}catch(NullPointerException e) {
					System.out.println("No sequence to clip at specified position");
				}
			}
			
			
			
			
			
			//Copy
			if (split[0].equals("copy"))
			{
				int pos1 = Integer.parseInt(split[1]);
				int pos2 = Integer.parseInt(split[2]);
				//If both position indexes are within the array bounds
				if (pos1 < arr.length && pos2 < arr.length && pos1 >= 0 && pos2 >= 0)
				{
					//If the first position is empty or has the type null
					if (arr[pos1] == null || arr[pos1].getType() == Seq.Type.NONE || arr[pos1].getList() == null)
					{
						System.out.println("No sequence to copy at specified position");
					}
					else //Else: First position has an actual sequence
					{
						//Create a list to store a copy for easier modification
						LList<String> copyLst = new LList<String>(); 
						arr[pos2] = new Seq(copyLst, arr[pos1].getType());
						arr[pos1].getList().moveToStart();
						//Iterate through original list
						for (int i = 0; i < arr[pos1].getList().length(); i++)
						{
							arr[pos1].getList().moveToPos(i);
							copyLst.append(arr[pos1].getList().getValue());
						}
						arr[pos2].setList(copyLst);
					}
				}
			}
			
			
			
			
			
			//Transcribe from DNA to RNA
			if (split[0].equals("transcribe")){
				int pos = Integer.parseInt(split[1]);
				if (pos < arr.length && pos >= 0) //Within array bounds
				{
					//If sequence object is null or has type none
					if (arr[pos] == null || arr[pos].getType() == Seq.Type.NONE || arr[pos].getList() == null)
					{
						System.out.println("No sequence to transcribe at specified position");
					}
					//If type RNA, cannot transcribe (since only DNA to RNA)
					else if (arr[pos].getType() == Seq.Type.RNA)
					{
						System.out.println("Cannot transcribe RNA");
					}
					else //If type DNA
					{
						//If the sequence is length 0, meaning null sequence
						if (arr[pos].getList().length() > 0) 
						{
							arr[pos].setType(Seq.Type.RNA);
							arr[pos].getList().moveToStart();
							String curr = "";
							LList<String> res = new LList<String>();
							//Iterate through original DNA list sequence
							//Seq temp = new Seq(res, null);
							for (int i = 0; i < arr[pos].getList().length(); i++)
							{
								arr[pos].getList().moveToPos(i);
								curr = arr[pos].getList().getValue();
								//If letter is A, complement is U
								if (curr.equals("A"))
								{
									res.append("U");
								}
								//If T in DNA, then U in RNA, and complement is A 
								if (curr.equals("T"))
								{
									res.append("A");
								}
								//Letter G, complement is C
								if (curr.equals("G"))
								{
									res.append("C");
								}
								//If C, complement is G
								if (curr.equals("C"))
								{
									res.append("G");
								}
								res.prev();
							}
							arr[pos].setList(res);
						}
						else //Else if list has length <= 0, or is empty
						{
							System.out.println("Empty sequence");
						}
					}
				}
			}	
		}
	}
}



//Linked List
class Link<E> {
	 private E element;        // Value for this node
	 private Link<E> next;     // Pointer to next node in list

	 // Constructors
	 Link(E it, Link<E> nextval)
	 { element = it;  next = nextval; }
	 Link(Link<E> nextval) { next = nextval; }

	 Link<E> next() { return next; }  // Return next field
	 Link<E> setNext(Link<E> nextval) // Set next field
	 { return next = nextval; }     // Return element field
	 E element() { return element; }  // Set element field
	 E setElement(E it) { return element = it; }
}

class Seq {

	public enum Type {
	    DNA, RNA, NONE;
	}
	
	private LList<String> list;
	private Type type;
	
	public Seq(LList<String> list,Type type) {
		this.list=list;
		this.type=type;
	}

	public LList<String> getList() {
		return list;
	}

	public void setList(LList<String> list) {
		this.list = list;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

class LList<E>{
	private Link<E> head;         // Pointer to list header
	private Link<E> tail;         // Pointer to last element
	protected Link<E> curr;       // Access to current element
	int cnt;		      // Size of list

	LList(int size) { this(); }   // Constructor -- Ignore size
	LList() {
		curr = tail = head = new Link<E>(null); // Create header
		cnt = 0;
	}

	public void clear() {
		head.setNext(null);         // Drop access to links
		curr = tail = head = new Link<E>(null); // Create header
		cnt = 0;
	}

	public void insert(E it) {
		curr.setNext(new Link<E>(it, curr.next()));  
		if (tail == curr) tail = curr.next();  // New tail
		cnt++;
	}

	public void append(E it) {
		tail = tail.setNext(new Link<E>(it, null));
		cnt++;
	}

	public E remove() {
		// Nothing to remove
		if (curr.next() == null) {
			return null; 
		}
		E it = curr.next().element();         // Remember value
		if (tail == curr.next()) tail = curr; // Removed last
		curr.setNext(curr.next().next());     // Remove from list
		cnt--;				// Decrement count
		return it;                            // Return value
	}

	public void moveToStart()
	{ curr = head; }

	
	public void moveToEnd()
	{ curr = tail; }
	

	public void prev() {
		if (curr == head) return; // No previous element
		Link<E> temp = head;
		// March down list until we find the previous element
		while (temp.next() != curr) temp = temp.next();
		curr = temp;
	}


	public void next() { if (curr != tail) curr = curr.next(); }

	public int length() { return cnt; }

	public int currPos() {
		Link<E> temp = head;
		int i;
		for (i=0; curr != temp; i++)
			temp = temp.next();
		return i;
	}

	public void moveToPos(int pos) {
		assert (pos>=0) && (pos<=cnt) : "Position out of range";
		curr = head;
		for(int i=0; i<pos; i++) curr = curr.next();
	}


	public E getValue() {
		if(curr.next() == null) return null;
		return curr.next().element();
	}

	public String toString(){
		// Save the current position of the list
		int oldPos = currPos();
    	int length = length();
    	StringBuffer out = new StringBuffer((length() + 1) * 4);

    	moveToStart();
    	for (int i = 0; i < oldPos; i++) {
    		out.append(getValue());
    		next();
    	}
    	for (int i = oldPos; i < length; i++) {
    		out.append(getValue());
    		next();
    	}
    	moveToPos(oldPos); // Reset the fence to its original position
    	return out.toString();
	}
}
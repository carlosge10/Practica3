import java.util.*;

public class HelloWorld{

	public class Pair               // a pair of states
	{
		public int P, Q;
		public Pair(int thep, int theq) { P = thep; Q = theq; }
	}

	public class Leftside{
			public int State;
			public char Symbol;
			public Leftside(int st, char sym) { State = st; Symbol = sym; }
	}

	public class DFA{
		public int StatesCount;
		public char[] Symbols;
		public bool[] IsFavorable;
		public int FavorableState;
		public int StartState;
		public Map<Leftside, int> Rules;

		public DFA readInput()
		{
			DFA dfa;
			// number of states
			dfa.StatesCount = Convert.ToInt32(Console.In.ReadLine()); 

			// symbols of the input tape
			string symbols_str = Console.In.ReadLine();
			dfa.Symbols = symbols_str.ToCharArray(0, symbols_str.Length);
			
			// a list of favorable states
			string[] favorable_list = (Console.In.ReadLine()).Split(' ');  
			dfa.IsFavorable = new bool[dfa.StatesCount + 1];
			foreach(string id in favorable_list)
				dfa.IsFavorable[Convert.ToInt32(id)] = true;
			

			dfa.StartState = Convert.ToInt32(Console.In.ReadLine()); 
			dfa.FavorableState = Convert.ToInt32(favorable_list[0]);
			
			dfa.Rules = new Dictionary<Leftside, int>();  
			string s;
			while((s = Console.In.ReadLine()) != "")   
			{
				string[] tr = s.Split(' ');
				dfa.Rules.Add(new Leftside(Convert.ToInt32(tr[0]), Convert.ToChar(tr[1])), Convert.ToInt32(tr[2]));
			}
		
			return dfa;
		}
	}

	public static void main(String ...args){

		System.out.println("kpedo");

	}

}
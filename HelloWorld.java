import java.util.*;
import java.util.Scanner;

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
		public Map<Leftside, Integer> Rules;

		public DFA readInput()
		{
			Scanner sc = new Scanner(System.in);
			DFA dfa;
			// number of states
			dfa.StatesCount = sc.nextInt(); 

			// symbols of the input tape
			string symbols_str = sc.nextLine();
			dfa.Symbols = symbols_str.toCharArray();
			
			// a list of favorable states
			string[] favorable_list = (sc.nextLine()).split(' ');  
			dfa.IsFavorable = new bool[dfa.StatesCount + 1];
			for(string id : favorable_list)
				dfa.IsFavorable[Integer.parseInt(id)] = true;
			

			dfa.StartState = sc.nextInt(); 
			dfa.FavorableState = Integer.parseInt(favorable_list[0]);
			
			dfa.Rules = new HashMap<Leftside, Integer>();  
			string s;
			while((s = sc.nextLine()) != "")   
			{
				string[] tr = s.split(' ');
				dfa.Rules.Add(new Leftside(Integer.parseInt(tr[0]), tr[1].charAt(0)), Integer.parseInt(tr[2]));
			}
		
			return dfa;
		}
	}

	public static Map<Pair, Boolean> setupMarkedPairs(DFA dfa)
	{
		// MARK pars where where one and only one state is favorable
		Map<Pair, Boolean> pairs = new HashMap<Pair, Boolean>();

		for(int i = 1; i <= dfa.StatesCount; ++i)
			for(int j = 1; j <= dfa.StatesCount; ++j)  
				{
					bool Marked = ((dfa.IsFavorable[i] == true && dfa.IsFavorable[j] == false) ||
								   (dfa.IsFavorable[i] == false && dfa.IsFavorable[j] == true));
					pairs.Add(new Pair(i, j), Marked);
				}
		
		return pairs;
	}

	public static void processPairs(DFA dfa, Map<Pair, Boolean> pairs)
	{
		bool found;
		do
		{
			found = false;
			for(Map.Entry<Pair, Boolean> pair : pairs.entrySet())
			{
				if(pair.Value == false)     
					for(char a in dfa.Symbols)
					{
						int d1 = dfa.Rules[new Leftside(pair.Key.P, a)];
						int d2 = dfa.Rules[new Leftside(pair.Key.Q, a)];

						if(pairs[new Pair(d1, d2)] == true) 
						{
							pairs[new Pair(pair.Key.P, pair.Key.Q)] = true;
							found = true;
							break exit;
						}
					}
			}

			exit: ;
		}
		while(found);
	}

	public static int[] createEqClasses(DFA dfa, Map<Pair, Boolean> pairs)
	{
		// determine equivalence classes
		int[] e_class = new int[dfa.StatesCount + 1];
		for(int i = 1; i <= dfa.StatesCount; ++i)
			e_class[i] = i;

		for(Map<Pair, Boolean> pair : pairs.entrySet())
			if(pair.Value == false)
				for(int i = 1; i <= dfa.StatesCount; ++i)
					if(e_class[i] == pair.Key.P)
						e_class[i] = pair.Key.Q;

		return e_class;
	}

	public static void outputResults(DFA dfa, Map<Pair, bool> pairs, int[] e_class)
	{
		// save the states of tne new automation in "states" object
		// (to remove duplicates)
		Map<Integer, Boolean> states = new HashMap<Integer, Boolean>();
		for(int state = 1; state <= dfa.StatesCount; ++state)
			states[e_class[state]] = true;
		
		// save the rules of the news rules in the "rules" object
		// (to remove duplicates)
		Map<string, Boolean> rules = new HashMap<string, Boolean>();
		for(KeyValuePair<Leftside, int> rule : dfa.Rules)
		{
			string rule_str = "(" + e_class[rule.Key.State].ToString() + ", " +
									rule.Key.Symbol.ToString() + ") -> " + e_class[rule.Value].ToString();
			
			rules[rule_str] = true;
		}
		
		System.out.println("States: "); 
		for(KeyValuePair<int, bool> state : states)
			System.out.println(state.Key + " ");

		System.out.println();

		//print special states
		System.out.println("Initial state: "+ e_class[dfa.StartState] +"\nFavorable state: " + e_class[dfa.FavorableState] );
		// print rules	
		System.out.println("Rules:");                
		for(KeyValuePair<string, bool> rule : rules)
			Console.WriteLine(rule.Key);
	}

	public static void main(String ...args){

		System.out.println("kpedo");

		DFA dfa = readInput();

		Dictionary<Pair, bool> pairs = setupMarkedPairs(dfa); 
		processPairs(dfa, pairs);

		int[] e_class = createEqClasses(dfa, pairs);
		
		outputResults(dfa, pairs, e_class);

	}

}
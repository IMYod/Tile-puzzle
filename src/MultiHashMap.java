import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//Mapping integer to set of 'T' values
//The integer generates by the hash function of 'T'
public class MultiHashMap<T> {
	
	HashMap<Integer, Set<T>> hash;
	
	public MultiHashMap() {
		hash = new HashMap<>();
	}
	
	public void add(T element) {
		Integer code = new Integer(element.hashCode());
		if (!hash.containsKey(code))
			hash.put(code, new HashSet<>());
		hash.get(code).add(element);
	}
	
	public void remove(T element) {
		Integer code = new Integer(element.hashCode());
		hash.get(code).remove(element);
	}
	
	public Set<T> get(T element) {
		Integer code = new Integer(element.hashCode());
		return hash.get(code);
	}
	
	public boolean contains(T element) {
		Integer code = new Integer(element.hashCode());
		if (!hash.containsKey(code))
			return false;
		return hash.get(code).contains(element);
	}
	
	//Get all the values in any set
	public Set<T> values() {
		Set<T> set = new HashSet<>();
		List<Set<T>> allSets = new LinkedList<>(hash.values());
		for (Set<T> someSet : allSets)
			set.addAll(someSet);
		return set;
	}

}

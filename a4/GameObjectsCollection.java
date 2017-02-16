package a4;

import java.util.*;

class GameObjectsCollection{
	
	private Vector<Object> objectVector;

	public GameObjectsCollection(){
		objectVector = new Vector<Object>();
	}
	public Object elementAt(int k){
		return objectVector.elementAt(k);
	}
	public void add(Object newObject){
		objectVector.addElement(newObject);
	}
	public void removeElementAt(int i){
		objectVector.remove(i);
	}
	public Iterator getIterator() {
		return new ObjectVectorIterator();
	}
	
	private class ObjectVectorIterator implements Iterator<Object>{
		
		private int currElementIndex;
		
		public ObjectVectorIterator() {
			currElementIndex = -1;
		}
		
		public boolean hasNext() {
			if (objectVector.size ( ) <= 0) return false;
			if (currElementIndex == objectVector.size() - 1 )
				return false;
			return true;
		}
		public void remove(){
			objectVector.removeElementAt(currElementIndex);
			currElementIndex--;
		}
		public Object next() {	
			currElementIndex ++ ;
			return(objectVector.elementAt(currElementIndex));
		}
	} //end private iterator class
		 
}

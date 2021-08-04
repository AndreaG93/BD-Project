package model;

import java.util.Vector;

public abstract class AbstractManager {
	private Vector<ApplicationObserver> listOfObservers = new Vector<ApplicationObserver>();

	/**
	 * This method is used to register a new observer.
	 * 
	 * @param arg0
	 *            - Represents an {@link ApplicationObserver} object.
	 */
	public void addObserver(ApplicationObserver arg0) {
		listOfObservers.add(arg0);
	}

	/**
	 * This method is used to remove an existing observer.
	 * 
	 * @param arg0
	 *            - Represents an {@link ApplicationObserver} object.
	 */
	public void removeObserver(ApplicationObserver arg0) {
		listOfObservers.remove(arg0);
	}

	public void notifyToObservers() {
		for (ApplicationObserver object : listOfObservers) {
			object.update();
		}
	}
}

package model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * The {@code Support} class is used to store some useful methods.
 * 
 * @author Andrea Graziani
 * @version 1.6
 */
public class Support {

	/**
	 * This method is used to verify if specified argument is {@code null}; if
	 * so it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents an {@code Object}.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static void nullCheck(Object arg0, String name) throws Exception {
		if (arg0 == null)
			throw new Exception("INVALID: '" + name + "' is null.");
	}

	/**
	 * This method is used to verify if specified argument is empty or null; if
	 * so it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static void emptyStringCheck(String arg0, String name) throws Exception {
		if (arg0.isEmpty())
			throw new Exception("INVALID: '" + name + "' is empty.");
	}

	/**
	 * This method is used to verify if specified argument has a length less
	 * than 64 characters; if not, it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static void maxLengthCheck(String arg0, String name) throws Exception {
		if (arg0.length() > 64)
			throw new Exception("INVALID: '" + name + "' cannot contain more than 64 characters.");
	}

	/**
	 * This method is used to verify if specified argument has a length greater
	 * than 6 characters; if not, it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static void minStringCheck(String arg0, String name) throws Exception {
		if (arg0.length() < 6)
			throw new Exception("INVALID: '" + name + "' cannot contain less than 6 characters.");
	}

	/**
	 * This method is used to verify if specified argument contains is a valid
	 * {@code String} object; if not, it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static void validStringOnlyLetterCheck(String arg0, String name) throws Exception {
		nullCheck(arg0, name);
		maxLengthCheck(arg0, name);
		emptyStringCheck(arg0, name);

		for (char elem : arg0.toCharArray()) {
			if (!Character.isLetter(elem))
				throw new Exception("INVALID: '" + name + "' can contain only letters.");
		}
	}

	/**
	 * This method is used to verify if specified argument is convertible to
	 * {@code double}; if not, it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static double stringToDoubleCheckField(String arg0, String name) throws Exception {
		nullCheck(arg0, name);
		emptyStringCheck(arg0, name);

		return Double.valueOf(arg0);
	}

	/**
	 * This method is used to verify if specified argument is convertible to
	 * {@code int}; if not, it raises an exception.
	 * 
	 * @param arg0
	 *            - Represents a {@code String} object.
	 * @param name
	 *            - Represents a {@code String} object.
	 * @throws Exception
	 */
	public static int stringToIntegerCheckField(String arg0, String name) throws Exception {
		nullCheck(arg0, name);
		emptyStringCheck(arg0, name);

		return Integer.valueOf(arg0);
	}

	/**
	 * This method is used to get an {@code Alert} object.
	 * 
	 * @param alertType
	 *            - Represents an {@code AlertType} object.
	 * @param title
	 *            - Represents a {@code String} object.
	 * @param header
	 *            - Represents a {@code String} object.
	 * @return An {@code Alert} object.
	 */
	public static Alert getAlert(Alert.AlertType alertType, String title, String header) {
		Alert dialog = new Alert(alertType);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		return dialog;
	}

	/**
	 * This method is used to show an 'exit' {@code Alert} object.
	 * 
	 * @param arg0
	 *            - Represents an {@code Stage} object.
	 */
	public static void showExitAlert(Stage arg0) {
		Alert dialog = getAlert(AlertType.CONFIRMATION, "Message", "You want to exit?");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK)
			arg0.close();
	}

	/**
	 * This method is used to show a confirmation {@code Alert} object.
	 * 
	 * @param arg1
	 *            - Represents an {@code String} object.
	 * @return
	 */
	public static boolean showConfirmationAlert(String arg0) {
		Alert dialog = getAlert(AlertType.CONFIRMATION, "Message", arg0);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK)
			return true;
		else
			return false;
	}
	
	
	/**
	 * This method is used to show a confirmation {@code Alert} object.
	 * 
	 * @param arg1
	 *            - Represents an {@code String} object.
	 * @return
	 */
	public static void showMessageAlert(String arg0) {
		getAlert(AlertType.INFORMATION, "Message", arg0).showAndWait();
	}
	
	
	
	public static String getDashedLine(boolean appendLineSeparator)
	{
	    StringBuilder sb = new StringBuilder(20);
	    for(int n = 0; n < 100; ++n)
	        sb.append('-');
	    if(appendLineSeparator)
	    	sb.append(System.lineSeparator());
	    return sb.toString();
	}
}

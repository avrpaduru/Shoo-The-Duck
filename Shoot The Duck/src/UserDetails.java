
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserDetails extends JPanel{

	static HashMap<String,ArrayList<Integer>> players;
	static JTable table;
	JScrollPane tableContainer;
	String[] columnNames = {"Name","Easy","Medium","Hard"};
	Object[][] tableData = new Object[1][4];

	UserDetails(){

        setLayout(new BorderLayout());

		table = new JTable(new DefaultTableModel(tableData,columnNames));
		table.setEnabled(false);
        tableContainer = new JScrollPane(table);
		//tableContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tableContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(tableContainer, BorderLayout.CENTER);
	}

	static void setPlayers(	HashMap<String,ArrayList<Integer>> pDetails){
		players = pDetails;
		DefaultTableModel  model = (DefaultTableModel)table.getModel();

		//remove all rows
		model.getDataVector().removeAllElements();

		Set keys = players.keySet();
		Iterator iterate = keys.iterator();
		while (iterate.hasNext()) {
			String key = (String)iterate.next();
			ArrayList<Integer> alist = players.get(key);
			model.addRow(new Object[]{key,alist.get(0),alist.get(1),alist.get(2)});
		}

		table.setModel(model);
	}
}
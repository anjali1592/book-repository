


import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Color;

public class List_Books {

	private JFrame frmBookRepository;
  private DefaultListModel model;
    GoogleAPI gapi;
    AuthorAPI api;
    APICall aci;
    List_Books lbs;
    private JTextField textField;
    static ArrayList<String> cal;
    private JTextArea textArea;
    private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					List_Books window = new List_Books();
					//window.frame.setVisible(true);
					//window.gapi = new GoogleAPI();
					//window.api =  new AuthorAPI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public List_Books() {
		initialize();
	}
    public void setAuthor(GoogleAPI a1,ArrayList<String> al,APICall ac){
    	gapi = a1;
    	lbs =this;
    	cal = al;
    	aci = ac;
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBookRepository = new JFrame();
		frmBookRepository.setTitle("Book Repository");
		frmBookRepository.getContentPane().setBackground(Color.PINK);
		frmBookRepository.setBounds(100, 100, 600, 600);
		frmBookRepository.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBookRepository.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			String text = null,callgoogleapi=null,random=null;int index = 0;
			HashMap<String,Book> hm = new HashMap<String,Book>();
			public void actionPerformed(ActionEvent arg0) {
				text = textField.getText();
				index = Integer.parseInt(text);
				
				File name,name1;
				callgoogleapi= cal.get(index-1); 
				try {
					hm = gapi.callGoogleApi(callgoogleapi);
					
					Book b1 = hm.get(callgoogleapi);
					frmBookRepository.dispose();
					BookDescription bd = new BookDescription();
					bd.setAPI(aci);
					bd.insertTexttoList(hm, callgoogleapi);
					System.out.println(b1.description);
				
				} catch (IOException e) {
					//System.out.println("");
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				catch (IndexOutOfBoundsException i){
					textArea.setText("OUT OF RANGE");
					
				}
			}
		});
		btnNewButton.setBounds(405, 271, 66, 29);
		frmBookRepository.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(321, 271, 56, 26);
		frmBookRepository.getContentPane().add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(53, 6, 302, 300);
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(20, 6, 510, 230);
		frmBookRepository.getContentPane().add(scroll,  BorderLayout.CENTER);
		
		lblNewLabel = new JLabel(" Enter Book no to view details of the book");
		lblNewLabel.setBounds(20, 248, 279, 57);
		frmBookRepository.getContentPane().add(lblNewLabel);

	}
    public void createList(List_Books lbs,ArrayList<String> al){
    	lbs.frmBookRepository.setVisible(true);
		for(int i = 0;i<al.size();i++){
			textArea.append((i+1)+") "+al.get(i));
			textArea.append("\n");	
         }
    }
}

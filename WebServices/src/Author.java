import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.awt.Color;


public class Author {

	private JFrame frmBookRepository;
	private JTextField textField;
    AuthorAPI author;
    GoogleAPI gapi;
    APICall ac;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Author window = new Author();
					window.frmBookRepository.setVisible(true);
				    window.author = new AuthorAPI();
				    window.gapi = new GoogleAPI();
				    window.ac = new APICall();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Author() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBookRepository = new JFrame();
		frmBookRepository.setTitle("Book Repository");
		frmBookRepository.getContentPane().setBackground(Color.PINK);
		frmBookRepository.setBounds(100, 100, 450, 300);
		frmBookRepository.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBookRepository.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Author Name");
		lblNewLabel.setBounds(29, 36, 123, 70);
		frmBookRepository.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(240, 50, 186, 42);
		frmBookRepository.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Get List of Books ");
		btnEnter.addActionListener(new ActionListener() {
			String url= null;
			ArrayList<String> al= new ArrayList<String>();
			public void actionPerformed(ActionEvent arg0) {
				url = textField.getText();
				try {
					al = author.callGoodReadsAPI(url);
					frmBookRepository.dispose();
					List_Books lb = new List_Books();
					lb.setAuthor(gapi,al,ac);
					lb.createList(lb,al);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(163, 134, 136, 70);
		frmBookRepository.getContentPane().add(btnEnter);
	}
}

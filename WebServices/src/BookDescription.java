import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JTextArea;
import java.awt.Color;


public class BookDescription {

	private JFrame frmBookRepository;
    BookDescription bd1;
    private DefaultListModel model;
    APICall ac1;
    static String book_name = null;
    JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 BookDescription window = new BookDescription();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookDescription() {
		initialize();
	}
   public void setAPI(APICall ac){
	   bd1 = this;
	   ac1 = ac;
   }
   public void insertTexttoList(HashMap<String,Book> hm,String name){
	   book_name = name;
	   bd1.frmBookRepository.setVisible(true);
   	   // model = new DefaultListModel();
		//list.setModel(model);
		for(int i = 0;i<hm.size();i++){
			Book book = hm.get(name);
			String des = book.description;
			
			textArea.append("DESCRIPTION::");
			if(des == null){
				textArea.append("No description available for this book");
			}
			textArea.append("\n");
			textArea.append(des);
			textArea.append("\n\n");
			String epub = book.epub;
			textArea.append(epub);
			textArea.append("\n\n");
			textArea.append(book.pdf);
			
		}
         
        
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
		
		JButton btnNewButton = new JButton("Yes");
		btnNewButton.addActionListener(new ActionListener() {
			HashMap<String,String> hm = new HashMap<String,String>();
			public void actionPerformed(ActionEvent e) {
				try {
					hm = ac1.callequest(book_name);
					frmBookRepository.dispose();
					Purchase p  = new Purchase();
					p.setPurchase();
					p.insertinTextArea(hm);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(254, 243, 98, 29);
		frmBookRepository.getContentPane().add(btnNewButton);
		
		JLabel lblDoYouWish = new JLabel("Check if available for purchase");
		lblDoYouWish.setBounds(27, 218, 211, 58);
		frmBookRepository.getContentPane().add(lblDoYouWish);
		
		JButton btnNo = new JButton("No");
		btnNo.setBounds(364, 243, 68, 29);
		frmBookRepository.getContentPane().add(btnNo);
		
		textArea = new JTextArea();
		textArea.setSize(500, 500);
		textArea.setLineWrap(true);
		textArea.setBounds(42, 65, 333, 109);
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(6, 6, 410, 200);
		frmBookRepository.getContentPane().add(scroll,  BorderLayout.CENTER);
	}

}

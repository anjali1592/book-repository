import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Color;


public class Purchase {

	private JFrame frmBookRepository;
	 private DefaultListModel model;
	 private JTextArea jt;
	Purchase p;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Purchase window = new Purchase();
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
	public Purchase() {
		
		initialize();
	}
	public void setPurchase(){
		p = this;
	}
    public void insertinTextArea(HashMap<String,String> hm){
    	int flag = 0;
         p.frmBookRepository.setVisible(true);
         for(Entry<String,String> en : hm.entrySet()){
        	 if(en.getKey().equals("viewItemURL")||en.getKey().equals("paymentMethod")||en.getKey().equals("autopay")
        			 || en.getKey().equals("discount")||en.getKey().equals("galleryURL")||en.getKey().equals("globalId")
         ||en.getKey().equals("tilte")){
        	 jt.append(en.getKey());
        	 jt.append("\n");
        	 jt.append(en.getValue());
        	 jt.append("\n\n");
        	 flag =1;
        	 }
        }
         if(flag != 1){
        	 jt.append("Book/Article not available for purchase");
        	 flag =0;
         }
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBookRepository = new JFrame();
		frmBookRepository.setTitle("Book Repository");
		frmBookRepository.setBackground(Color.PINK);
		frmBookRepository.getContentPane().setBackground(Color.PINK);
		frmBookRepository.setBounds(100, 100, 481, 327);
		frmBookRepository.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBookRepository.getContentPane().setLayout(null);
		jt = new JTextArea();
		jt.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(jt);
		scroll.setBounds(6, 6, 469, 251);
		frmBookRepository.getContentPane().add(scroll,  BorderLayout.CENTER);
		
		lblNewLabel = new JLabel("       eBay Online Payment");
		lblNewLabel.setBounds(149, 269, 199, 30);
		frmBookRepository.getContentPane().add(lblNewLabel);

	}
}

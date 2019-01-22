import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;

    // Jpanel object that will hold the login GUI
    private JPanel contentPane;

    // Setting up Basic layout for the login panel.
    public Login() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 380);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        // contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Text Field
        JTextField txtName = new JTextField();
        txtName.setBounds(67, 50, 165, 28);
        contentPane.add(txtName);
        txtName.setColumns(10);

        JLabel lblName = new JLabel("Name");
		lblName.setBounds(127, 34, 45, 16);
        contentPane.add(lblName);

        // Text field of IP Adress
        JTextField txtAddress = new JTextField();
		txtAddress.setBounds(67, 116, 165, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);

        JLabel lblIpAddress = new JLabel("IP Address:");
		lblIpAddress.setBounds(111, 96, 77, 16);
        contentPane.add(lblIpAddress);

        // Text Field for port
        JTextField txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(67, 191, 165, 28);
		contentPane.add(txtPort);

        // Labels for hinting the format of the input
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(133, 171, 34, 16);
        contentPane.add(lblPort);

        JLabel lblAddressDesc = new JLabel("(eg. 192.168.0.2)");
		lblAddressDesc.setBounds(94, 142, 112, 16);
		contentPane.add(lblAddressDesc);

		JLabel lblPortDesc = new JLabel("(eg. 8192)");
		lblPortDesc.setBounds(116, 218, 68, 16);
        contentPane.add(lblPortDesc);

        // Login Button setup and listener
        JButton btnLogin = new JButton("Login");
        // Adding a litener to the button
		btnLogin.addActionListener(new ActionListener() {
            // Fetching the data from input fields from a callback function
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
                int port = Integer.parseInt(txtPort.getText());
				login(name, address, port);
			}
		});
		btnLogin.setBounds(91, 311, 117, 29);
		contentPane.add(btnLogin);
    }

    private void login(String name, String address, int port) {
        dispose();
        new ClientWindow(name, address, port);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

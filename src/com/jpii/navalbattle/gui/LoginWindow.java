/*
 * Copyright (C) 2012 JPII and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jpii.navalbattle.gui;

import javax.swing.*;
import java.awt.event.*;
import com.jpii.navalbattle.NavalBattle;
import com.jpii.navalbattle.data.Constants;

public class LoginWindow {
	JButton loginButton;
	JFrame f;
	JLabel usernameLabel, passwordLabel;
	JTextField usernameField;
	JPasswordField passwordField;

	public LoginWindow() {
		
		NavalBattle.getDebugWindow().printInfo("LoginWindow opened");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		f = new JFrame();
		f.setTitle("NavalBattle");
		f.getContentPane().setLayout(null);
		usernameLabel = new JLabel();
		usernameLabel.setText("Username");
		usernameLabel.setBounds(311,11,100,20);
		usernameField = new JTextField(25);
		usernameField.setBounds(365,11,100,20);
		passwordLabel = new JLabel();
		passwordLabel.setText("Password");
		passwordLabel.setBounds(311,42,100,20);
		passwordField = new JPasswordField(25);
		passwordField.setBounds(365,42,100,20);
		loginButton = new JButton("Login");
		loginButton.setBounds(389,73,78,22);

		f.getContentPane().add(usernameLabel);
		f.getContentPane().add(usernameField);
		f.getContentPane().add(passwordLabel);
		f.getContentPane().add(passwordField);
		f.getContentPane().add(loginButton);
		
		passwordField.addKeyListener(new KeyboardListener(this));
		usernameField.addKeyListener(new KeyboardListener(this));		
		
		JLabel lblVersion = new JLabel(Constants.NAVALBATTLE_VERSION_TITLE);
		lblVersion.setBounds(10, 81, 193, 14);
		f.getContentPane().add(lblVersion);

		JButton registerButton = new JButton("Register");
		registerButton.setBounds(301, 73, 78, 22);
		f.getContentPane().add(registerButton);
		registerButton.addKeyListener(new KeyboardListener(this));
		
		JButton offlineButton = new JButton("Offline");
		offlineButton.setBounds(213, 73, 78, 22);
		f.getContentPane().add(offlineButton);

		f.setSize(491,143);
		f.setVisible(true);
		f.setLocation(500,300);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				login();
			}
		});
		
		offlineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				NavalBattle.getDebugWindow().printInfo("Opening in offline mode");
				NavalBattle.getDebugWindow().printWarning("RoketGamer disabled");
				NavalBattle.getGameState().setOffline(true);
				
				NavalBattle.getDebugWindow().printInfo("Disposing LoginWindow");
				f.dispose();
				NavalBattle.getDebugWindow().printInfo("Opening MainMenuWindow");
				new MainMenuWindow();
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {			
				NavalBattle.getDebugWindow().printInfo("Opening register page");
				
				String url = Constants.SERVER_LOCATION + "/register.php?game=1&name=NavalBattle";
				String os = System.getProperty("os.name").toLowerCase();
				Runtime rt = Runtime.getRuntime();

				try {
					if (os.indexOf("win") >= 0) {
						rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
					} else if (os.indexOf("mac") >= 0) {
						rt.exec("open " + url);

					} else if (os.indexOf("nix") >=0 || os.indexOf( "nux") >=0) {
						String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
								"netscape","opera","links","lynx", "chrome"};

						StringBuffer cmd = new StringBuffer();
						for (int i = 0; i < browsers.length; i++) {
							cmd.append((i==0  ? "" : " || " ) + browsers[i] + " \"" + url + "\" ");
						}

						rt.exec(new String[] { "sh", "-c", cmd.toString() });
					} else {
						return;
					}
					
				} catch (Exception e) {
					NavalBattle.getDebugWindow().printError(e.getMessage());
					return;
				}
			}
		});
		
		f.setFocusable(true);
		f.addKeyListener(new KeyboardListener(this));

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				NavalBattle.close();
			}
		});
	}
	
	/**
	 *  Method for handling login.
	 */
	public void login() {
		if(Constants.FORCE_LOGIN) {
			NavalBattle.getDebugWindow().printWarning("User is forced authenticated! Game will be insecure.");
			NavalBattle.getDebugWindow().printInfo("Disposing LoginWindow");
			f.dispose();
			NavalBattle.getDebugWindow().printInfo("Opening MainMenuWindow");
			new MainMenuWindow();
		} else if (false) { // TODO: Contact RoketGamer server
			NavalBattle.getDebugWindow().printInfo("User authenticated!");
			NavalBattle.getDebugWindow().printInfo("Disposing LoginWindow");
			f.dispose();
			NavalBattle.getDebugWindow().printInfo("Opening MainMenuWindow");
			new MainMenuWindow();
		} 
		else {
			NavalBattle.getDebugWindow().printWarning("Authentication failed!");	
			JOptionPane.showMessageDialog(f, "Unable to login. Check username and password.");
		}
	}
	
	/**
	 * Get method for LoginWindow
	 * 
	 * @return LoginWindow
	 */
	public JFrame getFrame() {
		return f;
	}
}
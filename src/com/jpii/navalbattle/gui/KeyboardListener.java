package com.jpii.navalbattle.gui;

import java.awt.event.*;

import com.jpii.navalbattle.NavalBattle;

public class KeyboardListener implements KeyListener {
	
	Object o;
	
	public KeyboardListener(Object l) {
		o = l;
	}
	
	public void keyPressed(KeyEvent k) {	
		if(k.getKeyCode() == KeyEvent.VK_ESCAPE) {
			NavalBattle.close();
		}
		
		if(o instanceof LoginWindow) {
			LoginWindow l = (LoginWindow) o;
			if(k.getKeyCode() == KeyEvent.VK_ENTER) {
				l.login();
			}
		}
		
		if(o instanceof DebugWindow) {
			DebugWindow d = (DebugWindow) o;
			if(k.getKeyCode() == KeyEvent.VK_ENTER) {
				d.submitCommandRemote();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}

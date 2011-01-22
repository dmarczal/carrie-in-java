package br.ufpr.c3sl.view.util;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel{

	private static final long serialVersionUID = 264977802547083579L;
	private JLabel lbLoading;
	private LoadingAnimation loading;

	public LoadingPanel(){
		loading = new LoadingAnimation();
		setVisible(false);
		configureLoad();
	}
	
	private void configureLoad(){
		lbLoading = new JLabel("Loading");
		lbLoading.setVerticalAlignment(JLabel.TOP);
		lbLoading.setHorizontalAlignment(JLabel.CENTER);
		lbLoading.setOpaque(true);
		lbLoading.setBackground(Color.RED);
		lbLoading.setForeground(Color.black);
		lbLoading.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(lbLoading);
	}
	
	public void startLoading(){
		this.setVisible(true);
		if (loading.isAlive())
			loading.stop();
		
		loading.start();
	}

	public void stopLoading(){
		loading.stop();
		setVisible(false);
	}
	
	private class LoadingAnimation implements Runnable {

		private volatile Thread thread = null;
		private int countPoint = 0;
		
		private void step() {
			lbLoading.setText(lbLoading.getText()+".");
			
			if (countPoint == 4){ 
				lbLoading.setText(lbLoading.getText().substring(0, lbLoading.getText().length()-countPoint));
				countPoint = 0;
				System.out.println("cortou" + lbLoading.getText());
			}
			
			countPoint++;	
		}
		
		public void run() {
			Thread me = Thread.currentThread();
			while(this.thread == me) {
				step();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
		}

		public void start() {
			this.thread = new Thread(this);
			this.thread.setPriority(Thread.MIN_PRIORITY);
			this.thread.start();
		}

		public void stop() {
			this.thread = null;
		}

		public boolean isAlive() {
			if(this.thread == null)
				return false;
			return this.thread.isAlive();
		}
	}
}
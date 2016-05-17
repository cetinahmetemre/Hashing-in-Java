import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;


public class Hash extends JFrame implements ActionListener{

	JLabel label1 , gereken;
	JLabel[]kod;
	JLabel[]yazi;
	JPanel panel1, panel2;
	JButton button1 , kua , doub , lineer , bilgi , sil , bul;
	JTextField textfield1;
	JTextArea textarea1;
	JScrollPane scroll;
	FileReader dosyaOkuyucu;
	BufferedReader okuyucu;
	Timer zamanKodla;
	int cx=85;
	int yaziindis;
	int x,y,b,z,kodsira;
	int sayac=0;
	int a=60;
	int deger=0;
	String girilen=" ";
	String adim;
	String[] dizi;
	public Hash ()
	{
		super();
		
		//Ýþlemler paneli
		textfield1 = new JTextField();
		
		button1 = new JButton("Tamam");
		lineer = new JButton("Linear Probing (Doðrusal Doldurma) ile Ekle");
		kua = new JButton("Quadratic Probing (Kuadratik Doldurma) ile Ekle");
		doub = new JButton("Double Hashing (Çift Kýrpma) ile Ekle");
		bilgi = new JButton("Hash Nedir");
		gereken = new JLabel("Tablo Boyutunu Giriniz; ");
		sil = new JButton("SÝL");
		bul= new JButton("BUL");
		
		sil.addActionListener(this);
		button1.addActionListener(this);
		lineer.addActionListener(this);
		kua.addActionListener(this);
		doub.addActionListener(this);
		bilgi.addActionListener(this);
		bul.addActionListener(this);
		
		panel1 = new JPanel();
		panel1.setBackground(Color.getHSBColor(178, 227, 130));
		panel1.setBounds(60,300, 600, 300);
		panel1.setLayout(null);	
		panel1.setBorder(new TitledBorder("Ýþlemler"));
		panel1.add(gereken);
		gereken.setBounds(10,20,140,25);
		panel1.add(textfield1);
		textfield1.setBounds(150,20,250,25);
		panel1.add(button1);
		button1.setBounds(401,20,100,25);
		panel1.add(kua);
		kua.setBounds(120,55,330,50);
		panel1.add(doub);
		doub.setBounds(120,110,330,50);
		panel1.add(lineer);
		lineer.setBounds(120,165,330,50);
		panel1.add(bilgi);
		bilgi.setBounds(123,220,100,50);
		panel1.add(sil);
		sil.setBounds(233,220,100,50);
		panel1.add(bul);
		bul.setBounds(343,220,100,50);
		
		add(panel1);
		
		// Üst panel
		label1 = new JLabel("HASH TABLOSU - HASHÝNG");		
		kod = new JLabel[4];	
		textarea1 = new JTextArea();
		
		try {
			dosyaOkuyucu = new FileReader("nedir.txt");
		 okuyucu = new BufferedReader(dosyaOkuyucu);
		 String ne;
		 
		 while((ne = okuyucu.readLine()) != null)
		 {
			 textarea1.setText(textarea1.getText()+"\n"+ne);
		 }
		 okuyucu.close();
		 dosyaOkuyucu.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		label1.setFont(new Font("Ariel", Font.BOLD, 25));
		panel2 = new JPanel();
		panel2.setLayout(null);	;
		scroll = new JScrollPane (textarea1);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		panel2.add(scroll);
		scroll.setBounds(670,305,520,300);
		panel2.setBackground(Color.getHSBColor(178, 227, 130));
		scroll.setVisible(false);
		panel2.add(label1);
		label1.setBounds(430,10,350,25);
		for(int i=0;i<kod.length;i++)
		{
			kod[i] = new JLabel();
			panel2.add(kod[i]);
			kod[i].setFont(new Font("Ariel", Font.BOLD, 16));
			kod[i].setBounds(700,170+i*70,500,300);
			
		}
		add(panel2);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
	
		int i=60;
		int j=0;
		g.drawRect(60, 100, 60 , 70);
		g.drawString("h(x)=x%"+Integer.toString(y), 62, 123);
		g.drawString("X", 85, 158);
		while(i<=x)
		{
		g.drawRect(120, 100, i, 70);
		g.drawString(Integer.toString(j), i+85, 123);
		
		j++;
		i +=60;
		}		
		
		g.drawLine(i+60, 135,60, 135);
		g.drawString(girilen, 60 , 200);
		
		g.setColor(Color.CYAN);
		g.drawRect(a, 100, 60, 70);
		
	}
	
	public void kareLin(int hl)
	{
		sayac=cx+120;
		b=hl*60+120;
		kodZaman();
		Timer myTimer=new Timer();
        TimerTask gorev =new TimerTask() {
        	
               @Override
               public void run() {
            	  a=b;
            	  repaint();
                  b+=60;
                  if(b==x+120)
                	  b=120;
                   if(b==sayac+60)
                      {		
                		zamanKodla.cancel();
                    	  	ekranaYaz();
                            myTimer.cancel();
                      }
               }
        };
        myTimer.schedule(gorev,0,1500);
	}
	
	public void kareDoub(int hl,int sayi)
	{
		z=0;
		sayac=cx+120;
		b=hl*60+120;
		kodZaman();
		Timer myTimer=new Timer();
        TimerTask gorev =new TimerTask() {
        	
               @Override
               public void run() {
            	   	
           	   		b=((hl+z*(11-(sayi%11)))%y)*60+120;
           	   		z++;
           	   		a=b;
           	   		repaint();
                    if(b==sayac)
                      {		
                    	  	ekranaYaz();
                    	  	zamanKodla.cancel();
                            myTimer.cancel();
                      }     
               }
        };
        myTimer.schedule(gorev,0,1500);
	}

	public void kareKua(int hl)
	{
		z=0;
		sayac=cx+120;
		b=hl*60+120;
		kodZaman();
		Timer myTimer=new Timer();
        TimerTask gorev =new TimerTask() {
        	
               @Override
               public void run() {
            	   	
           	   		b=((hl+z*z)%y*60)+120;
           	   		z++;
           	   		a=b;
           	   		repaint();
                      if(b==sayac)
                      {		
                    	  	ekranaYaz();
                    	  	zamanKodla.cancel();
                            myTimer.cancel();
                      }     
               }
        };
        myTimer.schedule(gorev,0,1500);
	}
	
	public void kodZaman()
	{
		kodsira=2;
		kod[2].setForeground(Color.black);
		zamanKodla=new Timer();
        TimerTask gorev =new TimerTask() {
        	
               @Override
               public void run() {
            	   
            	   kod[3].setForeground(Color.black);
            	   kod[kodsira-1].setForeground(Color.black);
            	   kod[kodsira].setForeground(Color.red);
            	   kodsira++;
            	   
            	   if(kodsira==4)
            	   {
            		   kodsira=1;
            	   }
            	   
               }
        };
        zamanKodla.schedule(gorev,0,500);
	}
	
	public void ekranaYaz()
	{
		if(adim.equals("ekle"))
		{
			yazi[yaziindis] = new JLabel(dizi[yaziindis]);	
		    panel2.add(yazi[yaziindis]);
			yazi[yaziindis].setBounds(cx+135,110,140,25);

			girilen=girilen+textfield1.getText()+", ";
			deger++;
			gereken.setText("Eleman giriniz;"); 
			
		}
		else if(adim.equals("sil"))
		{
			yazi[yaziindis].setText(dizi[yaziindis]);
		}
		textfield1.setText("");
		repaint();
	}	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Tamam"))
		{
			scroll.setVisible(false);
			if(textfield1.getText().equals(""))
				girilen = "BOÞ GÝRÝÞ YAPTINIZ , BÝR DEÐER GÝRÝNÝZ";
			
			else
			{
				y=Integer.parseInt(textfield1.getText());
				x=Integer.parseInt(textfield1.getText())*60;
				textfield1.setText("");
				girilen="Anahtarlar ;  ";
				
				gereken.setText("ilk elemaný giriniz;");
				button1.setVisible(false);
				
				dizi = new String[y];	
				yazi = new JLabel[y];
	
			}			
			repaint();
		}
				
		if(e.getActionCommand().equals("Quadratic Probing (Kuadratik Doldurma) ile Ekle"))
		{
			scroll.setVisible(false);
			adim="ekle";
			if(textfield1.getText().equals(""))
				JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
			
			else
			{
				doub.setVisible(false);
				lineer.setVisible(false);
			int hl=Integer.parseInt(textfield1.getText())%y;
			textfield1.requestFocusInWindow();
			kod[0].setText("i = girilen deðer % tablo boyutu");
			kod[1].setText("while(adým<Ht.length){");
			kod[2].setText("<html>if(HT[i]==boþ) // eðer tablo bölümü boþsa<br>HT[i]=girilen deðer; // girilen deðeri ekle</html>");
			kod[3].setText("<html>else // deðilse<br>i=(i+adým*adým)%tabloboyutu; // sayýyý adým karesi ile topla <br>}");
			if(deger<dizi.length)
			{
				if(dizi[hl]==null)
				{
			    dizi[hl]=textfield1.getText();
			    yaziindis=hl;
			    cx=hl*60;
			    a=cx+120;
			    kod[2].setForeground(Color.red);
			    ekranaYaz();
				}

				else
				{
					int j=1;
					int i=hl+j;
					
					
					while(j<=dizi.length)
					{
						if(i<dizi.length && dizi[i]==null)
						{
							dizi[i]=textfield1.getText();
							yaziindis=i;
							cx=i*60;
							kareKua(hl);
					        	break;
						}

						else
						{	
							
							i=(hl+j*j)%y;
							j++;
							
						}			
					}
					
					if(j>dizi.length)
						JOptionPane.showMessageDialog(null, j+" adýmdan sonra , hala bir ekleme noktasý bulunamýyor.");
				}		
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "HASH TABLOSU DOLDU");
				textfield1.setText("");
			}
			}
		}
		
		if(e.getActionCommand().equals("Double Hashing (Çift Kýrpma) ile Ekle"))
		{
			scroll.setVisible(false);
			adim="ekle";
			if(textfield1.getText().equals(""))
				JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
			
			else
			{
				kua.setVisible(false);
				lineer.setVisible(false);
				
			int sayi = Integer.parseInt(textfield1.getText());
			int hl=Integer.parseInt(textfield1.getText())%y;
			textfield1.requestFocusInWindow();
			kod[0].setText("i = girilen deðer % tablo boyutu");
			kod[1].setText("while(adým<Ht.length){");
			kod[2].setText("<html>if(HT[i]==boþ) // eðer tablo bölümü boþsa<br>HT[i]=girilen deðer; // girilen deðeri ekle</html>");
			kod[3].setText("<html>else // deðilse<br>i=((girilen%tablo boyutu)+adým*(11-(girilen%11)))%tablo boyutu;  <br>}");
			if(deger<dizi.length)
			{
				if(dizi[hl]==null)
				{
			    dizi[hl]=textfield1.getText();
			    yaziindis=hl;
			    cx=hl*60;
			    a=cx+120;
			    kod[2].setForeground(Color.red);
			    ekranaYaz();
				}

				else
				{
					int j=1;
					int i=(hl+j*(11-(sayi%11)))%y;
				
					
					while(j<=dizi.length)
					{
						if(i<dizi.length && dizi[i]==null)
						{
							dizi[i]=textfield1.getText();	
							yaziindis=i;
							cx=i*60;
							kareDoub(hl, sayi);
					        	break;
						}

						else
						{	
							
							i=(hl+j*(11-(sayi%11)))%y;
							j++;
														
						}			
					}
					
					if(j>dizi.length)
						JOptionPane.showMessageDialog(null, j+" adýmdan sonra , hala bir ekleme noktasý bulunamýyor.");
				}
			}
						
			else
			{
				JOptionPane.showMessageDialog(null, "HASH TABLOSU DOLDU");
				textfield1.setText("");
			}
			}
		}
		
		if(e.getActionCommand().equals("Linear Probing (Doðrusal Doldurma) ile Ekle"))
		{		
			scroll.setVisible(false);
			adim="ekle";
			if(textfield1.getText().equals(""))
				JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
			
			else
			{
				kua.setVisible(false);
				doub.setVisible(false);
			int hl=Integer.parseInt(textfield1.getText())%y;
			textfield1.requestFocusInWindow();
			kod[0].setText("i = girilen deðer % tablo boyutu");
			kod[1].setText("while(i<Ht.length){");
			kod[2].setText("<html>if(HT[i]==boþ) // eðer tablo bölümü boþsa<br>HT[i]=girilen deðer; // girilen deðeri ekle</html>");
			kod[3].setText("<html>else // deðilse<br>i++; // sayýyý 1 arttýr <br>}");
	
			if(deger<dizi.length)
			{
				
				if(dizi[hl]==null)
				{
			    dizi[hl]=textfield1.getText();
			    yaziindis=hl;
			    cx=hl*60;
			    a=cx+120;
			    kod[2].setForeground(Color.red);
			    ekranaYaz();
				}

				else
				{
					int i=hl+1;
					if(i>=dizi.length)
						i=0;
					
					while(i<dizi.length)
					{
						if(dizi[i]==null)
						{
							dizi[i]=textfield1.getText();
							yaziindis=i;
							cx=i*60;
							
							kareLin(hl);
					        	break;
						}
						else if(dizi[i]!=null && i==dizi.length-1)
							i=-1;
						else if(i==hl)
							break;
							
							i++;		
					}				
				}		
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "HASH TABLOSU DOLDU");
				textfield1.setText("");
			}
			}
		}
		
		if(e.getActionCommand().equals("SÝL"))
		{
			scroll.setVisible(false);
			adim="sil";
			if(kua.isVisible()==true)
			{
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");				
				else
				{
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(adým<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense <br>HT[i]=null; // girilen deðeri sil</html>");
				kod[3].setText("<html>else // deðilse<br>i=(i+adým*adým)%tabloboyutu; // sayýyý adým karesi ile topla <br>}");
							
					
					if(textfield1.getText().equals(dizi[hl]))
					{		
				    dizi[hl]=null;
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				     deger--;
				     ekranaYaz();
				   
					}

					else
					{
						int j=1;
						int i=hl+j;

						while(j<=dizi.length)
						{
							if(textfield1.getText().equals(dizi[i]) && i<dizi.length)
							{
								dizi[i]=null;
								yaziindis=i;
								cx=i*60;
								deger--;
								kareKua(hl);
						        	break;
							}

							else
							{	
								
								i=(hl+j*j)%y;
								j++;
								
							}			
						}	
					}		
				}
				
				
			}
			
			if(doub.isVisible()==true)
			{
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
				
				else
				{
					
				int sayi = Integer.parseInt(textfield1.getText());
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(adým<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense<br>HT[i]=null; // girilen deðeri sil</html>");
				kod[3].setText("<html>else // deðilse<br>i=((girilen+%tablo boyutu)+adým*(11-(girilen%11)))%tablo boyutu;  <br>}");
				
					if(textfield1.getText().equals(dizi[hl]))
					{
				    dizi[hl]=null;
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				    deger--;
				    ekranaYaz();
					}

					else
					{
						int j=1;
						int i=(hl+j*(11-(sayi%11)))%y;
					
						
						while(j<=dizi.length)
						{
							if(i<dizi.length && textfield1.getText().equals(dizi[i]))
							{
								dizi[i]=null;
								yaziindis=i;
								cx=i*60;
								kareDoub(hl, sayi);
								 deger--;
						        	break;
							}

							else
							{	
								
								i=(hl+j*(11-(sayi%11)))%y;
								j++;
								
								
							}			
						}	
					}
				
			}
		}
			if(lineer.isVisible()==true)
			{
				
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
				
				else
				{					
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(i<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense<br>HT[i]=null; // girilen deðeri sil</html>");
				kod[3].setText("<html>else // deðilse<br>i++; // sayýyý 1 arttýr <br>}");
	
				
					
					if(textfield1.getText().equals(dizi[hl]))
					{
						
				    dizi[hl]=null;
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				    deger--;
				    ekranaYaz();
					}

					else
					{
						int i=hl+1;
						if(i>=dizi.length)
							i=0;
						
						while(i<dizi.length)
						{
							if(textfield1.getText().equals(dizi[i]))
							{
								dizi[i]=null;
								yaziindis=i;
								cx=i*60;
								 deger--;					
								kareLin(hl);
						        	break;
							}
							else if(!textfield1.getText().equals(dizi[hl]) && i==dizi.length-1)
								i=-1;
							else if(i==hl)
								break;
								
								i++;		
						}				
					}		
				
			}
			}

			for(int k=0;k<dizi.length;k++)
				System.out.println(dizi[k]);
		}
		
		
		if(e.getActionCommand().equals("BUL"))
		{
			scroll.setVisible(false);
			adim="bul";
			if(kua.isVisible()==true)
			{
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");				
				else
				{
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(adým<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense <br>Break; // döngüden çýk</html>");
				kod[3].setText("<html>else // deðilse<br>i=(i+adým*adým)%tabloboyutu; // sayýyý adým karesi ile topla <br>}");
							
					
					if(textfield1.getText().equals(dizi[hl]))
					{		
				   
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				    repaint();
					}

					else
					{
						int j=1;
						int i=hl+j;

						while(j<=dizi.length)
						{
							if(textfield1.getText().equals(dizi[i]) && i<dizi.length)
							{
								
								yaziindis=i;
								cx=i*60;
								kareKua(hl);
						        	break;
							}

							else
							{	
								
								i=(hl+j*j)%y;
								j++;
								
							}			
						}	
					}		
				}
				
				
			}
			
			if(doub.isVisible()==true)
			{
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
				
				else
				{
					
				int sayi = Integer.parseInt(textfield1.getText());
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(adým<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense<br>break; // döngüden çýk</html>");
				kod[3].setText("<html>else // deðilse<br>i=((girilen+%tablo boyutu)+adým*(11-(girilen%11)))%tablo boyutu;  <br>}");
				
					if(textfield1.getText().equals(dizi[hl]))
					{
				   
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				    repaint();
					}

					else
					{
						int j=1;
						int i=(hl+j*(11-(sayi%11)))%y;
					
						
						while(j<=dizi.length)
						{
							if(i<dizi.length && textfield1.getText().equals(dizi[i]))
							{
								
								yaziindis=i;
								cx=i*60;
								kareDoub(hl, sayi);
						        	break;
							}

							else
							{	
								
								i=(hl+j*(11-(sayi%11)))%y;
								j++;
						
							}			
						}	
					}
				
			}
		}
			if(lineer.isVisible()==true)
			{
				
				if(textfield1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "BOÞ DEÐER GÝREMEZSÝNÝZ");
				
				else
				{
				int hl=Integer.parseInt(textfield1.getText())%y;
				textfield1.requestFocusInWindow();
				kod[0].setText("i = girilen deðer % tablo boyutu");
				kod[1].setText("while(i<Ht.length){");
				kod[2].setText("<html>if(HT[i]==girilen) // eðer tablo bölümü girilense<br>break; // döngüden çýk</html>");
				kod[3].setText("<html>else // deðilse<br>i++; // sayýyý 1 arttýr <br>}");
		
				
					
					if(textfield1.getText().equals(dizi[hl]))
					{
						
				  
				    yaziindis=hl;
				    cx=hl*60;
				    a=cx+120;
				    kod[2].setForeground(Color.red);
				    repaint();
					}

					else
					{
						int i=hl+1;
						if(i>=dizi.length)
							i=0;
						
						while(i<dizi.length)
						{
							if(textfield1.getText().equals(dizi[i]))
							{
								
								yaziindis=i;
								cx=i*60;
								
								kareLin(hl);
						        	break;
							}
							else if(!textfield1.getText().equals(dizi[hl]) && i==dizi.length-1)
								i=-1;
							else if(i==hl)
								break;
								
								i++;		
						}				
					}		
				}
			
			}
			
			for(int k=0;k<dizi.length;k++)
				System.out.println(dizi[k]);
		}
		if(e.getActionCommand().equals("Hash Nedir"))
		{
			scroll.setVisible(true);
		}	
	}


}

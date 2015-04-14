import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;


public class Scores {
	
	//ArrayList <String> _scores = new ArrayList<String>();
	List<Player> scores = new ArrayList<Player>();

	
	public Scores(){
		//try {
			//output = new FileOutputStream("Highscore/highscore.txt");
			upis_lista();
			sort_list();
	//	} catch (FileNotFoundException e) {
		//	e.printStackTrace();
	//	}


	}
	
	private void upis_lista(){
		Scanner s;
		try {
			s = new Scanner(new File("Highscore/highscore.txt"));
			
			while (s.hasNext()){
			    scores.add(new Player(s.next(),Integer.parseInt(s.next())));
			}
			System.out.print("Rezultati su upisani!!!");
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public String ispis_liste(){
		int i=1;
		String temp_scores="<html><div style=\"text-align: center; \">";
		for(Player score  : scores){
			temp_scores+=i+".   "+score.getName()+" ---------- "+score.getScore()+"<br>"+"<hr>";
			i++;
		}
		temp_scores+="</html>";
		return temp_scores;
	}
	
	public void score_new(String name,int bodovi){
		scores.add(new Player(name,bodovi));
		sort_list();
		scores.remove(10);
		output();
	}
	
	private void sort_list(){
		Collections.sort(scores,new Compare());
	}
	
	public void output(){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Highscore/highscore.txt"));
			for(Player player : scores){
				bw.write(player.getName()+" "+player.getScore());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLowestScore(){
		return scores.get(9).getScore();
	}
	
}

class Compare implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        int indicator = 0;
        if(p1.getScore() < p2.getScore())
        	indicator = 1;
        else if(p1.getScore() > p2.getScore())
        	indicator = -1;
        
        return indicator;
    }
}

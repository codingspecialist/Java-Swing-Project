package musicMaker;

import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;

import model.MusicFile;
import model.Track;

public class Converter {
	private MusicFile musicFile;
	private ArrayList<Track> tr;
	private ArrayList<String> drumTrack; //誘몄궗�슜
	private int bpm;
	
	public Converter(MusicFile musicFile) {
		this.musicFile = musicFile;
		musicFileToTrackArray();
		musicFileToDrumTrack();
		bpm = musicFile.getBpm();
	}
	
	public void addTrackAndSaveToMusicFile() {
		int maxNum = 0;
		for (Track t : tr) {
			if(maxNum<=t.getTrackNum()) {
				maxNum = t.getTrackNum()+1;
			}
		}
		tr.add(new Track(maxNum,"Piano","Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri "));
		trackSaveToMusicFile();
	}
	
	public void removeTrackAndSaveToMusicFile(int num) {
		for (int i = 0; i < tr.size(); i++) {
			if(tr.get(i).getTrackNum()==num) {
				tr.remove(i);
			}
		}
		trackSaveToMusicFile();
	}
	
	public void setBPM(int i) {
		bpm = i;
	}
	
	public int getBPM() {
		return bpm;
	}
	
	public MusicFile getMusicFile() {
		return musicFile;
	}
	
	public ArrayList<Track> getTrack() {
		return tr;
	}
	
	// DB�뿉�꽌 �씫�뼱�삩 note瑜� tr ArrayList�뿉 ���옣
	public void musicFileToTrackArray() {
		tr = new ArrayList<Track>();
		String[] trackStep1 = musicFile.getNote().split("V");
		for (int i = 0; i < trackStep1.length; i++) {
			// �꼸媛� 嫄곕Ⅴ湲�
			if (!(trackStep1[i].equals(""))) {
				int instStartIndex = trackStep1[i].indexOf("[") + 1;
				int instEndIndex = trackStep1[i].indexOf("]");
				int trackNum = Integer.parseInt(trackStep1[i].charAt(0) + "");
				String inst = trackStep1[i].substring(instStartIndex, instEndIndex);
				String finalNote = trackStep1[i].substring(instEndIndex + 1);
				tr.add(new Track(trackNum, inst, finalNote));
			}
		}
	}

	// DB�뿉�꽌 �씫�뼱�삩 drumNote瑜� drumTrack ArrayList�뿉 ���옣
	public void musicFileToDrumTrack() {
		drumTrack = new ArrayList<String>();
		String[] drumArray = musicFile.getDrumnote().split("@");
		for (int i = 0; i < drumArray.length; i++) {
			drumTrack.add(drumArray[i]);
		}
	}
	
	public void trackSaveToMusicFile() {
		musicFile.setNote(trackToString());
		musicFile.setTrack(tr.size());
	}

	// tr => String
	public String trackToString() {
		String st = "";

		for (Track t : tr) {
			st = st + "V" + t.getTrackNum() + " I[" + t.getInst() + "] " + t.getNote() + " ";
		}

		return st;
	}

	public String drumTrackToString() {
		String st = "";

		for (String s : drumTrack) {
			if(st.equals("")) {
				st = s;
			}else {
				st = st + "@" + s;
			}
		}

		return st;
	}
	
}

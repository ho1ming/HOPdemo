package datafeed;

public class TwitterWord implements Comparable<TwitterWord>{
	
	String text;

	int frequency;
	
	public TwitterWord(String txt, int freq){
		
		text = txt;
		frequency = freq;
		
	}
	

	@Override
	public boolean equals(Object otherWord){
		
		TwitterWord other = (TwitterWord) otherWord;
		
		return this.text.equals(other.text);
		
	}

	@Override
	public int compareTo(TwitterWord otherWord) {
		
		return this.getFrequency() - otherWord.getFrequency();
	}
	
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public int getFrequency() {
		return frequency;
	}



	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}

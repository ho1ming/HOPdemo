package datafeed;

public class TwitterWord implements Comparable<TwitterWord>{
	
	String text;

	int frequency;
	
	public TwitterWord(String txt, int freq){
		
		text = txt;
		frequency = freq;
		
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + frequency;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterWord other = (TwitterWord) obj;
		if (frequency != other.frequency)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
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
	
	public String toString(){
		return "text=" + this.text + ", freq=" + this.frequency;
	}

}

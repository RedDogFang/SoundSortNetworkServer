import javax.sound.sampled.LineUnavailableException;

public class SoundTest {

	Tone sounds = new Tone();
	
	public SoundTest(){
		for (int i=500; i<800; i+=25)
		{
			System.out.println(i);
			try {
				sounds.tone(i, 100,.5);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

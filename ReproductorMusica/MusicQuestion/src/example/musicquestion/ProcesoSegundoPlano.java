package example.musicquestion;

public class ProcesoSegundoPlano {

	MainActivity main = new MainActivity();

	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {

			while (true) {
				
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	protected void onProgressUpdate(String... values) {
		
		main.Update();

	}
}

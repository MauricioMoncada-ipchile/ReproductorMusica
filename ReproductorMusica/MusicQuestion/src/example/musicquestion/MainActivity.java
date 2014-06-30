package example.musicquestion;

import java.util.concurrent.TimeUnit;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	protected static final int Integer = 0;
	TextView tvPosicion;
	public String timer="0";
	private double startTime = 0;
	MediaPlayer mp;
	int posicion = 0;
	ProgressBar barraProgreso;
	Button btnCerrar;
	
	private Handler proceso1 = new Handler();
	//handler para la barra de progreso
	/*private Handler proceso2 = new Handler(){
		@Override
		public void handleMessage (Message msg){
			ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("Reproduciendo...");
			progressDialog.setMax(111256);
			progressDialog.setProgress((Integer)msg.obj);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	};

	private void iniciarProceso(View v) {
		iniciar(v);
		    Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
			
				for (int a=1;a<111256;a++){
					int contador = a;
					
					Message msg = new Message();
					msg.obj = a;
					proceso2.sendMessage(msg);
				}
				}
			
		});
		th1.start();
	}*/
	
	
	
	private Runnable runnable = new Runnable(){
		public void run() { 
			Update();//metodo que actualizara.
			Questions();//metodo que contiene las preguntas.
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvPosicion = (TextView) findViewById(R.id.tvPosicion);
		barraProgreso = (ProgressBar) findViewById(R.id.barraProgreso);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void StartProceso() {
		final Thread thread = new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						proceso1.post(runnable);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		thread.start();
	}
	
	public void destruir() {
		if (mp != null)
			mp.release();
	}

	public void iniciar(View v) {
		destruir();
		mp = MediaPlayer.create(this, R.raw.tvseries);
		mp.start();
		tvPosicion.setText("0");
		StartProceso();
	}

	public void Pausar(View v) {
		if (mp != null && mp.isPlaying()) {
			posicion = mp.getCurrentPosition();
			mp.pause();
		}
	}

	public void Continuar(View v) {
		if (mp != null && mp.isPlaying() == false) {
			mp.seekTo(posicion);
			mp.start();
		}
	}
	
	public void llenarBarra(View v){
		
	}
	
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public void Update() { // metodo que convierte los milisegundos en minutos y segundos
		
		startTime = mp.getCurrentPosition();

		tvPosicion.setText(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes((long) startTime),
				TimeUnit.MILLISECONDS.toSeconds((long) startTime)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime))));

		timer = tvPosicion.getText().toString();
		
	}
	
	//**********************************************************************************************************//
	//*********************************************************************************************************//
	//									PREGUNTAS : METODO QUESTIONS();										   //
	//**********************************************************************************************************//
	//*********************************************************************************************************//

	
	public void Questions() {
		// Validacion de todos los tiempos a preguntar
		
		
		if (timer.equals("0 min, 21 sec")) {
			
			try {
				
				Thread.sleep(1000);
				final View v = null;
				Pausar(v);
				
				//***respuesta de la primera pregunta realizada en el segundo 00:21
				//** la respuesta correcta es "The same thing"
				final CharSequence[] items = {"The same things", "Drink Beer","Eat BBQ" };
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				//***primera pregunta**//
				builder.setTitle("We did last Week ?");
				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int item) {
								
								//***Vidaciones de la respuestas***//
								if (items[item].equals("The same things")) {

									Toast toast = Toast.makeText(
											getApplicationContext(),
											"Exacto, Buena Respuesta, continuemos escuchando! ",													
											Toast.LENGTH_SHORT);
									toast.show();
									dialog.cancel();
									Continuar(v); 
								} else {

									Toast toast = Toast.makeText(
											getApplicationContext(),
											"Respuesta incorrecta, vuelve a intentarlo",													
											Toast.LENGTH_SHORT);
									toast.show();
									dialog.cancel();
									//*** inicia la cancion nuevamente***//
									iniciar(v);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			if (timer.equals("0 min, 39 sec")) {

				try {

					Thread.sleep(1000);
					final View v = null;
					Pausar(v);

					final CharSequence[] items = { "Sister", "Mom and Dad", "Brother" };

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Who Live Upstairs ?");
					builder.setSingleChoiceItems(items, -1,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int item) {

									if (items[item].equals("Mom and Dad")) {

										Toast toast = Toast.makeText(
												getApplicationContext(),
												"Exacto, Buena Respuesta, continuemos escuchando!",												
												Toast.LENGTH_SHORT);
												toast.show();
												dialog.cancel();
												Continuar(v);
									} else {
												Toast toast = Toast.makeText(
												getApplicationContext(),
												"Respuesta incorrecta, intente nuevamente:",														
												Toast.LENGTH_SHORT);
												toast.show();
												dialog.cancel();
												iniciar(v);
											}

								}
							});
					AlertDialog alert = builder.create();
					alert.show();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} }

		if (timer.equals("0 min, 43 sec")) {

			try {

				Thread.sleep(1000);
				final View v = null;
				Pausar(v);

				final CharSequence[] items = { "The Music", "The Piano","The Guitar" };

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Blaning out ?");
				builder.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int item) {

								if (items[item].equals("The Music")) {

									Toast toast = Toast.makeText(
										getApplicationContext(),
										"Exacto, Buena Respuesta, continuemos escuchando!",
										Toast.LENGTH_SHORT);
										toast.show();
										dialog.cancel();

									Continuar(v);
								} else {

									Toast toast = Toast.makeText(
											getApplicationContext(),
											"Respuesta incorrecta, intente nuevamente:",													
											Toast.LENGTH_SHORT);
									toast.show();
									dialog.cancel();

									iniciar(v);

								}

							}
						});
				AlertDialog alert = builder.create();
				alert.show();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else{

			if (timer.equals("1 min, 18 sec")) {

				try {

					Thread.sleep(1000);
					final View v = null;
					Pausar(v);

					final CharSequence[] items = { "In Manhattan", "In Santiago", "In Wisconsin" };

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Where we still Rocking ?");
					builder.setSingleChoiceItems(items, -1,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int item) {

									if (items[item].equals("In Wisconsin")) {

										Toast toast = Toast.makeText(
												getApplicationContext(),
												"Exacto, Buena Respuesta, continuemos escuchando!",														
												Toast.LENGTH_SHORT);
										toast.show();
										dialog.cancel();

										Continuar(v);
									} else {

										Toast toast = Toast.makeText(
												getApplicationContext(),
												"Respuesta incorrecta, intente nuevamente:",														
												Toast.LENGTH_SHORT);
										toast.show();
										dialog.cancel();
										iniciar(v);
									}
								}
							});
					AlertDialog alert = builder.create();
					alert.show();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} }
	}
	
	public void Cerrar(View view){
		android.os.Process.killProcess(android.os.Process.myPid());
	    finish();		
	}
}

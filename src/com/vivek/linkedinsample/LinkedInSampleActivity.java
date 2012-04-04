package com.vivek.linkedinsample;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.vivek.linkedinsample.LinkedinDialog.OnVerifyListener;

/**
 * @author Vivek Kumar Srivastava
 */
public class LinkedInSampleActivity extends Activity
{
	private Button helloButton;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		helloButton  = (Button) findViewById(R.id.hello);
		
		helloButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				linkedInLogin();
			}
		});

	}

	/**
	 * Connect IceBreaker with  linkedIn.
	 * <br>
	 * i.e. send  linkedIn access token to IceBreaker server.
	 */
	private void linkedInLogin()
	{
		ProgressDialog progressDialog = new ProgressDialog(LinkedInSampleActivity.this);//.show(LinkedInSampleActivity.this, null, "Loadong...");
		
		LinkedinDialog d = new LinkedinDialog(LinkedInSampleActivity.this, progressDialog);
		d.show();
		
		//set call back listener to get oauth_verifier value
		d.setVerifierListener(new OnVerifyListener()
		{
			@Override
			public void onVerify(String verifier)
			{
				try
				{
					Log.i("LinkedinSample", "verifier: " + verifier);

					LinkedInAccessToken accessToken = LinkedinDialog.oAuthService.getOAuthAccessToken(LinkedinDialog.liToken, verifier); 
					LinkedinDialog.factory.createLinkedInApiClient(accessToken);

					Log.i("LinkedinSample", "ln_access_token: " + accessToken.getToken());
					Log.i("LinkedinSample", "ln_access_token: " + accessToken.getTokenSecret());
				}
				catch (Exception e) 
				{
					Log.i("LinkedinSample", "error to get verifier");
					e.printStackTrace();
				}
			}
		});
		
		//set progress dialog 
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}
}


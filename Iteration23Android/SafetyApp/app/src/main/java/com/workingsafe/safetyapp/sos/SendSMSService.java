package com.workingsafe.safetyapp.sos;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.workingsafe.safetyapp.model.ContactPerson;

import java.io.Serializable;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SendSMSService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SMS = "com.workingsafe.safetyapp.sos.action.FOO";
    private static final String ACTION_BAZ = "com.workingsafe.safetyapp.sos.action.BAZ";

    // TODO: Rename parameters
    private static final String MESSAGE = "com.workingsafe.safetyapp.sos.extra.PARAM1";
    private static final String COUNT = "com.workingsafe.safetyapp.sos.extra.PARAM2";
    private static final String MOBILE_NUMBER = "com.workingsafe.safetyapp.sos.extra.PARAM3";

    public SendSMSService() {
        super("SendSMSService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSMS(Context context, List<ContactPerson> contactPersonList) {
/*        List<String> numbers = new ArrayList<String>();
        for(ContactPerson contactPerson: contactPersonList){
            numbers.add(contactPerson.getNumber());
        }
        String[] numberArr = numbers.toArray(new String[0]);*/

        Intent intent = new Intent(context, SendSMSService.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(MESSAGE, (Serializable) contactPersonList);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SendSMSService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(MESSAGE, param1);
        intent.putExtra(COUNT, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final List<ContactPerson> contactPeople = (List<ContactPerson>) intent.getSerializableExtra(MESSAGE);
                handleActionSMS(contactPeople);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSMS(List<ContactPerson> contactPersonList) {
        // TODO: Handle action Foo
        try {
            if(contactPersonList!=null) {
                try{
                    for(int j=0;j<contactPersonList.size();j++) {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(contactPersonList.get(j).getNumber(), null, contactPersonList.get(j).getMessage(), null, null);
                        SystemClock.sleep(1000);
                        //sendBroadcastMessage("Result:"+ (j+1) + " "+ contactPersonList.get(j).getNumber());

                    }
                }catch (Exception e){
                    Log.d("ExceptionHappens",e.getMessage().toString());
                }
            }
        }catch(Exception e){

        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
